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
import Server.Model.Item;
import Server.Model.Order;
import Server.Model.OrderLine;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Alan Joseph Aaron Joseph
 */
public class ClientController {

    private final String SERVER_NAME = "localhost";
    private final int PORT = 8081;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
//    private ModelController modelController;
    private Serializer serializer;
    private Deserializer deserializer;

    public ClientController() {
        try {
            socket = new Socket(SERVER_NAME, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            serializer = new Serializer();
            deserializer = new Deserializer();
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public ModelController getModelController() {
//        return modelController;
//    }
//
//    public void setModelController(ModelController modelController) {
//        this.modelController = modelController;
//    }
    public String sendRequest(String request) {
        String response = null;
        try {
            objectOutputStream.writeObject(request);
            response = (String) objectInputStream.readObject();
            System.out.println("got response  -- " + response);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public String getResponse() {
        String response = "";
        try {
            response = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public static void main(String[] args) {
//        ClientController clientController = new ClientController();
//        Customer customer = new Deserializer().deserailizeCustomer(clientController.sendRequest("get-customer"));
//        String response = clientController.getResponse();
//        customer.toString();
//        clientController.sendRequest(null);
//        clientController.getResponse();
//        clientController.closeClient();
    }

    public ObservableList<Customer> getCustomerListByRequest(String request) {
        try {
            objectOutputStream.writeObject(request);
            String response = (String) objectInputStream.readObject();
            System.out.println("got response  -- " + response);

            ObservableList<Customer> cl = deserializer.unmarshal(Customer.class, response);

            if (cl == null || cl.isEmpty()) {
                return null;
            }
            System.out.println("result --- " + cl.get(0).getFirstName());

            return cl;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sendCustomerToServer(String request, Customer customer) {
        try {
            ObservableList<Customer> cl = FXCollections.observableArrayList();
            cl.add(customer);
            String serialized = serializer.marshal(Customer.class, cl, "customers");
            objectOutputStream.writeObject(request + "%" + serialized);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Item> getItemListByRequest(String request) {
        try {
            objectOutputStream.writeObject(request);
            String response = (String) objectInputStream.readObject();
            System.out.println("got response  -- " + response);

            ObservableList<Item> cl = deserializer.unmarshal(Item.class, response);

            if (cl == null || cl.isEmpty()) {
                return null;
            }
//            System.out.println("result --- " + cl.get(0).getName());

            return cl;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sendItemToServer(String request, Item item) {
        try {
            ObservableList<Item> cl = FXCollections.observableArrayList();
            cl.add(item);
            String serialized = serializer.marshal(Item.class, cl, "items");
            objectOutputStream.writeObject(request + "%" + serialized);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendOrderToServer(String request, Order order) {
        try {
            ObservableList<Order> cl = FXCollections.observableArrayList();
            cl.add(order);
            String serialized = serializer.marshal(Order.class, cl, "orders");
            objectOutputStream.writeObject(request + "%" + serialized);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendOrderLineToServer(String request, OrderLine ol) {
        try {
            ObservableList<OrderLine> cl = FXCollections.observableArrayList();
            cl.add(ol);
            String serialized = serializer.marshal(OrderLine.class, cl, "order-lines");
            objectOutputStream.writeObject(request + "%" + serialized);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeClient() {
        try {
            socket.close();
            objectInputStream.close();
            objectOutputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
