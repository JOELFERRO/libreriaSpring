package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.entities.Cliente;
import com.libreriaSpring.demo.entities.Libro;
import com.libreriaSpring.demo.services.PrestamoServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoServicio prestamoServicio;
    
    //PRESTAMO
    @PostMapping("/registrarPrestamo")
    public String registrarPrestamo(Model model, @RequestParam Date entrega, @RequestParam Date devolucion, @RequestParam Libro libro, @RequestParam Cliente cliente){
        
        prestamoServicio.registrarPrestamo(entrega, devolucion, libro, cliente);
        
    return "index";
    }
}