package com.metropolitan.smartlogistics.controller;

import com.metropolitan.smartlogistics.service.LogisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final LogisticsService logisticsService;

    public DashboardController(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("stats", logisticsService.getDashboardStats());
        return "dashboard";
    }
}
