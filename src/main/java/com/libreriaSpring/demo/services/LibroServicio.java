package com.libreriaSpring.demo.services;

import com.libreriaSpring.demo.entities.Autor;
import com.libreriaSpring.demo.entities.Editorial;
import com.libreriaSpring.demo.entities.Libro;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.repositories.LibroRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Transactional
    public void crearLibro (String titulo, Autor autor, Editorial editorial, Integer stock) throws ErrorServicio{
        
        validacion(titulo);
        
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setStock(stock);
        libro.setPrestados(0);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepository.save(libro);
        
    }
    
    @Transactional
    public void eliminarLibro(String id){
        libroRepository.deleteById(id);
    } 
    
    @Transactional
    public void editarLibro(String id, String titulo, Autor autor, Editorial editorial, Integer stock) throws ErrorServicio{
        
        validacion(titulo);
        
        Libro libro = libroRepository.findById(id).get();
        
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setStock(stock);
        
        libroRepository.save(libro);        
    } 
    
     private void validacion(String titulo) throws ErrorServicio{
        /*Validaciones*/
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título del libro no puede ser nulo o estar vacío.");
        }
    }
    
    @Transactional
    public Optional<Libro> findEditorialById(String id){
        return libroRepository.findById(id);
    }
      public List<Libro> listarLibros(){
        return libroRepository.findAll();
    }
}
