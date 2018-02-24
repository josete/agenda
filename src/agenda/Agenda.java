/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Portatil
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="agenda")
public class Agenda implements Serializable{
    
    @XmlElement(name = "persona")
    ArrayList<Persona> personas;

    public Agenda() {
        personas=new ArrayList<>();
    }

    public Agenda(ArrayList<Persona> personas) {
        this.personas = personas;
    }
    
    public void anadirPersona(Persona persona){
        personas.add(persona);
    }
}
