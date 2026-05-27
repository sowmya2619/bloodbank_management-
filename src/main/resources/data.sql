INSERT INTO donors (id, full_name, age, blood_group, phone, email, city, last_donation_date, eligible)
VALUES
  (1, 'Balla Sowmya Sushma', 21, 'O_POSITIVE', '7780381794', 'sowmysushma2619@gmail.com', 'Visakhapatnam', '2026-01-20', true),
  (2, 'Anusha Reddy', 24, 'A_POSITIVE', '9000011111', 'anusha@example.com', 'Vijayawada', '2025-12-12', true),
  (3, 'Rahul Kumar', 28, 'B_POSITIVE', '9000022222', 'rahul@example.com', 'Hyderabad', '2026-02-05', true),
  (4, 'Meghana Rao', 23, 'AB_POSITIVE', '9000033333', 'meghana@example.com', 'Visakhapatnam', '2025-11-18', false);

INSERT INTO hospitals (id, name, contact_person, phone, email, city, address)
VALUES
  (1, 'GVP Care Hospital', 'Dr. Kavitha', '9888811111', 'gvpcare@example.com', 'Visakhapatnam', 'Madhurawada, Visakhapatnam'),
  (2, 'LifeLine Hospital', 'Dr. Prasad', '9888822222', 'lifeline@example.com', 'Vijayawada', 'Benz Circle, Vijayawada');

INSERT INTO blood_inventory (id, blood_group, available_units)
VALUES
  (1, 'A_POSITIVE', 18),
  (2, 'A_NEGATIVE', 6),
  (3, 'B_POSITIVE', 14),
  (4, 'B_NEGATIVE', 4),
  (5, 'AB_POSITIVE', 9),
  (6, 'AB_NEGATIVE', 3),
  (7, 'O_POSITIVE', 22),
  (8, 'O_NEGATIVE', 5);

INSERT INTO blood_requests (id, hospital_id, patient_name, blood_group, required_units, priority, status, requested_at)
VALUES
  (1, 1, 'Emergency Case 101', 'O_POSITIVE', 2, 'HIGH', 'PENDING', CURRENT_TIMESTAMP),
  (2, 2, 'Surgery Case 44', 'B_POSITIVE', 3, 'MEDIUM', 'PENDING', CURRENT_TIMESTAMP);
