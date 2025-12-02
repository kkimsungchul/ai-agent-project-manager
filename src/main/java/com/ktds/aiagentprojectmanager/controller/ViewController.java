package com.ktds.aiagentprojectmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for handling view-related endpoints.
 * This controller serves HTML pages and handles redirects to views.
 */
@Controller
public class ViewController {

    /**
     * Redirects to the index.html page
     * @return Redirect string to the index.html page
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/ai/index.html";
    }
}