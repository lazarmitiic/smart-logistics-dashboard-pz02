package com.metropolitan.smartlogistics.service;

import com.metropolitan.smartlogistics.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogisticsService {

    private final List<Shipment> shipments = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();
    private final List<Warehouse> warehouses = new ArrayList<>();

    public LogisticsService() {
        shipments.add(new Shipment(1L, "SH-001", "Electronics delivery", "In Transit", "High", 120.5));
        shipments.add(new Shipment(2L, "SH-002", "Office supplies", "Delivered", "Medium", 45.0));
        shipments.add(new Shipment(3L, "SH-003", "Medical equipment", "Pending", "Critical", 85.2));
        shipments.add(new Shipment(4L, "SH-004", "Automotive parts", "In Transit", "Low", 340.0));

        vehicles.add(new Vehicle(1L, "BG-123-AA", "Mercedes-Benz Sprinter", 1500.0));
        vehicles.add(new Vehicle(2L, "BG-456-BB", "Volvo FH16", 20000.0));
        vehicles.add(new Vehicle(3L, "BG-789-CC", "Scania R500", 18000.0));

        drivers.add(new Driver(1L, "John Doe", "C", "Active"));
        drivers.add(new Driver(2L, "Jane Smith", "E", "Active"));
        drivers.add(new Driver(3L, "Bob Johnson", "B", "Inactive"));

        warehouses.add(new Warehouse(1L, "Central Hub", "Belgrade", 50000.0));
        warehouses.add(new Warehouse(2L, "North Warehouse", "Novi Sad", 25000.0));
        warehouses.add(new Warehouse(3L, "South Depot", "Nis", 15000.0));
    }

    public List<Shipment> getAllShipments() {
        return new ArrayList<>(shipments);
    }

    public Shipment getShipmentById(Long id) {
        return shipments.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addShipment(Shipment shipment) {
        if (shipment.getId() == null) {
            long nextId = shipments.stream().mapToLong(Shipment::getId).max().orElse(0L) + 1;
            shipment.setId(nextId);
        }
        shipments.add(shipment);
    }

    public void updateShipment(Shipment shipment) {
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getId().equals(shipment.getId())) {
                shipments.set(i, shipment);
                return;
            }
        }
    }

    public void deleteShipment(Long id) {
        shipments.removeIf(s -> s.getId().equals(id));
    }

    public List<Shipment> filterShipments(String status, String priority) {
        return shipments.stream()
                .filter(s -> (status == null || status.isEmpty() || s.getStatus().equalsIgnoreCase(status)))
                .filter(s -> (priority == null || priority.isEmpty() || s.getPriority().equalsIgnoreCase(priority)))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public Vehicle getVehicleById(Long id) {
        return vehicles.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            long nextId = vehicles.stream().mapToLong(Vehicle::getId).max().orElse(0L) + 1;
            vehicle.setId(nextId);
        }
        vehicles.add(vehicle);
    }

    public void updateVehicle(Vehicle vehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(vehicle.getId())) {
                vehicles.set(i, vehicle);
                return;
            }
        }
    }

    public void deleteVehicle(Long id) {
        vehicles.removeIf(v -> v.getId().equals(id));
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers);
    }

    public Driver getDriverById(Long id) {
        return drivers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addDriver(Driver driver) {
        if (driver.getId() == null) {
            long nextId = drivers.stream().mapToLong(Driver::getId).max().orElse(0L) + 1;
            driver.setId(nextId);
        }
        drivers.add(driver);
    }

    public void updateDriver(Driver driver) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId().equals(driver.getId())) {
                drivers.set(i, driver);
                return;
            }
        }
    }

    public void deleteDriver(Long id) {
        drivers.removeIf(d -> d.getId().equals(id));
    }

    public List<Warehouse> getAllWarehouses() {
        return new ArrayList<>(warehouses);
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouses.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addWarehouse(Warehouse warehouse) {
        if (warehouse.getId() == null) {
            long nextId = warehouses.stream().mapToLong(Warehouse::getId).max().orElse(0L) + 1;
            warehouse.setId(nextId);
        }
        warehouses.add(warehouse);
    }

    public void updateWarehouse(Warehouse warehouse) {
        for (int i = 0; i < warehouses.size(); i++) {
            if (warehouses.get(i).getId().equals(warehouse.getId())) {
                warehouses.set(i, warehouse);
                return;
            }
        }
    }

    public void deleteWarehouse(Long id) {
        warehouses.removeIf(w -> w.getId().equals(id));
    }

    public DashboardStats getDashboardStats() {
        int totalShipments = shipments.size();
        int activeVehicles = (int) vehicles.stream().count();
        int activeDrivers = (int) drivers.stream().filter(d -> d.getStatus().equalsIgnoreCase("Active")).count();
        int pendingTasks = (int) shipments.stream().filter(s -> s.getStatus().equalsIgnoreCase("Pending")).count();

        return new DashboardStats(totalShipments, activeVehicles, activeDrivers, pendingTasks);
    }
}
