package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.entities.Cliente;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.services.ClienteServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicio;
    
    //CLIENTE
    
    @GetMapping("/cargar_cliente")
    public String cargarCliente(){
        return "/cargar_cliente";
    }
    
    @PostMapping("/guardar_cliente")
    public String guardarCliente(ModelMap model, @RequestParam String nombre, @RequestParam String documento, @RequestParam String telefono){
        
        try {
            clienteServicio.guardarCliente(nombre, documento, telefono);
        } catch (ErrorServicio ex) {
            model.put("errorC", ex.getMessage());
            model.put("nombre", nombre);
            model.put("documento", documento);
            model.put("telefono", telefono);
            
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            return "/cargar_cliente";
        }
        return "index";
    }
    
    @GetMapping("/listar_clientes")
    public String listarClientes(Model model){
        List<Cliente> clientes = clienteServicio.listarClientes();
        model.addAttribute("titulo", "LISTA DE CLIENTES");
        model.addAttribute("clientes", clientes);
        return "listar_clientes";
    }
}