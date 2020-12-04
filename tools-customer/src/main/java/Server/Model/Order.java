/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "order")
@XmlAccessorType (XmlAccessType.FIELD)
public class Order {
    
     
    private final int orderId;
    private String date;
    private final ObservableList<OrderLine> orderLines;

    public Order() {
        orderLines = FXCollections.observableArrayList();        
        orderId = 10000 + new Random().nextInt(90000);        
        date = LocalDate.now().toString();
        writeOrderLinesToFile();
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public ObservableList<OrderLine> getOrderLines() {
        return orderLines;
    }
    
        
     public void writeOrderLinesToFile() {
        try {
            FileWriter fileWriter = new FileWriter("order.txt");
            fileWriter.write(this.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------Order----------------------------\n");
        sb.append(String.format("%-25s %-20s\n", "Order Id :", getOrderId()));
        sb.append(String.format("%-25s %-20s\n", "Date : ", getDate()));
        for (OrderLine orderLine: orderLines) {
            sb.append(orderLine).append("\n");
        }
        sb.append("------------------------------Order----------------------------");
        return sb.toString();
    }
    
}
