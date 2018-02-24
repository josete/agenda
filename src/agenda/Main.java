/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

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
        a = new Agenda();
        guardador = new ImportarExportar("Agenda.xml");
        scanner = new Scanner(System.in);  
        crearMenu();
    }
    
    public static void crearMenu(){
        System.out.println("1.  Añadir contacto");
        System.out.println("2.  Guardar");
        System.out.println("3.  Salir");
        int opcion = scanner.nextInt();
        comprobar(opcion);
    }
    
    public static void comprobar(int opcion){
        switch(opcion){
            case 1:
                crearContacto();
                break;
            case 2:
                guardar();
                break;
            case 3:
                System.exit(0);
        }
    }
    
    public static void crearContacto(){
        System.out.println("Nombre: ");
        String nombre = scanner.next();
        System.out.println("Telefono: ");
        String telefono = scanner.next();
        System.out.println("email: ");
        String email = scanner.next();
        Persona p = new Persona(nombre, telefono, email);
        a.anadirPersona(p);
        crearMenu();
    }
    
    public static void guardar(){
        guardador.guardar(a);
        crearMenu();
    }
    
}
