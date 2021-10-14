package com.libreriaSpring.demo.repositories;

import com.libreriaSpring.demo.entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EditorialRepository extends JpaRepository<Editorial, String>{

    @Query("SELECT c FROM Editorial c WHERE c.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre")String nombre);
}
