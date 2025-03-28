package com.gumiel.diccionario.service;

import com.gumiel.diccionario.entities.Diccionario;
import com.gumiel.diccionario.repositories.DiccionarioRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DiccionarioService {

    private static final String GITLAB_API_URL = "https://gitlab.et.bo/api/v4";
    private static final String PROJECT_ID = "714"; // ID del proyecto
    private static final String PAGE_SLUG = "home"; // Nombre de la página (URL encoded)
    private static final String PRIVATE_TOKEN = "glpat-9xTYytYuC855xxzX86Ng";

    private final DiccionarioRepository diccionarioRepository;

    public void run() throws ClassNotFoundException, IOException {

        StringBuilder tablaMd = new StringBuilder();

        List<String> listEntities = listClassesInPackage("src/main/java/com/gumiel/diccionario/entities");

        for (String nameEntity: listEntities){
            tablaMd.append(createTableMarkDown(nameEntity));
        }

        this.sendToGitlabWiki(tablaMd);

    }

    public StringBuilder createTableMarkDown(String className) throws ClassNotFoundException {

        StringBuilder tablaMd = new StringBuilder();


        Class<?> clazz = null;
        if (!className.contains(".")) {
            clazz = Class.forName("com.gumiel.diccionario.entities."+className);
        }else{
            clazz = Class.forName(className);
        }

        tablaMd.append("  \n");
        tablaMd.append("Nombre de tabla: "+clazz.getSimpleName()+"  \n");
        tablaMd.append("| Tipo de dato | Nombre de campo | Descripcion del campo |\n");
        tablaMd.append("|:----------|:-------------|:------|\n");

        for (var field : clazz.getDeclaredFields()) {

            Column column = null;
            Schema schema = null;

            for (var annotation : field.getAnnotations()) {

                if (annotation instanceof Column)
                    column = (Column) annotation;

                if (annotation instanceof Schema)
                    schema = (Schema) annotation;

            }

            String fieldType = field.getType().getSimpleName();
            String fieldName = (column!=null)? (!Objects.equals(column.name(), ""))? column.name(): field.getName(): field.getName();
            String fieldDescription = (schema!=null)? schema.description(): "";
            tablaMd.append("| ").append(fieldType).append(" |").append(fieldName).append("|").append(fieldDescription).append("|\n");

        }
        tablaMd.append("  \n");
        return tablaMd;
    }

    /**
     * Devuelve la lista de todas las tablas en la carpeta de entidades
     * @param directoryName Directorio
     * @return Lista de entidades
     * @throws ClassNotFoundException
     */
    public static List<String> listClassesInPackage(String directoryName) throws ClassNotFoundException {
        File directory = new File(directoryName);
        List<String> resultList = new ArrayList<>();

        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    String packageName = directoryName.replace("src/main/java/", "").replace("/", ".");
                    resultList.add(packageName + "." + file.getName().replace(".java", ""));
                } else if (file.isDirectory()) {
                    resultList.addAll(listClassesInPackage(file.getAbsolutePath()));
                }
            }
        }
        return resultList;
    }

    public void sendToGitlabWiki(StringBuilder stringMD) throws IOException {

        URL url = new URL(GITLAB_API_URL + "/projects/" + PROJECT_ID + "/wikis/" + PAGE_SLUG);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("PRIVATE-TOKEN", PRIVATE_TOKEN);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // Datos a enviar (contenido nuevo)
        String postData = "content=" + String.valueOf(stringMD);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes());
        }

        // Respuesta del servidor
        int responseCode = conn.getResponseCode();
        System.out.println("Código de respuesta: " + responseCode);

    }
}
