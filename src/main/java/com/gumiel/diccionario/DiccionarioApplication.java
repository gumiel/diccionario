package com.gumiel.diccionario;

import com.gumiel.diccionario.entities.Diccionario;
import com.gumiel.diccionario.repositories.DiccionarioRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DiccionarioApplication {




	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(DiccionarioApplication.class, args);







	}



}
