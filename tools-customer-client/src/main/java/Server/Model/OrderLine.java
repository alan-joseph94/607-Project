/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "orderLine")
@XmlAccessorType (XmlAccessType.FIELD)
public class OrderLine {

    private int itemId;
    private int orderId;
    private int supplierId;
    private int orderQty;
    private Item item;

    public OrderLine() {
    }

    public OrderLine(Item item, int orderId, int orderQty) {
        this.item = item;
        this.itemId = item.getId();
        this.orderId = orderId;
        this.supplierId = item.getSupplierId();
        this.orderQty = orderQty;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public String toString() {
        return "\n*****************************************************\n"+
                String.format("%-25s %-10s", "Item Id:", item.getId()) + "\n" +
                String.format("%-25s %-10s", "Supplier:", getSupplierId()) + "\n" + 
                String.format("%-25s %-10s", "Amount ordered:", getOrderQty()) + "\n"+
                "*****************************************************";
    }
}
