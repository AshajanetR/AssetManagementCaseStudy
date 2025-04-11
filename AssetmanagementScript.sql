CREATE DATABASE assetmanagement;
USE assetmanagement;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS asset_allocations;
DROP TABLE IF EXISTS maintenance_records;
DROP TABLE IF EXISTS assets;
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE assets (
    asset_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    purchase_date DATE NOT NULL,
    location VARCHAR(100),
    status ENUM('INUSE', 'DECOMISSIONED', 'UNDERMAINTAINENCE','AVAILABLE') NOT NULL,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES employees(employee_id) ON DELETE SET NULL  
);
desc assets;

CREATE TABLE maintenance_records (
    maintenance_id INT PRIMARY KEY AUTO_INCREMENT,
    asset_id INT NOT NULL,
    maintenance_date DATE NOT NULL,
    description TEXT NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE  
);

CREATE TABLE asset_allocations (
    allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    asset_id INT NOT NULL,
    employee_id INT NOT NULL,
    allocation_date DATE NOT NULL,
    return_date DATE NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,  
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE  
);

CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    asset_id INT NOT NULL,
    employee_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'CANCELED') NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,  
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE  
);

INSERT INTO employees (name, department, email, password) VALUES 
('Alice Johnson', 'HR', 'alice.johnson@example.com', 'passAlice123'),
('Bob Smith', 'IT', 'bob.smith@example.com', 'passBob456'),
('Charlie Brown', 'Finance', 'charlie.brown@example.com', 'charlieFin789'),
('Diana Prince', 'Marketing', 'diana.prince@example.com', 'wonderD123'),
('Ethan Hunt', 'Operations', 'ethan.hunt@example.com', 'ethanOp007'),
('Fiona Gallagher', 'Sales', 'fiona.g@example.com', 'salesFi321'),
('George Martin', 'IT', 'george.martin@example.com', 'georgeCode900'),
('Hannah Lee', 'Design', 'hannah.lee@example.com', 'designHL555'),
('Ian Wright', 'Logistics', 'ian.wright@example.com', 'logiIan888'),
('Jasmine Kaur', 'R&D', 'jasmine.kaur@example.com', 'researchJK2025');

INSERT INTO assets (name, type, serial_number, purchase_date, location, status, owner_id)
VALUES 
('Dell Laptop', 'Electronics', 'S001', '2022-05-12', 'IT Room', 'INUSE', 1),
('Office Chair', 'Furniture', 'S002', '2021-11-23', 'HR Cabin', 'AVAILABLE', 2),
('Projector Epson', 'Electronics', 'S003', '2023-02-15', 'Meeting Room', 'INUSE', 3),
('HP Printer', 'Electronics', 'S004', '2020-07-01', 'Print Room', 'UNDERMAINTAINENCE', 4),
('Whiteboard', 'Stationery', 'S005', '2021-09-18', 'Training Room', 'AVAILABLE', 5),
('MacBook Pro', 'Electronics', 'S006', '2022-12-10', 'Design Studio', 'INUSE', 6),
('Water Dispenser', 'Appliance', 'S007', '2020-01-20', 'Pantry', 'DECOMISSIONED', 7),
('CCTV Camera', 'Security', 'S008', '2019-10-05', 'Entrance', 'INUSE', 8),
('Router Cisco', 'Networking', 'S009', '2021-06-30', 'Server Room', 'INUSE', 9),
('Standing Desk', 'Furniture', 'S010', '2023-03-14', 'Tech Lead Cabin', 'AVAILABLE', 10);

INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost)
VALUES
(1, '2023-06-10', 'Replaced battery and cleaned internals of Dell Laptop', 1500.00),
(2, '2024-01-25', 'Fixed paper jam and replaced toner in HP Printer', 800.00),
(3, '2022-11-15', 'General service and filter replacement for Water Dispenser', 450.00),
(4, '2023-09-08', 'Updated firmware and cleaned lens of Projector', 1200.00),
(5, '2024-03-20', 'Router reboot issue fixed, firmware upgraded', 950.00);


INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date, status)
VALUES
(1, 1, '2025-03-01', '2025-03-05', '2025-03-10', 'APPROVED'),
(2, 2, '2025-03-15', '2025-03-20', '2025-03-25', 'PENDING'),
(3, 3, '2025-02-20', '2025-02-22', '2025-02-28', 'CANCELED'),
(4, 4, '2025-04-01', '2025-04-03', '2025-04-07', 'APPROVED'),
(5, 5, '2025-03-10', '2025-03-12', '2025-03-15', 'APPROVED');

INSERT INTO asset_allocations (asset_id, employee_id, allocation_date, return_date)
VALUES
(1, 1, '2025-02-01', '2025-02-28'),
(2, 2, '2025-01-15', NULL),  
(3, 3, '2025-03-10', '2025-03-20'),
(4, 4, '2025-02-25', NULL),  
(5, 5, '2025-01-01', '2025-01-31');


select * from assets;
select * from asset_allocations;
select * from maintenance_records;
select * from reservations;
select * from employees;

