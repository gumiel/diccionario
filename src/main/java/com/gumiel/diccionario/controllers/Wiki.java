package com.gumiel.diccionario.controllers;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cache")
public class Wiki {

  private static final String GITLAB_API_URL = "https:///api/v4";
  private static final String PROJECT_ID = "714"; // ID del proyecto
  private static final String PAGE_SLUG = "home"; // Nombre de la página (URL encoded)
  private static final String PRIVATE_MIO = "11";

  @GetMapping
  public ResponseEntity<String> wikis() throws IOException {
    URL url = new URL(GITLAB_API_URL + "/projects/" + PROJECT_ID + "/wikis/" + PAGE_SLUG);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("PUT");
    conn.setRequestProperty("PRIVATE-MIO", PRIVATE_MIO);
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setDoOutput(true);

    // Datos a enviar (contenido nuevo)
    String postData = "content=" + "Nuevo contenido de la página Wiki";
    try (OutputStream os = conn.getOutputStream()) {
      os.write(postData.getBytes());
    }

    // Respuesta del servidor
    int responseCode = conn.getResponseCode();
    System.out.println("Código de respuesta: " + responseCode);
    return ResponseEntity.status(HttpStatus.OK).body("(Refresh cache) Updated cache throughout the system");
  }

}
/*
curl --request PUT --header "PRIVATE-TOKEN: TU_ACCESS_TOKEN" \
     --data "content=Nueva información en la página de la Wiki" \
     "https://gitlab.com/api/v4/projects/12345/wikis/mi-pagina"
 */