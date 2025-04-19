package com.energy.usage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class UsageEntry {

    @Id
    private LocalDateTime hour;

    private double communityProduced = 0;
    private double communityUsed = 0;
    private double gridUsed = 0;

    public LocalDateTime getHour() { return hour; }
    public void setHour(LocalDateTime hour) { this.hour = hour; }

    public double getCommunityProduced() { return communityProduced; }
    public void setCommunityProduced(double communityProduced) { this.communityProduced = communityProduced; }

    public double getCommunityUsed() { return communityUsed; }
    public void setCommunityUsed(double communityUsed) { this.communityUsed = communityUsed; }

    public double getGridUsed() { return gridUsed; }
    public void setGridUsed(double gridUsed) { this.gridUsed = gridUsed; }

    @Override
    public String toString() {
        return String.format("[%s] Produced: %.3f, Used: %.3f, Grid: %.3f", hour, communityProduced, communityUsed, gridUsed);
    }
}