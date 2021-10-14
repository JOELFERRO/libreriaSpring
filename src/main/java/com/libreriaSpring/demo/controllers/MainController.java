package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.services.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("titulo", "LIBRERIA SPRING");
        return "index";
    }
    
    
    
    
}
