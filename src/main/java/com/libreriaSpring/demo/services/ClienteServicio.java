package com.libreriaSpring.demo.services;

import com.libreriaSpring.demo.entities.Cliente;
import com.libreriaSpring.demo.exceptions.ErrorServicio;
import com.libreriaSpring.demo.repositories.ClienteRepository;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio{
    
    @Autowired 
    private ClienteRepository clienteRepository;
    
    
    /*Guardar Cliente*/
    @Transactional
    public void guardarCliente(String nombre, String documento, String telefono) throws ErrorServicio{
        
        validacion(nombre, documento, telefono);
        
        Cliente cliente = new Cliente();
        
        cliente.setNombre(nombre);
        cliente.setDocumento(documento);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
   
            if (clienteRepository.findAll().contains(clienteRepository.buscarPorDocumento(documento))) {
                throw new ErrorServicio("El documento ingresado ya se encuentra registrado");
            }else{
                clienteRepository.save(cliente);  
            }
        
    }
    
    /*Lista de Clientes*/
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }
         
    
    /*Validaciones*/
    private void validacion(String nombre, String documento, String telefono) throws ErrorServicio{
       
        Pattern pString = Pattern.compile("^([A-Za-z]+[ ]*){1,3}$");
        Pattern pNum = Pattern.compile("^[0-9]{8}$");
        Pattern pNumTel = Pattern.compile("^[0-9]{8,10}$");
        
        Matcher mNombre = pString.matcher(nombre); 
        Matcher mDocumento = pNum.matcher(documento);
        Matcher mTelefono = pNumTel.matcher(telefono);
        
        System.out.println(mNombre.matches());
        System.out.println(mDocumento.matches());
        System.out.println(mTelefono.matches());
        
        
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("No ha ingresado el nombre.");
        }else if (!mNombre.matches()) {
            throw new ErrorServicio("El nombre del cliente sólo puede contener letras."); 
        }    
        
        
        if (documento == null || documento.isEmpty()) {  
            throw new ErrorServicio("No ha ingresado un documento.");
        }else if (!mDocumento.matches()) {
            throw new ErrorServicio("El documento debe contener 8 dígitos.");
        }
        
       
        if(telefono == null || telefono.isEmpty()) {
            throw new ErrorServicio("No ha ingresado el teléfono.");
        }else if (!mTelefono.matches()) {
            throw new ErrorServicio("El telefono debe contener entre 8 y 10 dígitos.");
        }
    }
}