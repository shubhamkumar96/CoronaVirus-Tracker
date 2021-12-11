package io.tutorial.coronavirustracker.controllers;

import io.tutorial.coronavirustracker.models.LocationStats;
import io.tutorial.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        NumberFormat numberFormat = NumberFormat.getInstance();

        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat-> stat.getDiffFromPrevDay()).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", numberFormat.format(totalReportedCases));
        model.addAttribute("totalNewCases", numberFormat.format(totalNewCases));

        return "home";
    }
}
