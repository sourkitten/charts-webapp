package org.tardis.controller;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AutoConfiguration
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/report")
    public String report(Model model) {
        return "report";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
}
