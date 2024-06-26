package org.tardis.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tardis.service.ScriptWriter;
import org.tardis.data.Country;
import org.tardis.service.ChartService;
import org.tardis.data.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AutoConfiguration
public class GraphController {

    @Autowired
    private ChartService chartService;
    @Autowired
    private ScriptWriter scriptWriter;

    @GetMapping("/graphs")
    public String listFiles(Model model) {
        return "graphs/index.html";
    }

    @PostMapping("/graphs/annual_surface_temperature_changes/data")
    @ResponseBody
    public List<CountryData> getASTCCountryData(@RequestBody List<String> countries) {
        ArrayList<char[]> isoCodes = (ArrayList<char[]>) countries.stream().map(String::toCharArray).collect(Collectors.toList());
        ArrayList<ArrayList<DataPoint>> dataPoints = chartService.getASTCDataPoints(isoCodes);

        List<CountryData> result = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++) {
            String country = countries.get(i);
            ArrayList<DataPoint> points = dataPoints.get(i);
            result.add(new CountryData(country, null, points));
        }

        return result;
    }

    @GetMapping("/graphs/annual_surface_temperature_changes")
    public String getASTCChart(@NotNull Model model) {
        List<Country> countries = chartService.getAllCountries();
        model.addAttribute("countries", countries);
        return "graphs/annual_surface_temperature_changes";
    }

    @PostMapping("/graphs/climate-related_disasters_frequency/data")
    @ResponseBody
    public List<CountryData> getCRDFCountryData(@RequestBody DataRequest dataRequest) {
        List<String> countries = dataRequest.getCountries();
        List<String> indicators = dataRequest.getIndicators();

        ArrayList<char[]> isoCodes = (ArrayList<char[]>) countries.stream().map(String::toCharArray).collect(Collectors.toList());
        ArrayList<ArrayList<DataPoint>> dataPoints = chartService.getCRDFDataPoints(isoCodes, indicators);

        List<CountryData> result = new ArrayList<>();
        int index = 0;
        for (String country : countries) {
            for (String indicator : indicators) {
                ArrayList<DataPoint> points = dataPoints.get(index++);
                result.add(new CountryData(country, indicator, points));
            }
        }

        return result;
    }

    @GetMapping("/graphs/climate-related_disasters_frequency")
    public String getCRDFChart(@NotNull Model model) {
        List<String> indicators = List.of(
                "Storm",
                "Drought",
                "Wildfire",
                "Landslide",
                "Flood",
                "Extreme temperature",
                "TOTAL"
        );
        List<Country> countries = chartService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("indicators", indicators);
        return "graphs/climate-related_disasters_frequency";
    }

    @PostMapping("/graphs/forest_and_carbon/data")
    @ResponseBody
    public List<CountryData> getFCCountryData(@RequestBody DataRequest dataRequest) {
        List<String> countries = dataRequest.getCountries();
        List<String> indicators = dataRequest.getIndicators();

        ArrayList<char[]> isoCodes = (ArrayList<char[]>) countries.stream().map(String::toCharArray).collect(Collectors.toList());
        ArrayList<ArrayList<DataPoint>> dataPoints = chartService.getFCDataPoints(isoCodes, indicators);

        List<CountryData> result = new ArrayList<>();
        int index = 0;
        for (String country : countries) {
            for (String indicator : indicators) {
                ArrayList<DataPoint> points = dataPoints.get(index++);
                result.add(new CountryData(country, indicator, points));
            }
        }

        return result;
    }

    @GetMapping("/graphs/forest_and_carbon")
    public String getFCChart(@NotNull Model model) {
        List<String> indicators = List.of(
                "Forest area",
                "Index of forest extent",
                "Land area",
                "Share of forest area"
            );
        List<Country> countries = chartService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("indicators", indicators);
        return "graphs/forest_and_carbon";
    }

    @PostMapping("/graphs/land_cover_accounts/data")
    @ResponseBody
    public List<CountryData> getLCACountryData(@RequestBody DataRequest dataRequest) {
        List<String> countries = dataRequest.getCountries();
        List<String> indicators = dataRequest.getIndicators();

        ArrayList<char[]> isoCodes = (ArrayList<char[]>) countries.stream().map(String::toCharArray).collect(Collectors.toList());
        ArrayList<ArrayList<DataPoint>> dataPoints = chartService.getLCADataPoints(isoCodes, indicators);

        List<CountryData> result = new ArrayList<>();
        int index = 0;
        for (String country : countries) {
            for (String indicator : indicators) {
                ArrayList<DataPoint> points = dataPoints.get(index++);
                result.add(new CountryData(country, indicator, points));
            }
        }

        return result;
    }

    @GetMapping("/graphs/land_cover_accounts")
    public String getLCAChart(@NotNull Model model) {
        List<String> indicators = List.of(
            "Artificial surfaces (including urban and associated areas)",
            "Climate Altering Land Cover Index",
            "Grassland",
            "Herbaceous crops",
            "Inland water bodies",
            "Mangroves",
            "Permanent snow and glaciers",
            "Shrub-covered areas",
            "Shrubs and/or herbaceous vegetation, aquatic or regularly flooded",
            "Sparsely natural vegetated areas",
            "Terrestrial barren land",
            "Tree-covered areas",
            "Woody crops"
        );
        List<Country> countries = chartService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("indicators", indicators);
        return "graphs/land_cover_accounts";
    }

    public static class DataRequest {
        private List<String> countries;
        private List<String> indicators;

        // Getters and setters
        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }

        public List<String> getIndicators() {
            return indicators;
        }

        public void setIndicators(List<String> indicators) {
            this.indicators = indicators;
        }
    }

    public static class CountryData {
        public String country;
        public String indicator;
        public List<DataPoint> data;

        public CountryData(String country, String indicator, List<DataPoint> data) {
            this.country = country;
            this.indicator = indicator;
            this.data = data;
        }
    }
}
