/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Portatil
 */
public class ImportarExportar {
    
    File agenda;
    ValidadorXML validador;

    public ImportarExportar(String nombre) {
        agenda = new File(nombre);
        validador = new ValidadorXML();
    }
    
    public void guardar(Agenda agenda){
        try {
            JAXBContext context = JAXBContext.newInstance(Agenda.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
  "\n<!DOCTYPE Agenda [ \n<!ELEMENT Agenda (Persona)*> \n" +
"<!ELEMENT Persona (name,telephone,email)>\n" +
"<!ELEMENT name (#PCDATA)>\n" +
"<!ELEMENT telephone (#PCDATA)>\n" +
"<!ELEMENT email (#PCDATA)>\n ]>");
            marshaller.marshal(agenda, this.agenda);
            validador.validarDocumento(this.agenda);
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void exportarPersona(Persona persona,String nombreArchivo){
        try {
            JAXBContext context = JAXBContext.newInstance(Persona.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File XMLfile = new File(nombreArchivo+".xml");
            marshaller.marshal(persona, XMLfile);
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Persona importarPersona(File persona){
        try {
            System.out.println("Cargando persona ...");
            JAXBContext jaxbC = JAXBContext.newInstance(Persona.class);
            Unmarshaller jaxbU = jaxbC.createUnmarshaller();
            Persona p = (Persona) jaxbU.unmarshal(persona);
            return p;
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Agenda cargar(File agenda){
        try {
            System.out.println("Cargando agenda ...");
            JAXBContext jaxbC = JAXBContext.newInstance(Agenda.class);
            Unmarshaller jaxbU = jaxbC.createUnmarshaller();
            Agenda a = (Agenda) jaxbU.unmarshal(agenda);
            return a;
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Agenda comprobarSiExisteAgenda(){
        if(agenda.exists() && !agenda.isDirectory()){
            return cargar(agenda);
        }
        return null;
    }

    public ValidadorXML getValidador() {
        return validador;
    }
    
    
}
