/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jdk.nashorn.internal.ir.BreakNode;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Familia
 */
public class ValidadorXML {

    boolean correcto = true;
    public void validarDocumento(File documento) {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.err.println("El documento no es correcto");
                    incorrecto();
                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.err.println("El documento no es correcto");
                    incorrecto();
                }
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    
                }
            });
            Document doc = builder.parse(documento);  
            if(correcto){
                System.out.println("Documento correcto");
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ValidadorXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void incorrecto(){
        correcto = false;
    }
}
