package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.entities.Editorial;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.services.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServicio;
    
    //EDITORIAL
    
    @GetMapping("/cargar_editorial")
    public String cargarEditorial(){
        return "/cargar_editorial";
    }
    
    @PostMapping("/registrar_editorial")
    public String registrarEditorial(ModelMap model, @RequestParam String nombre) {
        
        try {    
            editorialServicio.guardarEditorial(nombre);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            
            Logger.getLogger(EditorialController.class.getName()).log(Level.SEVERE, null, ex);
            return "/cargar_editorial";
        }
        return "index";
    }
    
    @RequestMapping("/eliminarEditorial")
    public String eliminarEditorial(String id) throws ErrorServicio{
        try{
          editorialServicio.eliminarEditorial(id);
        return "redirect:/editorial/listar_editorial"; 
        }catch(Exception e){
          return "redirect:/editorial/listar_editorial"; 
        }
    }
    
    @GetMapping("/editar_editorial/{id}")
    public String editarEditorial(Model model, @PathVariable String id){
        
        Optional<Editorial> editorial = editorialServicio.findEditorialById(id);
        model.addAttribute ("editorial", editorial);

        return "editar_editorial"; 
    } 
    
    @PostMapping("/modificar_editorial/{id}/{nombre}")
    public String modificarEditorial(@PathVariable String id, @RequestParam String nombre){
        System.out.println("esta ingresando al metodo " + id);
        try {
            editorialServicio.editarEditorial(id, nombre);
        } catch (ErrorServicio ex) {
            System.out.println("Error al guardar");
        }
      return "redirect:/editorial/listar_editorial";
    }
    
    @GetMapping("/listar_editorial")
    public String listarEditorial(Model model){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("titulo", "LISTA DE EDITORIAL");
        model.addAttribute("editoriales", editoriales);
        return "listar_editorial";        
    }
}