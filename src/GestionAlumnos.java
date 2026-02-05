import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


public  class GestionAlumnos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, List<Double>> alumnos = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("../alumnos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null ) {
                String[] dato = linea.split(";"); //split "corta" por el indicador, en este caso ;

                if(dato.length == 2) {
                    String nombre = dato[0];
                    double nota = Double.parseDouble(dato[1]);

                    alumnos.putIfAbsent(nombre, new ArrayList<>()); //si no existe la clave(nombre) en el mapa la crea y si existe añade la nota a la clave
                    alumnos.get(nombre).add(nota);

                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " +
                    e.getMessage());
        }

        for (String nombre : alumnos.keySet()) {
            System.out.println(nombre + ": " + alumnos.get(nombre));
        }

        //Calcular media de cada alumno
        System.out.println("MEDIAS DE CADA ALUMNO");
        Map<String, Double> notasMedias = new HashMap<>();
        for (String nombre : alumnos.keySet()) {
            notasMedias.put(nombre, calcularMedia(alumnos.get(nombre)));

        }
        for (String nombre : notasMedias.keySet()) {
            System.out.println(nombre + ": " + notasMedias.get(nombre));
        }

        //Mostrar alumnos aprobados
        System.out.println("ALUMNOS APROBADOS POR NOTA MEDIA");
        Set<String> aprobados = new HashSet<>();
        for (String nombre : notasMedias.keySet()) {
            if (notasMedias.get(nombre) >= 5) {
                aprobados.add(nombre);
            }
        }

        for (String nombre : aprobados) {
            System.out.println(nombre);
        }

        //Listado de alumnos ordenados por nota media
        System.out.println("ALUMNOS ORDENADOS POR NOTA MEDIA");
        List<Map.Entry<String, Double>> alumnosOrdenados = new ArrayList<>(notasMedias.entrySet());
        alumnosOrdenados.sort(Map.Entry.comparingByValue()); //de menor a mayor
        alumnosOrdenados.sort(Map.Entry.comparingByValue(Collections.reverseOrder())); //de mayor a menor

        for (var entrada : alumnosOrdenados) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        }

        //Alumno con mejor nota media
        System.out.println("ALUMNO CON MEJOR NOTA MEDIA");
        String nomb = "";
        double peorNota = -1.0;
        for (String nombre : notasMedias.keySet()) {
            System.out.println(nombre + ": " + notasMedias.get(nombre));
            if (notasMedias.get(nombre) >= peorNota) {
                peorNota = notasMedias.get(nombre);
                nomb = nombre;
            }
        }
        System.out.println(nomb);



    }
    public static double calcularMedia(List<Double> notas){
        Iterator<Double> it = notas.iterator();
        double media = 0;
        int totalNotas = 0;

        while (it.hasNext()) {
            media += it.next();
            totalNotas++;
        }
        media /= totalNotas;
        return media;
    }
}