package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.entity.User;
import com.greenportal.monitoting.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("totalTrees", 120);
        model.addAttribute("waterUsedToday", "2026-02-03 09:00");
        model.addAttribute("electricityConsumption", 8);
        model.addAttribute("wasteCollected", 2);

        return "dashboard_admin";
    }


    @GetMapping("/add-data")
    public String addData() {
        return "add_data";
    }

    @GetMapping("/view-reports")
    public String viewReports() {
        return "viewReports";
    }

    @GetMapping("/create-test-user")
    @ResponseBody
    public String createTestUser(UserRepository repo, PasswordEncoder encoder) {

        User u = new User();
        u.setUsername("admin");
        u.setPassword(encoder.encode("admin123"));
        u.setRole("ADMIN");

        repo.save(u);

        return "User created successfully";
    }


}
