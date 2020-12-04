/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CustomerList implements Serializable{

    public CustomerList() {
        this.customerList = new ArrayList<>();
    }
    
    
    private List<Customer> customerList;

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }        
    
}
