package com.libreriaSpring.demo.services;

import com.libreriaSpring.demo.entities.Autor;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.repositories.AutorRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Transactional
    public void guardarAutor(String nombre) throws ErrorServicio{
        
        validacion(nombre);
        
        Autor autor = new Autor();
        autor.setNombre(nombre);
        Date hoy = new Date();
        autor.setAlta(hoy);
        
        if (autorRepository.findAll().contains(autorRepository.buscarPorNombre(nombre))) {
            System.out.println("El Autor ya se encuentra registrado");
        }else{
            autorRepository.save(autor);   
            
        }
        
    }
    
    @Transactional
    public void eliminarAutor(String id){
        autorRepository.deleteById(id);
    } 
    
    @Transactional
    public void editarAutor(String id, String nombre) throws ErrorServicio{
        
        validacion(nombre);
        
        Autor autor = autorRepository.findById(id).get();
        autor.setNombre(nombre);
        
        autorRepository.save(autor);
    }
    
      @Transactional
    public Optional<Autor> findAutorById(String id){
        return autorRepository.findById(id);
    }
    
    public List<Autor> listarAutores(){
        return autorRepository.findAll();
    }
    
    private void validacion(String nombre)  throws ErrorServicio{
        /*Validaciones*/
        if (nombre == null || nombre.isEmpty()) {
            System.out.println("El nombre del autor no puede ser nulo o estar vacío.");
            throw new ErrorServicio("El nombre del autor no puede ser nulo o estar vacío.");
        }
    }
}
