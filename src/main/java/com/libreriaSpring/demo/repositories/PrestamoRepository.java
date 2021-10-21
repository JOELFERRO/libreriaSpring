package com.libreriaSpring.demo.repositories;

import com.libreriaSpring.demo.entities.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestamoRepository extends JpaRepository<Prestamo, String>{

    @Query("SELECT c FROM Prestamo c WHERE c.cliente = :nombre")
    public Prestamo buscarPorNombre(@Param("nombre")String nombre);
}