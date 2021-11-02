package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.entities.Cliente;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.services.ClienteServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("documento", documento);
            model.put("telefono", telefono);
            
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            return "/cargar_cliente";
        }
        return "index";
    }
    
    @RequestMapping("/eliminar_cliente")
    public String eliminarCliente(@RequestParam String id) throws ErrorServicio{
        try{
          clienteServicio.eliminarCliente(id);
        return "/listar_clientes"; 
        }catch(Exception e){
          return "/listar_clientes"; 
        }
    }
    
    @GetMapping("/editar_cliente/{id}")
    public String editarCliente(ModelMap model, @PathVariable String id){
        
        Optional<Cliente> cliente = clienteServicio.findClienteById(id);
        model.put("cliente", cliente);
        
        return "editar_cliente"; 
    } 
    
    @PostMapping("/modificar_cliente/{id}/{nombre}/{documento}/{telefono}")
    public String modificarCliente(ModelMap model, @PathVariable String id, @RequestParam String nombre, @RequestParam String documento, @RequestParam String telefono){

        try {
            
            Optional<Cliente> cliente = clienteServicio.findClienteById(id);
            
            clienteServicio.editarCliente(id, nombre, documento, telefono);
            model.put("nombre1", cliente.get().getNombre());
            model.put("documento", cliente.get().getDocumento());
            model.put("telefono", cliente.get().getTelefono());
            
        } catch (ErrorServicio ex) {
            
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("documento", documento);
            model.put("telefono", telefono);
            
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/cliente/editar_cliente";
        }
      return "redirect:/cliente/listar_clientes";
    }
    
    @GetMapping("/listar_clientes")
    public String listarClientes(Model model){
        List<Cliente> clientes = clienteServicio.listarClientes();
        model.addAttribute("titulo", "LISTA DE CLIENTES");
        model.addAttribute("clientes", clientes);
        return "listar_clientes";
    }
    
}