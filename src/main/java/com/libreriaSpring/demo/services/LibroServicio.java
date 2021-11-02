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
        
        String stockQ = stock.toString();
        validacion(titulo, stockQ, stock);
        
        Libro libro = new Libro();
        
        libro.setTitulo(titulo.toUpperCase());
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
        
        String stockQ = stock.toString();
        validacion(titulo, stockQ, stock);
        
        Libro libro = libroRepository.findById(id).get();
        
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setStock(stock);
        
        libroRepository.save(libro);        
    } 
    
     private void validacion(String titulo, String stockQ, Integer stock) throws ErrorServicio{
        /*Validaciones*/

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("No ha ingresado el título del libro.");            
        }
        if (stockQ == null || stockQ.isEmpty()) {
            throw new ErrorServicio("No ha ingresado el stock del libro.");
        }else if(stock<=0){
            throw new ErrorServicio("El stock no debe ser igual o inferior a cero.");
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
