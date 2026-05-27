const bloodGroups = [
  ["A_POSITIVE", "A+"],
  ["A_NEGATIVE", "A-"],
  ["B_POSITIVE", "B+"],
  ["B_NEGATIVE", "B-"],
  ["AB_POSITIVE", "AB+"],
  ["AB_NEGATIVE", "AB-"],
  ["O_POSITIVE", "O+"],
  ["O_NEGATIVE", "O-"]
];

const api = {
  dashboard: "/api/dashboard",
  donors: "/api/donors",
  hospitals: "/api/hospitals",
  inventory: "/api/inventory",
  requests: "/api/requests"
};

document.addEventListener("DOMContentLoaded", async () => {
  fillBloodGroupSelects();
  wireForms();
  await refreshAll();
});

function fillBloodGroupSelects() {
  document.querySelectorAll("select[name='bloodGroup']").forEach((select) => {
    select.innerHTML = bloodGroups
      .map(([value, label]) => `<option value="${value}">${label}</option>`)
      .join("");
  });
}

function wireForms() {
  document.getElementById("donorForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.currentTarget));
    data.age = Number(data.age);
    data.eligible = event.currentTarget.eligible.checked;
    if (!data.lastDonationDate) {
      data.lastDonationDate = null;
    }
    await postJson(api.donors, data);
    event.currentTarget.reset();
    event.currentTarget.eligible.checked = true;
    toast("Donor registered successfully");
    await refreshAll();
  });

  document.getElementById("requestForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.currentTarget));
    data.hospitalId = Number(data.hospitalId);
    data.requiredUnits = Number(data.requiredUnits);
    await postJson(api.requests, data);
    event.currentTarget.reset();
    toast("Blood request submitted");
    await refreshAll();
  });

  document.getElementById("hospitalForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.currentTarget));
    await postJson(api.hospitals, data);
    event.currentTarget.reset();
    toast("Hospital registered successfully");
    await refreshAll();
  });

  document.getElementById("inventoryForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(event.currentTarget));
    data.units = Number(data.units);
    await patchJson(api.inventory, data);
    event.currentTarget.reset();
    toast("Inventory updated");
    await refreshAll();
  });
}

async function refreshAll() {
  const [dashboard, donors, hospitals, inventory, requests] = await Promise.all([
    getJson(api.dashboard),
    getJson(api.donors),
    getJson(api.hospitals),
    getJson(api.inventory),
    getJson(api.requests)
  ]);

  renderDashboard(dashboard);
  renderHospitalOptions(hospitals);
  renderInventory(inventory);
  renderRequests(requests);
  renderDonors(donors);
}

function renderDashboard(dashboard) {
  document.getElementById("totalDonors").textContent = dashboard.totalDonors;
  document.getElementById("totalHospitals").textContent = dashboard.totalHospitals;
  document.getElementById("pendingRequests").textContent = dashboard.pendingRequests;
  document.getElementById("totalAvailableUnits").textContent = dashboard.totalAvailableUnits;
}

function renderHospitalOptions(hospitals) {
  const select = document.querySelector("select[name='hospitalId']");
  select.innerHTML = hospitals
    .map((hospital) => `<option value="${hospital.id}">${hospital.name} - ${hospital.city}</option>`)
    .join("");
}

function renderInventory(inventory) {
  const inventoryByGroup = new Map(inventory.map((item) => [item.bloodGroup, item.availableUnits]));
  document.getElementById("inventoryList").innerHTML = bloodGroups
    .map(([value, label]) => `
      <article class="blood-card">
        <span>${label}</span>
        <strong>${inventoryByGroup.get(value) ?? 0}</strong>
        <span>units available</span>
      </article>
    `)
    .join("");
}

function renderRequests(requests) {
  const requestList = document.getElementById("requestList");
  if (!requests.length) {
    requestList.innerHTML = "<p>No blood requests available.</p>";
    return;
  }

  requestList.innerHTML = requests
    .slice()
    .sort((a, b) => b.id - a.id)
    .map((request) => `
      <article class="request-card">
        <div>
          <h3>${request.patientName}</h3>
          <p>${request.hospital.name} needs ${request.requiredUnits} unit(s) of ${labelFor(request.bloodGroup)}</p>
          <p>Priority: ${request.priority} · <span class="status ${request.status}">${request.status}</span></p>
        </div>
        <div class="request-actions">
          <button ${request.status !== "PENDING" ? "disabled" : ""} data-action="approve" data-id="${request.id}">Approve</button>
          <button class="reject" ${request.status !== "PENDING" ? "disabled" : ""} data-action="reject" data-id="${request.id}">Reject</button>
        </div>
      </article>
    `)
    .join("");

  requestList.querySelectorAll("button[data-action]").forEach((button) => {
    button.addEventListener("click", async () => {
      await patchJson(`${api.requests}/${button.dataset.id}/${button.dataset.action}`);
      toast(`Request ${button.dataset.action}d`);
      await refreshAll();
    });
  });
}

function renderDonors(donors) {
  document.getElementById("donorRows").innerHTML = donors
    .slice()
    .sort((a, b) => b.id - a.id)
    .map((donor) => `
      <tr>
        <td>${donor.fullName}</td>
        <td>${labelFor(donor.bloodGroup)}</td>
        <td>${donor.city}</td>
        <td>${donor.phone}</td>
        <td>${donor.eligible ? "Eligible" : "Not eligible"}</td>
      </tr>
    `)
    .join("");
}

function labelFor(value) {
  return bloodGroups.find(([group]) => group === value)?.[1] || value;
}

async function getJson(url) {
  const response = await fetch(url);
  return parseResponse(response);
}

async function postJson(url, data) {
  const response = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  return parseResponse(response);
}

async function patchJson(url, data) {
  const options = data
    ? {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
      }
    : { method: "PATCH" };
  const response = await fetch(url, options);
  return parseResponse(response);
}

async function parseResponse(response) {
  const text = await response.text();
  const data = text ? JSON.parse(text) : null;
  if (!response.ok) {
    throw new Error(data?.message || "Request failed");
  }
  return data;
}

function toast(message) {
  const toastElement = document.getElementById("toast");
  toastElement.textContent = message;
  toastElement.classList.add("show");
  setTimeout(() => toastElement.classList.remove("show"), 2200);
}
