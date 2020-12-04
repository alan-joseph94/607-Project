/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "customer")
@XmlAccessorType (XmlAccessType.FIELD)
public class Customer implements Serializable{

    private int customerId;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String address;
    private String postalCode;
    private String type;

    public Customer() {
    }

    
    public Customer(int customerId, String firstName, String lastName, String phoneNum, String address, String postalCode, String type) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.postalCode = postalCode;
        this.type = type;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s", getCustomerId(), getFirstName(), getLastName(), getPhoneNum(), getAddress(), getPostalCode(), getType());
    }
}
