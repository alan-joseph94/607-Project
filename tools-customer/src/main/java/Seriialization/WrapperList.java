
package Seriialization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlAnyElement;


public class WrapperList<T> {

    private ObservableList<T> items;

    public WrapperList() {
        items = FXCollections.observableArrayList();
    }

    public WrapperList(ObservableList<T> items) {
        this.items = items;
    }

    @XmlAnyElement(lax = true)
    public ObservableList<?> getItems() {
        return items;
    }
}
