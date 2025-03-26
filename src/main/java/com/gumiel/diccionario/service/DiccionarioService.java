package com.gumiel.diccionario.service;

import com.gumiel.diccionario.entities.Diccionario;
import com.gumiel.diccionario.repositories.DiccionarioRepository;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiccionarioService {

    private final DiccionarioRepository diccionarioRepository;

    public void run() throws ClassNotFoundException {
        String className = "Product";

        listClassesInPackage("src/main/java/com/gumiel/diccionario/entities");

        Class<?> clazz = null;
        if (!className.contains(".")) {
            clazz = Class.forName("com.gumiel.diccionario.entities."+className);
        }else{
            clazz = Class.forName(className);
        }

        for (var field : clazz.getDeclaredFields()) {

            List<String> anottationList = new ArrayList<>();
            for (var annotation : field.getAnnotations()) {
                String anottationString = annotation.toString()+"\n";
                anottationList.add(anottationString);
                System.out.println(anottationString);

                if (annotation instanceof Column) {
                    Column column = (Column) annotation;
                    System.out.println("Column Name: " + column.name());

                    Diccionario diccionario = new Diccionario();
                    diccionario.setTabla("className");
                    diccionario.setTipoCampo("generico");
                    diccionario.setNombreCampo(field.getType().getName());
                    diccionario.setDescripcionCampo(field.getName());
                    diccionarioRepository.save(diccionario);

                }

            }
            System.out.println(anottationList);
            System.out.println(field.getType().getSimpleName());
            System.out.println(field.getName());
        }
    }
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

        for (String s : resultList) {
            System.out.println(s);
        }
        return resultList;
    }
}
