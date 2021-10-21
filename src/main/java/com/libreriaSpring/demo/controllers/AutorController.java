package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.entities.Autor;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.services.AutorServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;
    
    //AUTOR
   
    @GetMapping("/cargar_autor")
    public String cargarAutor(){
        return "/cargar_autor";
    }
    
    @PostMapping("/registrar_autor")
    public String registrarAutor(Model model, @RequestParam String nombre){
        
        try {
            autorServicio.guardarAutor(nombre);
        } catch (ErrorServicio ex) {
            model.addAttribute("errorA", ex.getMessage());
            Logger.getLogger(AutorController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/autor/registrar_autor";
        }
        return "index";
    }
    
    @RequestMapping("/eliminarAutor")
    public String eliminarAutor(@RequestParam String id) throws ErrorServicio{
        try{
          autorServicio.eliminarAutor(id);
        return "redirect:/autor/listar_autores"; 
        }catch(Exception e){
          return "redirect:/autor/listar_autores"; 
        }
    }
    
    @GetMapping("/editar_autor/{id}")
    public String editarAutor(Model model, @PathVariable String id){
        
        Optional<Autor> autor = autorServicio.findAutorById(id);
        model.addAttribute ("autor", autor);

        return "editar_autor"; 
    } 
    
    @PostMapping("/modificar_autor/{id}/{nombre}")
    public String modificarAutor(@PathVariable String id, @RequestParam String nombre){
        
        try {
            autorServicio.editarAutor(id, nombre);
        } catch (ErrorServicio ex) {
            System.out.println("Error al guardar");
        }
      return "redirect:/autor/listar_autores";
    }
    @GetMapping("/listar_autores")
    public String listarAutores(Model model){
        List<Autor> autores = autorServicio.listarAutores();
        model.addAttribute("titulo", "LISTA DE AUTORES");
        model.addAttribute("autores", autores);
        return "listar_autores";
    }
}
