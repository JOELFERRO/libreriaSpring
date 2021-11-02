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
    
    
    @GetMapping("/cargar_libro")
    public String cargarLibro(ModelMap model){
        
        //Estas list son para la opción de elegir un autor y editorial que ya se encuentre en la base de datos
        List<Autor> autores = autorRep.findAll();
        model.put("autores", autores);
        List<Editorial> editoriales = editorialRep.findAll();
        model.put("editoriales", editoriales);
        
        return "cargar_libro";
    }
    
    @PostMapping("/guardar_libro")
    public String guardarLibro(ModelMap model, @RequestParam String titulo, @RequestParam Autor autor, @RequestParam Editorial editorial, @RequestParam Integer stock){
    
        
        try {    
//            //Validamos el ingreso del autor
//            autorServicio.guardarAutor(nombreAutor);
//            Autor autor = autorRep.getById(autorRep.buscarPorNombre(nombreAutor).getId());
//            //Validamos el ingreso de la editorial
//            editorialServicio.guardarEditorial(nombreEditorial);
//            Editorial editorial =  editorialRep.getById(editorialRep.buscarPorNombre(nombreEditorial).getId());
            
            libroServicio.crearLibro(titulo, autor, editorial, stock);
            
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("titulo", titulo);
//            model.put("autor", autor);
//            model.put("editorial", editorial);
            model.put("stock", stock);
            
            Logger.getLogger(EditorialController.class.getName()).log(Level.SEVERE, null, ex);
            return "/cargar_libro";
        }
        return "index";
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
    public String editarLibro(ModelMap model, @PathVariable String id){
        
        Optional<Libro> libro = libroServicio.findEditorialById(id);
        model.put ("libro", libro);
        List<Autor> autores = autorRep.findAll();
        model.put("autores", autores);
        List<Editorial> editoriales = editorialRep.findAll();
        model.put("editoriales", editoriales);
        
        return "editar_libro"; 
    } 
    
    @PostMapping("/modificar_libro/{id}/{titulo}/{nombreAutor}/{nombreEditorial}/{stock}")
    public String modificarLibro(@PathVariable String id, @RequestParam String titulo, @RequestParam Autor autor, @RequestParam Editorial editorial, @RequestParam Integer stock){
        
        try {
            //1° Creo un objeto Libro similar al original; para poder traer el autor y la editorial.
                Libro libro = libroRep.getById(id);
//            //2° Creo un objeto Autor similar al original para poder usar el id en el método "editarAutor"
//                Autor autor = libro.getAutor();
//            //3° Este método setea el nombre del autor original por el nuevo(nombreAutor)
//                autorServicio.editarAutor(autor.getId(), nombreAutor);
//            //4° Hago lo mismo con Editorial
//                Editorial editorial = libro.getEditorial();
//                editorialServicio.editarEditorial(editorial.getId(), nombreEditorial);
            //5°
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
