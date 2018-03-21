/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import com.saxonica.xqj.SaxonXQDataSource;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Node;

/**
 *
 * @author Portatil
 */
public class EjecutorXQuery {

    public Persona buscarPorNombre(File agenda, String nombre) {
        Persona p = null;
        try {
            //Create Query
            String query = "doc(\"" + agenda.getName() + "\")/Agenda/Persona[name='" + nombre + "']";
            XQDataSource xqjd = new SaxonXQDataSource();
            XQConnection xqjc = xqjd.getConnection();
            XQExpression expr = xqjc.createExpression();

            XQResultSequence result = expr.executeQuery(query);
            while (result.next()) {
                //System.err.println(result.getItemAsString(null));
                p = convertirPersona(result.getNode());
            }
            result.close();
            expr.close();
            xqjc.close();
        } catch (XQException ex) {
            Logger.getLogger(EjecutorXQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public Persona convertirPersona(Node node) {
        Persona p = null;
        try {
            JAXBContext jaxbC = JAXBContext.newInstance(Persona.class);
            Unmarshaller jaxbU = jaxbC.createUnmarshaller();
            p = (Persona) jaxbU.unmarshal(node);
            return p;
        } catch (JAXBException ex) {
            Logger.getLogger(EjecutorXQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
}
