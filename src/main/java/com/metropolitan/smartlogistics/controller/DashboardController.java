package com.metropolitan.smartlogistics.controller;

import com.metropolitan.smartlogistics.model.DashboardStats;
import com.metropolitan.smartlogistics.service.LogisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final LogisticsService logisticsService;

    public DashboardController(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getStats() {
        return ResponseEntity.ok(logisticsService.getDashboardStats());
    }
}
