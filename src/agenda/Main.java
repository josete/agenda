/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Portatil
 */
public class Main {

    static Agenda a;
    static ImportarExportar guardador;
    static Scanner scanner;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        guardador = new ImportarExportar("Agenda.xml");
        a = guardador.comprobarSiExisteAgenda();
        if (a == null) {
            a = new Agenda();
        }
        scanner = new Scanner(System.in);
        crearMenu();
    }

    public static void crearMenu() {
        System.out.println("1.  Añadir contacto");
        System.out.println("2.  Guardar");
        System.out.println("3.  Exportar persona");
        System.out.println("4.  Importar persona");
        System.out.println("5.  Comprobar agenda");
        System.out.println("6.  Salir");
        int opcion = scanner.nextInt();
        comprobar(opcion);
    }

    public static void comprobar(int opcion) {
        switch (opcion) {
            case 1:
                crearContacto();
                break;
            case 2:
                guardar();
                break;
            case 3:
                exportarPersona();
            case 4:
                importarPersona();
            case 5:
                System.exit(0);
        }
    }

    public static void crearContacto() {
        System.out.println("Nombre: ");
        String nombre = scanner.next();
        System.out.println("Telefono: ");
        String telefono = scanner.next();
        System.out.println("email: ");
        String email = scanner.next();
        Persona p = new Persona(nombre, telefono, email);
        a.anadirPersona(p);
        System.out.println("El contacto ha sido creado, no olvides guardar la agenda");
        crearMenu();
    }

    public static void guardar() {
        guardador.guardar(a);
        crearMenu();
    }

    private static void exportarPersona() {
        int i = 1;
        for (Persona p : a.personas) {
            System.out.println(i + ". " + p.nombre);
            i++;
        }
        System.out.println("Elegir contacto a exportar: ");
        int opcion = scanner.nextInt();
        guardador.exportarPersona(a.personas.get(opcion - 1), a.personas.get(opcion - 1).nombre);
        crearMenu();
    }

    private static void importarPersona() {
        System.out.println("Nombre del archivo: ");
        String nombre = scanner.next();
        try {
            if (nombre.split("\\.")[1].equals("xml")) {                
                a.anadirPersona(guardador.importarPersona(new File(nombre)));
            }
        } catch (ArrayIndexOutOfBoundsException e) {            
            a.anadirPersona(guardador.importarPersona(new File(nombre + ".xml")));
        }
        System.out.println("El contacto ha sido importado, no olvides guardar la agenda");
        crearMenu();
    }

}
