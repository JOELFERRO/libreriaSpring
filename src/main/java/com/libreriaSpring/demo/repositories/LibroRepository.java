
package com.libreriaSpring.demo.repositories;

import com.libreriaSpring.demo.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, String>{
    
    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo")String titulo);
}
