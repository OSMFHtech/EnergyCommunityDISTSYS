package com.energy.percentage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PercentageCalculator {

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private PercentageRepository percentageRepository;

    @RabbitListener(queues = "data.update")
    public void handleUpdateSignal(String message) {
        LocalDateTime hour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        usageRepository.findById(hour).ifPresent(usage -> {
            double communityUsed = usage.getCommunityUsed();
            double communityProduced = usage.getCommunityProduced();
            double gridUsed = usage.getGridUsed();

            double communityDepleted = communityUsed >= communityProduced ? 100.0 : 0.0;
            double totalUsed = communityUsed + gridUsed;
            double gridPortion = totalUsed > 0 ? (gridUsed / totalUsed) * 100.0 : 0.0;

            PercentageEntry entry = new PercentageEntry();
            entry.setHour(hour);
            entry.setCommunityDepleted(communityDepleted);
            entry.setGridPortion(gridPortion);

            percentageRepository.save(entry);
            System.out.println("Saved percentage entry: " + entry);
        });
    }
}