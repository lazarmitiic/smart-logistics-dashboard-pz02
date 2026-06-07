package com.metropolitan.smartlogistics.service;

import com.metropolitan.smartlogistics.model.DashboardStats;
import com.metropolitan.smartlogistics.model.Shipment;
import com.metropolitan.smartlogistics.repository.DriverRepository;
import com.metropolitan.smartlogistics.repository.ShipmentRepository;
import com.metropolitan.smartlogistics.repository.VehicleRepository;
import com.metropolitan.smartlogistics.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogisticsServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private LogisticsService logisticsService;

    @Test
    public void testAddShipment() {
        Shipment shipment = new Shipment(null, "SH-001", "Electronics", "Pending", "High", 100.0);
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);

        logisticsService.addShipment(shipment);

        verify(shipmentRepository).save(shipment);
    }

    @Test
    public void testGetDashboardStats() {
        when(shipmentRepository.count()).thenReturn(10L);
        when(vehicleRepository.count()).thenReturn(5L);
        when(driverRepository.findAll()).thenReturn(List.of());
        when(shipmentRepository.findAll()).thenReturn(List.of());

        DashboardStats stats = logisticsService.getDashboardStats();

        assertNotNull(stats);
        assertEquals(10, stats.getTotalShipments());
        assertEquals(5, stats.getActiveVehicles());
        assertEquals(0, stats.getActiveDrivers());
        assertEquals(0, stats.getPendingTasks());
    }
}
