package com.libreriaSpring.demo.services;

import com.libreriaSpring.demo.entities.Cliente;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.repositories.ClienteRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio{
    
    @Autowired 
    private ClienteRepository clienteRepository;
    
    @Transactional
    public void guardarCliente(String nombre, Long documento, String telefono) throws ErrorServicio{
        
        validacion(nombre, documento, telefono);
        
        Cliente cliente = new Cliente();
        
        cliente.setNombre(nombre);
        cliente.setDocumento(documento);
        cliente.setTelefono(telefono);
        cliente.setAlta(Boolean.TRUE);
        
        clienteRepository.save(cliente);
        
    }
    
    private void validacion(String nombre, Long documento, String telefono)  throws ErrorServicio{
        /*Validaciones*/
        if (nombre == null || nombre.isEmpty()) {
            System.out.println("El nombre del cliente no puede ser nulo o estar vacío.");
            throw new ErrorServicio("El nombre del cliente no puede ser nulo o estar vacío.");
        }else if (documento == null || documento.toString().isEmpty()) {
            System.out.println("El documento no puede ser nulo o estar vacío.");
            throw new ErrorServicio("El documento no puede ser nulo o estar vacío.");
        }else if (telefono == null || telefono.isEmpty()) {
            System.out.println("El teléfono no puede ser nulo o estar vacío.");
            throw new ErrorServicio("El teléfono no puede ser nulo o estar vacío.");
        }
    }
}