package com.gumiel.diccionario.controllers;

import com.gumiel.diccionario.service.DiccionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/diccionario")
@Tag(name = "Cache", description = "Control del cache del sistema.")
public class DiccionarioController {

    private final DiccionarioService diccionarioService;

    @GetMapping
    public ResponseEntity<String> cleanCache() throws ClassNotFoundException, IOException {
        diccionarioService.run();
        return ResponseEntity.status(HttpStatus.OK).body("(Refresh cache) Updated cache throughout the system");
    }

}
