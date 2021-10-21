package com.libreriaSpring.demo.services;

import com.libreriaSpring.demo.entities.Cliente;
import com.libreriaSpring.demo.entities.Libro;
import com.libreriaSpring.demo.entities.Prestamo;
import com.libreriaSpring.demo.repositories.PrestamoRepository;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Transactional
    public void registrarPrestamo(Date entrega, Date devolucion, Libro libro, Cliente cliente){
        
        Prestamo prestamo = new Prestamo();
        
        prestamo.setCliente(cliente);
        prestamo.setLibro(libro);
        prestamo.setEntrega(entrega);
        prestamo.setDevolucion(devolucion);
        prestamo.setAlta(Boolean.TRUE);
        
        prestamoRepository.save(prestamo);
                
    }
}