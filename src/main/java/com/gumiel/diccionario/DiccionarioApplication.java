package com.gumiel.diccionario;

import com.gumiel.diccionario.entities.Diccionario;
import com.gumiel.diccionario.repositories.DiccionarioRepository;
import com.gumiel.diccionario.service.DiccionarioService;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
public class DiccionarioApplication {


	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(DiccionarioApplication.class, args);
	}



}
