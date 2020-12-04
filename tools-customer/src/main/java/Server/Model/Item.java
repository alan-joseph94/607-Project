/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "item")
@XmlAccessorType (XmlAccessType.FIELD)
public class Item {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private String type;    
    private String powerType;
    private int supplierId;

    public Item() {
    }

    public Item(int id, String name, int quantity, double price, String type, String powerType, int supplierId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.powerType = powerType;
        this.supplierId = supplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String supplierName) {
        this.powerType = supplierName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    
    @Override
    public String toString() {
        return String.format("%-12d %-16s %-20s %-20s %-10.2f %-8d %-12s", getId(), getName(), getType(), getPowerType(), getPrice(), getQuantity(), getSupplierId());
    }
}
