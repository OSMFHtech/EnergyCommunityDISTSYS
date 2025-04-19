package com.Energy.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

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
}