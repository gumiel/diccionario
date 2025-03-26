package com.gumiel.diccionario.repositories;

import com.gumiel.diccionario.entities.Diccionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DiccionarioRepository extends JpaRepository<Diccionario, Integer> {
}
