/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Portatil
 */
public class Main {

    static Agenda a;
    static ImportarExportar guardador;
    static EjecutorXPath ejecutorXPath;
    static EjecutorXQuery ejecutorXQuery;
    static Scanner scanner;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        guardador = new ImportarExportar("Agenda.xml");
        ejecutorXPath = new EjecutorXPath();
        ejecutorXQuery = new EjecutorXQuery();
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
        System.out.println("5.  Listar nombres");
        System.out.println("6.  Ejecutar sentencia XPath");
        System.out.println("7.  Buscar por nombre");
        System.out.println("8.  Validar agenda");
        System.out.println("9.  Salir");
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
                listarNombres();
            case 6:
                ejecutarSentencia();
            case 7:
                buscarPorNombre();
            case 8:
                validarAgenda();
            case 9:
                System.exit(0);
        }
    }

    public static void crearContacto() {
        System.out.println("Nombre: ");
        String nombre = scanner.next();
        System.out.println("Telefono: ");
        String telefono = scanner.next();
        while (!comprobarNumero(telefono)) {
            System.out.println("El numero de telefono no es valido, vuelve a introducirlo: ");
            telefono = scanner.next();
        }
        System.out.println("email: ");
        String email = scanner.next();
        if(!comprobarEmail(email)){
            System.out.println("El email introducido no es valido, vuelve a introducirlo: ");
            email = scanner.next();
        }
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

    private static void listarNombres() {
        ArrayList<String> nombres = ejecutorXPath.listarNombre(guardador.agenda);
        for (String s : nombres) {
            System.out.println("-- " + s);
        }
        System.out.println(".................");
        crearMenu();
    }

    private static void ejecutarSentencia() {
        System.out.println("Introduce la sentencia: ");
        String sentencia = scanner.next();
        ArrayList<String> nombres = ejecutorXPath.ejecutarSentencia(guardador.agenda, sentencia);
        for (String s : nombres) {
            System.out.println("-- " + s);
        }
        System.out.println(".................");
        crearMenu();
    }

    private static void buscarPorNombre() {
        System.out.println("Introduce el nombre de la persona: ");
        String nombre = scanner.next();
        Persona p = ejecutorXQuery.buscarPorNombre(guardador.agenda, nombre);
        if(p!=null){
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Telefono: " + p.getTelefono());
            System.out.println("Email: " + p.getEmail());
            System.out.println("..................");
        }else{
            System.out.println("No Existe una persona con ese nombre");
        }
        crearMenu();
    }
    
    private static void validarAgenda(){
        System.out.println("Nombre del archivo: ");
        String nombre = scanner.next();
        try {
            if (nombre.split("\\.")[1].equals("xml")) {
                guardador.getValidador().validarDocumento(new File(nombre));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            guardador.getValidador().validarDocumento(new File(nombre+".xml"));
        }        
        crearMenu();
    }

    private static boolean comprobarNumero(String numero) {
        Pattern patron = Pattern.compile("\\d{9}");
        Matcher valido = patron.matcher(numero);
        return valido.matches();
    }
    
    private static boolean comprobarEmail(String email) {
        Pattern patron = Pattern.compile("[\\-a-zA-Z0-9\\.\\+]+@[a-zAZ0-9](\\.?[\\-a-zA-Z0-9]*[a-zA-Z0-9])*");
        Matcher valido = patron.matcher(email);
        return valido.matches();
    }

}
