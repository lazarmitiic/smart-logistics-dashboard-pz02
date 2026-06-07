package com.metropolitan.smartlogistics.controller;

import com.metropolitan.smartlogistics.model.Shipment;
import com.metropolitan.smartlogistics.service.LogisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {

    private final LogisticsService logisticsService;

    public ShipmentController(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @GetMapping
    public String listShipments(@RequestParam(value = "status", required = false) String status,
                                @RequestParam(value = "priority", required = false) String priority,
                                Model model) {
        if ((status != null && !status.isEmpty()) || (priority != null && !priority.isEmpty())) {
            model.addAttribute("shipments", logisticsService.filterShipments(status, priority));
        } else {
            model.addAttribute("shipments", logisticsService.getAllShipments());
        }
        model.addAttribute("status", status);
        model.addAttribute("priority", priority);
        return "shipments";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("shipment", new Shipment());
        return "shipment-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Shipment shipment = logisticsService.getShipmentById(id);
        if (shipment == null) {
            return "redirect:/shipments";
        }
        model.addAttribute("shipment", shipment);
        return "shipment-form";
    }

    @PostMapping
    public String saveShipment(@ModelAttribute("shipment") Shipment shipment) {
        if (shipment.getId() == null) {
            logisticsService.addShipment(shipment);
        } else {
            logisticsService.updateShipment(shipment);
        }
        return "redirect:/shipments";
    }

    @GetMapping("/delete/{id}")
    public String deleteShipment(@PathVariable("id") Long id) {
        logisticsService.deleteShipment(id);
        return "redirect:/shipments";
    }
}
