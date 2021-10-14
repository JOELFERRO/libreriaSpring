package com.libreriaSpring.demo.controllers;

import com.libreriaSpring.demo.entities.Autor;
import com.libreriaSpring.demo.entities.Editorial;
import com.libreriaSpring.demo.entities.Libro;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.repositories.AutorRepository;
import com.libreriaSpring.demo.repositories.EditorialRepository;
import com.libreriaSpring.demo.repositories.LibroRepository;
import com.libreriaSpring.demo.services.AutorServicio;
import com.libreriaSpring.demo.services.EditorialServicio;
import com.libreriaSpring.demo.services.LibroServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroController {
    
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private LibroRepository libroRep;
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private EditorialRepository editorialRep;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private AutorRepository autorRep;
    
    
    
    @PostMapping("/guardarLibro")
    public String guardarLibro(Model model, @RequestParam String titulo, @RequestParam String nombreAutor, @RequestParam String nombreEditorial, @RequestParam Integer stock){
    
        try {    
            autorServicio.guardarAutor(nombreAutor);
            Autor autor = autorRep.getById(autorRep.buscarPorNombre(nombreAutor).getId());
            editorialServicio.guardarEditorial(nombreEditorial);
            Editorial editorial =  editorialRep.getById(editorialRep.buscarPorNombre(nombreEditorial).getId());
            
            libroServicio.crearLibro(titulo, autor, editorial, stock);
            
            return "index";
        } catch (ErrorServicio ex) {
            model.addAttribute("errorE", ex.getMessage());
            java.util.logging.Logger.getLogger(EditorialController.class.getName()).log(Level.SEVERE, null, ex);
            return "index";
        }
    }
    
    @RequestMapping("/eliminarLibro")
    public String eliminarLibro(String id){
        try{
          libroServicio.eliminarLibro(id);
        return "redirect:/libro/listar_libros"; 
        }catch(Exception e){
          return "redirect:/libro/listar_libros"; 
        }
    }
    
    @GetMapping("/editar_libro/{id}")
    public String editarLibro(Model model, @PathVariable String id){
        
        Optional<Libro> libro = libroServicio.findEditorialById(id);
        model.addAttribute ("libro", libro);

        return "editar_libro"; 
    } 
    
    @PostMapping("/modificar_libro/{id}/{titulo}/{nombreAutor}/{nombreEditorial}/{stock}")
    public String modificarLibro(@PathVariable String id, @RequestParam String titulo, @RequestParam String nombreAutor, @RequestParam String nombreEditorial, @RequestParam Integer stock){
        
        try {
            String autorAnterior = libroRep.findById(id);
            Autor autor = autorRep.getById(autorRep.buscarPorNombre(nombreAutor).getId());
            autorServicio.editarAutor(autor.getId(), nombreAutor);
            
            Editorial editorial =  editorialRep.getById(editorialRep.buscarPorNombre(nombreEditorial).getId());
            editorialServicio.editarEditorial(editorial.getId(), nombreEditorial);
            
            libroServicio.editarLibro(id, titulo, autor, editorial, stock);
        } catch (ErrorServicio ex) {
//            System.out.println("Error al guardar");
        }
      return "redirect:/libro/listar_libros";
    }
    
     @GetMapping("/listar_libros")
    public String listarLibros(Model model){
        List<Libro> libros = libroServicio.listarLibros();
        model.addAttribute("titulo", "LISTA DE LIBROS");
        model.addAttribute("libros", libros);
        return "listar_libros";        
    }
    
}
