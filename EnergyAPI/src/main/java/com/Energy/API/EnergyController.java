package com.Energy.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private PercentageRepository percentageRepository;

    @GetMapping("/current")
    public PercentageEntry getCurrentPercentage() {
        LocalDateTime currentHour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return percentageRepository.findById(currentHour).orElse(null);
    }

    @GetMapping("/historical")
    public List<UsageEntry> getHistoricalData(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return usageRepository.findByHourBetween(start, end);
    }

    /**
     * This endpoint returns simulated data for EnergyUser and EnergyProducer.
     */
    @GetMapping("/current2")
    public Map<String, Object> getCurrentWithEntities() {
        // Simulated EnergyUser and EnergyProducer data
        List<Map<String, Object>> energyUsers = Arrays.asList(
                Map.of("id", 1, "time", LocalDateTime.now().toString()),
                Map.of("id", 2, "time", LocalDateTime.now().toString())
        );

        List<Map<String, Object>> energyProducers = Arrays.asList(
                Map.of("id", 1, "time", LocalDateTime.now().toString()),
                Map.of("id", 2, "time", LocalDateTime.now().toString())
        );

        // Response map
        Map<String, Object> response = new HashMap<>();
        response.put("energyUsers", energyUsers);
        response.put("energyProducers", energyProducers);

        return response;
    }
}