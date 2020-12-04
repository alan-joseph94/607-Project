
package Client.Serialization;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;



public class WrapperDAO {
    
    static JAXBContext jc;
    
    static String marshal(Class c, ObservableList<?> list, String name) {
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

    static <T> ObservableList<T> unmarshal(Class c, String content) {
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

    public static <T> void add(T obj, Class c, String filename, String rootNodeName) {
        ObservableList<T> list = getList(c, filename);
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(obj);
        create(c, list, rootNodeName);
    }

    public static String create(Class c, ObservableList<?> list, String rootNodeName) {
        return marshal(c, list, rootNodeName);
    }

    public static <T> ObservableList<T> getList(Class c, String filename) {
        ObservableList<T> list = null;
        try {
            list = unmarshal(c, filename);
        } catch (Exception ex) {
            System.out.println("Error creating file : WrapperDAO");
            Logger.getLogger(WrapperDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return list;
    }
}
