/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controller;

import Seriialization.Deserializer;
import Seriialization.Serializer;
import Server.Model.Customer;
import Server.Model.CustomerList;
import Server.Model.Item;
import Server.Model.Order;
import Server.Model.OrderLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ModelController {

    DbController dbController;
    Serializer serializer;
    Deserializer deserializer;
    ResultSet rs;
    CustomerList customerList;

    public ModelController() {
        dbController = new DbController();
        serializer = new Serializer();
        deserializer = new Deserializer();
    }

    public boolean addCustomer(String serailziedObject) {
        System.out.println("Deserializing customer");
        Customer customer = (Customer) deserializer.unmarshal(Customer.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        boolean result = dbController.addCustomer(customer);
        return result;
    }

    public boolean updateCustomer(String serailziedObject) {
        System.out.println("Deserializing customer");
        Customer customer = (Customer) deserializer.unmarshal(Customer.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        boolean result = dbController.updateCustomer(customer);
        return result;
    }

    public boolean deleteCustomer(String serailziedObject) {
        System.out.println("Deserializing customer");
        Customer customer = (Customer) deserializer.unmarshal(Customer.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        boolean result = dbController.deleteCustomer(customer);
        return result;
    }

    public String getCustomerById(String id) {
        String serialized = "";
        try {
            rs = dbController.getCustomerById(id);
            customerList = new CustomerList();
            ObservableList cl = FXCollections.observableArrayList();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_id"),
                        rs.getString("First_name"),
                        rs.getString("Last_name"),
                        rs.getString("Phone_number"),
                        rs.getString("Address"),
                        rs.getString("PostalCode"),
                        rs.getString("Customer_type")
                );

                cl.add(customer);
            }
            serialized = serializer.marshal(Customer.class, cl, "customers");
        } catch (SQLException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serialized;
    }

    public String getCustomerByName(String name) {
        String serialized = "";
        try {
            rs = dbController.getCustomerByName(name);
            customerList = new CustomerList();
            ObservableList cl = FXCollections.observableArrayList();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_id"),
                        rs.getString("First_name"),
                        rs.getString("Last_name"),
                        rs.getString("Phone_number"),
                        rs.getString("Address"),
                        rs.getString("PostalCode"),
                        rs.getString("Customer_type")
                );

                cl.add(customer);
            }
            serialized = serializer.marshal(Customer.class, cl, "customers");
        } catch (SQLException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serialized;
    }

    public String getCustomerByType(String type) {
        String serialized = "";
        try {
            rs = dbController.getCustomerByType(type);
            customerList = new CustomerList();
            ObservableList cl = FXCollections.observableArrayList();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_id"),
                        rs.getString("First_name"),
                        rs.getString("Last_name"),
                        rs.getString("Phone_number"),
                        rs.getString("Address"),
                        rs.getString("PostalCode"),
                        rs.getString("Customer_type")
                );

                cl.add(customer);
            }
            serialized = serializer.marshal(Customer.class, cl, "customers");
        } catch (SQLException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serialized;
    }

    public String getAllItems() {
        String serialized = "";
        try {
            rs = dbController.getAllItems();
            ObservableList cl = FXCollections.observableArrayList();
            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("Item_id"),
                        rs.getString("Item_name"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Price"),
                        rs.getString("Item_type"),
                        rs.getString("Power_type"),
                        rs.getInt("Supplier_id")
                );

                cl.add(item);
            }
//            System.out.println("on server "+cl.get(0).toString()); //remove this
            serialized = serializer.marshal(Item.class, cl, "items");
        } catch (SQLException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serialized;
    }

    public String getItemById(String id) {
        String serialized = "";
        try {
            rs = dbController.getItemById(id);
            ObservableList<Item> cl = FXCollections.observableArrayList();
            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("Item_id"),
                        rs.getString("Item_name"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Price"),
                        rs.getString("Item_type"),
                        rs.getString("Power_type"),
                        rs.getInt("Supplier_id")
                );

                cl.add(item);
            }
            serialized = serializer.marshal(Item.class, cl, "items");

        } catch (SQLException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serialized;
    }

    public String getItemByName(String name) {
        String serialized = "";
        try {
            rs = dbController.getItemByName(name);
            ObservableList cl = FXCollections.observableArrayList();
            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("Item_id"),
                        rs.getString("Item_name"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Price"),
                        rs.getString("Item_type"),
                        rs.getString("Power_type"),
                        rs.getInt("Supplier_id")
                );

                cl.add(item);
            }
            serialized = serializer.marshal(Item.class, cl, "items");
        } catch (SQLException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serialized;
    }

    public boolean addItem(String serailziedObject) {
        System.out.println("Deserializing item");
        Item item = (Item) deserializer.unmarshal(Item.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        boolean result = dbController.addItem(item);
        return result;
    }

    public boolean deleteItem(String serailziedObject) {
        System.out.println("Deserializing item");
        Item item = (Item) deserializer.unmarshal(Item.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        boolean result = dbController.deleteItem(item);
        return result;
    }

    public boolean sellItem(int quantiy, String serailziedObject) {
        System.out.println("Deserializing item");
        Item item = (Item) deserializer.unmarshal(Item.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        boolean result = dbController.sellItem(quantiy, item);
        return result;
    }

    public void createOrder(String serailziedObject) {
        System.out.println("Deserializing item");
        Order order = (Order) deserializer.unmarshal(Order.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        dbController.createOrder(order);
    }

    public void createOrderLine(String serailziedObject) {
        System.out.println("Deserializing item");
        OrderLine ol = (OrderLine) deserializer.unmarshal(OrderLine.class, serailziedObject).get(0);
        System.out.println("desrialization success");
        dbController.createOrderLine(ol);
    }

    public static void readDataFromTextFiles() {
        BufferedReader br = null;
        DbController dbController1 = new DbController();
        ArrayList<String> tokens;
        try {
            
            //Read Customer data
            File file = new File("clients.txt");
            br = new BufferedReader(new FileReader(file));
            String line;
            dbController1.deleteAllRecords("customer");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                StringTokenizer st = new StringTokenizer(line, ";");
                tokens = new ArrayList<>();
                while (st.hasMoreTokens()) {                    
                    tokens.add(st.nextToken());
                }
                Customer customer = new Customer(Integer.parseInt(tokens.get(0)), tokens.get(1), 
                        tokens.get(2), tokens.get(5), tokens.get(3), tokens.get(4), tokens.get(6));
                
                dbController1.addCustomer(customer);
            }
            
            //Read Item data
            file = new File("items.txt");
            br = new BufferedReader(new FileReader(file));            
            dbController1.deleteAllRecords("item");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                StringTokenizer st = new StringTokenizer(line, ";");
                tokens = new ArrayList<>();
                while (st.hasMoreTokens()) {                    
                    tokens.add(st.nextToken());
                }
                Item item = new Item(Integer.parseInt(tokens.get(0)), tokens.get(1), 
                        Integer.parseInt(tokens.get(2)), Double.parseDouble(tokens.get(3)), 
                        tokens.get(4), tokens.get(5), Integer.parseInt(tokens.get(6)));
                
                dbController1.addItem(item);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
