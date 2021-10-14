package com.libreriaSpring.demo.services;

import com.libreriaSpring.demo.entities.Editorial;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.repositories.EditorialRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepository editorialRepository;
    
    @Transactional
    public void guardarEditorial(String nombre) throws ErrorServicio{
        
        validacion(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        Date hoy = new Date();
        editorial.setAlta(hoy);
        
        if (editorialRepository.findAll().contains(editorialRepository.buscarPorNombre(nombre))) {
            System.out.println("La editorial ya se encuentra registrada.");
        }else{
            editorialRepository.save(editorial);  
            
        }
        
    }
    
    @Transactional
    public void eliminarEditorial(String id){
        editorialRepository.deleteById(id);
    } 
    
    @Transactional
    public void editarEditorial(String id, String nombre) throws ErrorServicio{
        
        validacion(nombre);
        
        Editorial editorial = editorialRepository.findById(id).get();
        editorial.setNombre(nombre);
        
        editorialRepository.save(editorial);        
    } 

    @Transactional
    public Optional<Editorial> findEditorialById(String id){
        return editorialRepository.findById(id);
    }
    
    public List<Editorial> listarEditoriales(){
        return editorialRepository.findAll();
    }
    
    private void validacion(String nombre)  throws ErrorServicio{
        /*Validaciones*/
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la Editorial no puede ser nulo o estar vacío.");
        }
    }
}
