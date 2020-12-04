/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Serialization;

import Server.Model.CustomerList;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;


public class Deserializer {
    
    static JAXBContext jc;
    
    public CustomerList deserailizeCustomer(String serializedObject){
        try {
            byte b[] = Base64.getDecoder().decode(serializedObject.getBytes());
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            CustomerList obj = (CustomerList) si.readObject();
            return obj;
        } catch (Exception e) {
            Logger.getLogger(Deserializer.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    } 
    
    public <T> ObservableList<T> unmarshal(Class c, String content) {
        try {            
            jc = JAXBContext.newInstance(c, WrapperList.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource xml = new StreamSource(new ByteArrayInputStream(content.getBytes()));
            WrapperList<T> wrapper = (WrapperList<T>) unmarshaller.unmarshal(xml, WrapperList.class).getValue();
            return (ObservableList<T>) wrapper.getItems();
        } catch (Exception ex) {
            System.out.println("Problem with unmarshalling the file");
            Logger.getLogger(WrapperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
