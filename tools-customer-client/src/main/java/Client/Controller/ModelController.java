/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Controller;

import Client.Serialization.Deserializer;
import Client.Serialization.Serializer;
import Client.Serialization.WrapperDAO;
import Server.Model.Customer;
import Server.Model.CustomerList;
import Server.Model.Item;
import Server.Model.Order;
import Server.Model.OrderLine;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Alan Joseph Aaron Joseph
 */
public class ModelController {

    ClientController clientController;
    List<Customer> customerList;
    Serializer serializer;
    Deserializer deserializer;

    public ModelController() {
        clientController = new ClientController();
        customerList = new ArrayList<>();
        serializer = new Serializer();
        deserializer = new Deserializer();
    }

    public ObservableList<Item> getAllItems() {
        System.out.println("Request sent - " + "get-items");
        ObservableList<Item> cl = clientController.getItemListByRequest("get-items");

        String serverResponse = clientController.getResponse();
        System.out.println(serverResponse);

        return cl;
    }

    public ObservableList<Item> getToolById(String id) {
        System.out.println("Request sent - " + "get-item-by-Id%" + id);
        ObservableList<Item> cl = clientController.getItemListByRequest("get-item-by-Id%" + id);

        String serverResponse = clientController.getResponse();
        System.out.println(serverResponse);

        return cl;
    }

    public ObservableList<Item> getToolByName(String id) {
        System.out.println("Request sent - " + "get-item-by-Name%" + id);
        ObservableList<Item> cl = clientController.getItemListByRequest("get-item-by-Name%" + id);

        String serverResponse = clientController.getResponse();
        System.out.println(serverResponse);

        return cl;
    }
    
    public boolean addNewItem(Item item) {
        System.out.println("Request sent - " + "add-item");

        clientController.sendItemToServer("add-item", item);

        String response = clientController.getResponse();

        if (response.contains("failed")) {
            return false;
        } else {
            return true;
        }
    }
    
     public boolean deleteItem(Item item) {
        System.out.println("Request sent - " + "delete-item");

        clientController.sendItemToServer("delete-item", item);

        String response = clientController.getResponse();

        if (response.contains("failed")) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean sellItem(Item item, int qty){
        System.out.println("Request sent - " + "sell-item");

        clientController.sendItemToServer("sell-item%"+qty, item);

        String response = clientController.getResponse();
        if (response.contains("failed")) {
            return false;
        } else {
            return true;
        }
    }
    
    public void createOrder(Order order){
        System.out.println("Request sent - " + "create-order");

        clientController.sendOrderToServer("create-order", order);

        String response = clientController.getResponse();
        System.out.println(response);
    }
    
    public void createOrderLine(OrderLine ol){
        System.out.println("Request sent - " + "create-order-line");

        clientController.sendOrderLineToServer("create-order-line", ol);

        String response = clientController.getResponse();
        System.out.println(response);
    }
    

    public ObservableList<Customer> getCustomerById(String id) {
        System.out.println("Request sent - " + "get-customer-by-Id%" + id);
        ObservableList<Customer> cl = clientController.getCustomerListByRequest("get-customer-by-Id%" + id);

        String serverResponse = clientController.getResponse();
        System.out.println(serverResponse);

        return cl;
    }

    public ObservableList<Customer> getCustomerByName(String id) {
        System.out.println("Request sent - " + "get-customer-by-Name%" + id);
        ObservableList<Customer> cl = clientController.getCustomerListByRequest("get-customer-by-Name%" + id);

        String serverResponse = clientController.getResponse();
        System.out.println(serverResponse);

        return cl;
    }

    public ObservableList<Customer> getCustomerByType(String type) {
        System.out.println("Request sent - " + "get-customer-by-Type%" + type);
        ObservableList<Customer> cl = clientController.getCustomerListByRequest("get-customer-by-Type%" + type);

        String serverResponse = clientController.getResponse();
        System.out.println(serverResponse);

        return cl;
    }

    public boolean addNewCustomer(Customer customer) {
        System.out.println("Request sent - " + "add-customer");

        clientController.sendCustomerToServer("add-customer", customer);

        String response = clientController.getResponse();

        if (response.contains("failed")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateCustomer(Customer customer) {
        System.out.println("Request sent - " + "update-customer");

        clientController.sendCustomerToServer("update-customer", customer);

        String response = clientController.getResponse();

        if (response.contains("failed")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteCustomer(Customer customer) {
        System.out.println("Request sent - " + "delete-customer");

        clientController.sendCustomerToServer("delete-customer", customer);

        String response = clientController.getResponse();

        if (response.contains("failed")) {
            return false;
        } else {
            return true;
        }
    }

//    public static void main(String[] args) {
//        ModelController mc = new ModelController();
//        mc.getCustomerById("1").forEach(c -> System.out.println(c.getFirstName()));
//    }
}
