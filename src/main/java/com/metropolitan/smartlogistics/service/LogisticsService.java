package com.metropolitan.smartlogistics.service;

import com.metropolitan.smartlogistics.model.*;
import com.metropolitan.smartlogistics.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogisticsService {

    private final ShipmentRepository shipmentRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final WarehouseRepository warehouseRepository;

    public LogisticsService(ShipmentRepository shipmentRepository,
                            VehicleRepository vehicleRepository,
                            DriverRepository driverRepository,
                            WarehouseRepository warehouseRepository) {
        this.shipmentRepository = shipmentRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    @Transactional
    public void updateShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    @Transactional
    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    public List<Shipment> filterShipments(String status, String priority) {
        return shipmentRepository.findAll().stream()
                .filter(s -> (status == null || status.isEmpty() || s.getStatus().equalsIgnoreCase(status)))
                .filter(s -> (priority == null || priority.isEmpty() || s.getPriority().equalsIgnoreCase(priority)))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Transactional
    public void updateDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public void updateWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public DashboardStats getDashboardStats() {
        int totalShipments = (int) shipmentRepository.count();
        int activeVehicles = (int) vehicleRepository.count();
        int activeDrivers = (int) driverRepository.findAll().stream()
                .filter(d -> d.getStatus() != null && d.getStatus().equalsIgnoreCase("Active"))
                .count();
        int pendingTasks = (int) shipmentRepository.findAll().stream()
                .filter(s -> s.getStatus() != null && s.getStatus().equalsIgnoreCase("Pending"))
                .count();

        return new DashboardStats(totalShipments, activeVehicles, activeDrivers, pendingTasks);
    }
}
