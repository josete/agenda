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

/**
 *
 * @author Portatil
 */
public class ImportarExportar {
    
    File agenda;

    public ImportarExportar(String nombre) {
        agenda = new File(nombre);
    }
    
    public void guardar(Agenda agenda){
        try {
            JAXBContext context = JAXBContext.newInstance(Agenda.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(agenda, this.agenda);
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
