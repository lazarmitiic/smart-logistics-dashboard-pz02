package com.metropolitan.smartlogistics.controller;

import com.metropolitan.smartlogistics.model.Shipment;
import com.metropolitan.smartlogistics.service.LogisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final LogisticsService logisticsService;

    public ShipmentController(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "priority", required = false) String priority) {
        if ((status != null && !status.isEmpty()) || (priority != null && !priority.isEmpty())) {
            return ResponseEntity.ok(logisticsService.filterShipments(status, priority));
        }
        return ResponseEntity.ok(logisticsService.getAllShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShipmentById(@PathVariable("id") Long id) {
        Shipment shipment = logisticsService.getShipmentById(id);
        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipment);
    }

    @PostMapping
    public ResponseEntity<?> addShipment(@RequestBody Shipment shipment) {
        logisticsService.addShipment(shipment);
        return ResponseEntity.ok(shipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShipment(@PathVariable("id") Long id, @RequestBody Shipment shipment) {
        Shipment existing = logisticsService.getShipmentById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        shipment.setId(id);
        logisticsService.updateShipment(shipment);
        return ResponseEntity.ok(shipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShipment(@PathVariable("id") Long id) {
        Shipment existing = logisticsService.getShipmentById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        logisticsService.deleteShipment(id);
        return ResponseEntity.ok().build();
    }
}
