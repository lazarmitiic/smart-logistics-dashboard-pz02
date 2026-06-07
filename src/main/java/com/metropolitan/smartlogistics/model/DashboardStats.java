package com.metropolitan.smartlogistics.model;

public class DashboardStats {
    private Integer totalShipments;
    private Integer activeVehicles;
    private Integer activeDrivers;
    private Integer pendingTasks;

    public DashboardStats() {
    }

    public DashboardStats(Integer totalShipments, Integer activeVehicles, Integer activeDrivers, Integer pendingTasks) {
        this.totalShipments = totalShipments;
        this.activeVehicles = activeVehicles;
        this.activeDrivers = activeDrivers;
        this.pendingTasks = pendingTasks;
    }

    public Integer getTotalShipments() {
        return totalShipments;
    }

    public void setTotalShipments(Integer totalShipments) {
        this.totalShipments = totalShipments;
    }

    public Integer getActiveVehicles() {
        return activeVehicles;
    }

    public void setActiveVehicles(Integer activeVehicles) {
        this.activeVehicles = activeVehicles;
    }

    public Integer getActiveDrivers() {
        return activeDrivers;
    }

    public void setActiveDrivers(Integer activeDrivers) {
        this.activeDrivers = activeDrivers;
    }

    public Integer getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(Integer pendingTasks) {
        this.pendingTasks = pendingTasks;
    }
}
