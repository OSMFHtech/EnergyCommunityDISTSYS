package com.energy.usage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class EnergyMessageListener {

    @Autowired
    private UsageRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "energy")
    public void handleMessage(String message) {
        try {
            JsonNode json = mapper.readTree(message);
            String type = json.get("type").asText();
            double kwh = json.get("kwh").asDouble();
            LocalDateTime datetime = LocalDateTime.parse(json.get("datetime").asText());
            LocalDateTime hour = datetime.truncatedTo(ChronoUnit.HOURS);

            UsageEntry entry = repository.findById(hour).orElse(new UsageEntry());
            entry.setHour(hour);

            if ("PRODUCER".equals(type)) {
                entry.setCommunityProduced(entry.getCommunityProduced() + kwh);
            } else if ("USER".equals(type)) {
                double remaining = entry.getCommunityProduced() - entry.getCommunityUsed();
                entry.setCommunityUsed(entry.getCommunityUsed() + kwh);
                if (remaining < kwh) {
                    entry.setGridUsed(entry.getGridUsed() + (kwh - Math.max(remaining, 0)));
                }
            }

            repository.save(entry);
            System.out.println("Updated entry: " + entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}