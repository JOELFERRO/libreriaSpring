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
        
        autor.setNombre(nombre.toUpperCase());
        Date hoy = new Date();
        autor.setAlta(hoy);
        
        if (autorRepository.findAll().contains(autorRepository.buscarPorNombre(nombre))) {
            throw new ErrorServicio("El Autor ya se encuentra registrado");
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
            throw new ErrorServicio("No ha ingresado el nombre.");
        }
    }
}
