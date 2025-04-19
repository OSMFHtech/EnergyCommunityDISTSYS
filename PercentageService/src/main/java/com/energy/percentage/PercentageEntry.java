package com.energy.percentage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class PercentageEntry {

    @Id
    private LocalDateTime hour;

    private double communityDepleted;
    private double gridPortion;

    public LocalDateTime getHour() { return hour; }
    public void setHour(LocalDateTime hour) { this.hour = hour; }

    public double getCommunityDepleted() { return communityDepleted; }
    public void setCommunityDepleted(double communityDepleted) { this.communityDepleted = communityDepleted; }

    public double getGridPortion() { return gridPortion; }
    public void setGridPortion(double gridPortion) { this.gridPortion = gridPortion; }

    @Override
    public String toString() {
        return String.format("[%s] Depleted: %.2f%%, Grid: %.2f%%", hour, communityDepleted, gridPortion);
    }
}