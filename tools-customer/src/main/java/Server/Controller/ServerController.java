/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controller;

import Seriialization.Serializer;
import Server.Model.Customer;
import Server.Model.CustomerList;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;


public class ServerController implements Runnable {

    private ModelController modelController;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ServerController(Socket socket) {
        try {
            modelController = new ModelController();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getClientRequest() {
        String request = null;
        try {
            request = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request;
    }

    public void sendResponse(String response) {
        try {
            objectOutputStream.writeObject(response);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        String clientRequest = null;

        while (true) {
            System.out.println("In Serivce Controller");

            try {
                clientRequest = getClientRequest();
            } catch (Exception e) {
                closeConnection();
                break;
            }
            if (clientRequest == null) {
                System.out.println("request is null");
                break;
            }

            String[] query = clientRequest.split("%");
            switch (query[0]) {
                case "quit": {
                    System.out.println("Client has disconnected");
                    sendResponse("Connection terminated.");
                    closeConnection();
                    break;
                }
                case "customer": {
                    System.out.println("Customer has conencted");
                    sendResponse("Customer from server");
                    System.out.println("customer added by server");
                    break;
                }
                case "get-customer": {
                    System.out.println("Getting Customer");
//                    sendResponse("Customer from server");
                    Customer customer = new Customer(001, "asdsad", "asdsad", "123213", "123/123213a", "47845", "Residentail");
                    sendResponse(new Serializer().SerializeCustomer(customer));
                    System.out.println("customer sent by server");
                    sendResponse("customer sent by server");
                    break;
                }
                case "get-customer-by-Id": {
                    String id = query[1];
                    System.out.println("Getting Customer id " + id);
                    String serializedObject = modelController.getCustomerById(id);
                    sendResponse(serializedObject);
                    System.out.println("customer sent by server");
                    sendResponse("customer sent by server");
                    break;
                }
                case "get-customer-by-Name": {
                    String name = query[1];
                    System.out.println("Getting Customer name " + name);
                    String serializedObject = modelController.getCustomerByName(name);
                    sendResponse(serializedObject);
                    System.out.println("customer sent by server");
                    sendResponse("customer sent by server");
                    break;
                }
                case "get-customer-by-Type": {
                    String type = query[1];
                    System.out.println("Getting Customer type " + type);
                    String serializedObject = modelController.getCustomerByType(type);
                    sendResponse(serializedObject);
                    System.out.println("customer sent by server");
                    sendResponse("customer sent by server");
                    break;
                }
                case "add-customer": {
                    System.out.println("In add customer");
                    String serialized = query[1];
                    System.out.println("Recieved customer object");
                    boolean result = modelController.addCustomer(serialized);
                    if (result) {
                        sendResponse("Customer added successfully");
                    } else {
                        sendResponse("Customer insertion failed!!");
                    }
                    break;
                }
                case "update-customer": {
                    System.out.println("In update customer");
                    String serialized = query[1];
                    System.out.println("Recieved customer object");
                    boolean result = modelController.updateCustomer(serialized);
                    if (result) {
                        sendResponse("Customer added successfully");
                    } else {
                        sendResponse("Customer insertion failed!!");
                    }
                    break;
                }
                case "delete-customer": {
                    System.out.println("In delete customer");
                    String serialized = query[1];
                    System.out.println("Recieved customer object");
                    boolean result = modelController.deleteCustomer(serialized);
                    if (result) {
                        sendResponse("Customer added successfully");
                    } else {
                        sendResponse("Customer insertion failed!!");
                    }
                    break;
                }
                case "get-items": {
                    System.out.println("Getting all Items");
                    String serializedObject = modelController.getAllItems();
                    sendResponse(serializedObject);
                    System.out.println("item sent by server");
                    sendResponse("item sent by server");
                    break;
                }
                case "tools": {
                    System.out.println("Tools has conencted");
                    sendResponse("Tools from server");
                    break;
                }
                case "get-item-by-Id": {
                    String id = query[1];
                    System.out.println("Getting item id " + id);
                    String serializedObject = modelController.getItemById(id);
                    sendResponse(serializedObject);
                    System.out.println("item sent by server");
                    sendResponse("item sent by server");
                    break;
                }
                case "get-item-by-Name": {
                    String name = query[1];
                    System.out.println("Getting item name " + name);
                    String serializedObject = modelController.getItemByName(name);
                    sendResponse(serializedObject);
                    System.out.println("item sent by server");
                    sendResponse("item sent by server");
                    break;
                }
                case "add-item": {
                    System.out.println("In add item");
                    String serialized = query[1];
                    System.out.println("Recieved item object");
                    boolean result = modelController.addItem(serialized);
                    if (result) {
                        sendResponse("Item added successfully");
                    } else {
                        sendResponse("Item insertion failed!!");
                    }
                    break;
                }
                case "delete-item": {
                    System.out.println("In delete item");
                    String serialized = query[1];
                    System.out.println("Recieved item object");
                    boolean result = modelController.deleteItem(serialized);
                    if (result) {
                        sendResponse("Item added successfully");
                    } else {
                        sendResponse("Item insertion failed!!");
                    }
                    break;
                }
                case "sell-item": {
                    int quantity = Integer.parseInt(query[1]);
                    String name = query[2];
                    System.out.println("Getting qty - " + quantity + " Getting item name " + name);
                    boolean result = modelController.sellItem(quantity, name);
                    if (result) {
                        sendResponse("item quantity updated by server");
                    } else {
                        sendResponse("item quantity updation failed!!!");
                    }
                    break;
                }
                case "create-order": {
                    String serialized = query[1];
                    System.out.println("Creating order");
                    modelController.createOrder(serialized);
                    sendResponse("order created");
                    break;
                }
                case "create-order-line": {
                    String serialized = query[1];
                    System.out.println("Creating order line");
                    modelController.createOrderLine(serialized);
                    sendResponse("order line created");
                    break;
                }
            }
        }
        closeConnection();
    }

    public void closeConnection() {
        try {
            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
