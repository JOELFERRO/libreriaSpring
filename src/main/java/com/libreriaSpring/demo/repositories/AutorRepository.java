package com.libreriaSpring.demo.repositories;

import com.libreriaSpring.demo.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutorRepository extends JpaRepository<Autor, String>{
    
    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre")
    public Autor buscarPorNombre(@Param("nombre")String nombre);
}
