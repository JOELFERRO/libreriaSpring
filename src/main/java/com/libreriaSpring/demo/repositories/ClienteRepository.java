package com.libreriaSpring.demo.repositories;

import com.libreriaSpring.demo.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, String>{

    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    public Cliente buscarPorNombre(@Param("nombre")String nombre);
}