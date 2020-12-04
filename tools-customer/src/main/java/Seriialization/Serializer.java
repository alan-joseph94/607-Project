/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Seriialization;

import Server.Model.Customer;
import Server.Model.CustomerList;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;


public class Serializer {
    
    public String SerializeCustomer(Customer customer){
        // serialize the object
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(customer);
            so.flush();
            String serializedObject = Base64.getEncoder().encodeToString(bo.toByteArray());
            return serializedObject;
        } catch (Exception e) {
            Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public String SerializeCustomer(CustomerList customerList){
        // serialize the object
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(customerList);
            so.flush();
            String serializedObject = Base64.getEncoder().encodeToString(bo.toByteArray());
            return serializedObject;
        } catch (Exception e) {
            Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    static JAXBContext jc;
    
    public String marshal(Class c, ObservableList<?> list, String name) {
        try {            
            StringWriter sw = new StringWriter();
            jc = JAXBContext.newInstance(c, WrapperList.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            QName qName = new QName(name);
            WrapperList wrapper = new WrapperList(list);
            JAXBElement<WrapperList> jaxbElement = new JAXBElement<>(qName, WrapperList.class, wrapper);
            marshaller.marshal(jaxbElement, sw);
            String result = sw.toString();
            return result;
        } catch (Exception ex) {
            System.out.println("Problem with marshalling to file");
            Logger.getLogger(WrapperList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
