/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controller;

import Server.Model.Customer;
import Server.Model.Item;
import Server.Model.Order;
import Server.Model.OrderLine;

import java.sql.Connection;
import java.sql.Driver;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbController {

    private final String JDBC_DRIVER = "jdbc:mysql://localhost/toolshop";
    private final String DB = "jdbc:mysql://localhost/toolshop";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DbController() {

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");  
            //Register JDBC driver            
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            //Open a connection
            connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("Problem");
            e.printStackTrace();
        }

    }

    public boolean addCustomer(Customer customer) {
        try {
            System.out.println("Trying to add to db - " + customer.toString());

            String query = "insert into customer values(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getPhoneNum());
            preparedStatement.setString(7, customer.getType());
            preparedStatement.executeUpdate();

            System.out.println("Added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean updateCustomer(Customer customer) {
        try {
            System.out.println("Trying to update to db - " + customer.toString());

            String query = "UPDATE customer SET First_name = ?, Last_name = ?, Address = ?, PostalCode = ?, Phone_number = ?, Customer_type = ? WHERE Customer_id = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhoneNum());
            preparedStatement.setString(6, customer.getType());
            preparedStatement.setInt(7, customer.getCustomerId());
            preparedStatement.executeUpdate();

            System.out.println("Updated in the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean deleteCustomer(Customer customer) {
        try {
            System.out.println("Trying to delete to db - " + customer.toString());

            String query = "delete from customer where Customer_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.executeUpdate();

            System.out.println("Delete from the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void getAllCustomer() {
        try {
            String query = "SELECT * FROM customer";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Name - " + resultSet.getString("First_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAllRecords(String tablename) {
        try {
            String query = "delete from "+ tablename;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getCustomerById(String id) {
        try {
            String query = "SELECT * FROM customer where Customer_id = '" + id + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public ResultSet getCustomerByName(String name) {
        try {
            String query = "SELECT * FROM customer where Last_name = '" + name + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public ResultSet getCustomerByType(String type) {
        try {
            String query = "SELECT * FROM customer where Customer_type = '" + type + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public boolean addItem(Item item) {
        try {
            System.out.println("Trying to add to db - " + item.toString());

            String query = "insert into item values(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setDouble(4, item.getPrice());
            preparedStatement.setString(5, item.getType());
            preparedStatement.setString(6, item.getPowerType());
            preparedStatement.setInt(7, item.getSupplierId());
            preparedStatement.executeUpdate();

            System.out.println("Added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean deleteItem(Item item) {
        try {
            System.out.println("Trying to delete to db - " + item.toString());

            String query = "delete from item where Item_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item.getId());
            preparedStatement.executeUpdate();

            System.out.println("Delete from the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean sellItem(int qty, Item item) {
        try {
            System.out.println("Trying to update in db - " + item.toString());

            String query = "UPDATE item SET quantity = quantity - ? WHERE Item_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, qty);
            preparedStatement.setInt(2, item.getId());
            preparedStatement.executeUpdate();

            System.out.println("Item qty updated");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public ResultSet getAllItems() {
        try {
            String query = "SELECT * FROM item";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public ResultSet getItemById(String id) {
        try {
            String query = "SELECT * FROM item where Item_id = '" + id + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public ResultSet getItemByName(String name) {
        try {
            String query = "SELECT * FROM item where Item_name = '" + name + "'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public void createOrder(Order order) {
        try {
            System.out.println("Trying to add order to db - " + order.toString());

            String query = "insert into orders values(?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setString(2, order.getDate().toString());       
            preparedStatement.executeUpdate();

            System.out.println("Order added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    public void createOrderLine(OrderLine ol) {
        try {
            System.out.println("Trying to add order line to db - " + ol.toString());

            String query = "insert into order_line values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ol.getOrderId());
            preparedStatement.setInt(2, ol.getOrderId());       
            preparedStatement.setInt(3, ol.getSupplierId());       
            preparedStatement.setInt(4, ol.getOrderQty());       
            preparedStatement.executeUpdate();

            System.out.println("Order line added to the database");
        } catch (SQLException ex) {
            Logger.getLogger(DbController.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }

    public static void main(String[] args) {
        new DbController().getAllCustomer();
    }
}
