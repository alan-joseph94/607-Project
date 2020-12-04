package Client.Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Server.Model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ClientManagementController implements Initializable {

    @FXML
    private RadioButton clientIdRB;
    @FXML
    private RadioButton lastNameRB;
    @FXML
    private RadioButton clientTypeRB;
    @FXML
    private TextField searchTF;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
    @FXML
    private ListView<Customer> resultListView;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addNewButton;
    @FXML
    private TextField clientIdTF;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField addressTF;
    @FXML
    private TextField postalCodeTF;
    @FXML
    private TextField phoneNoTF;
    @FXML
    private ComboBox<String> clientTypeCB;

    ModelController modelController;
    Customer customer;
    ToggleGroup toggleGroup;
    private ObservableList<Customer> customerList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modelController = new ModelController();

        //Add R and C values to combo box
        ObservableList<String> clientTypes = FXCollections.observableArrayList();
        clientTypes.addAll("Residential", "Commercial");
        clientTypeCB.setItems(clientTypes);

        //set default value of combo box to residential
        clientTypeCB.setValue("Residential");

        //Add all radio buttons to same toggle group
        toggleGroup = new ToggleGroup();
        clientIdRB.setToggleGroup(toggleGroup);
        lastNameRB.setToggleGroup(toggleGroup);
        clientTypeRB.setToggleGroup(toggleGroup);

        customerList = FXCollections.observableArrayList();

        //configure listview        
        resultListView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                return new ClientManagementController.customerCell<>();
            }
        });

        //onclick of list view item
        resultListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //in case no item is selected
                if (resultListView.getSelectionModel().getSelectedItem() == null) {
                    return;
                }

                customer = resultListView.getSelectionModel().getSelectedItem();
                System.out.println("clicked on " + customer.getFirstName());
                displayCustomerDetails();
            }
        });
    }

    @FXML
    private void onsearchButtonClicked(ActionEvent event) {
        //get search parameter
        String keyword = "";
        if (searchTF.getText() == null || searchTF.getText().equals("")) {
            return;
        }
        keyword = searchTF.getText();

        if (toggleGroup.getSelectedToggle() == null) {
            return;
        }

        //get which radio button is selceted
        String value = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

        //Alert box if no data
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!!!!!!");
        alert.setHeaderText(null);
        alert.setContentText("No Data Found");

        if (value.equals("Client Id")) {
            customerList = modelController.getCustomerById(keyword);
            if (customerList == null || customerList.isEmpty()) {
                alert.showAndWait();
                return;
            }
            customerList.forEach(c -> System.out.println(c.getFirstName()));
            resultListView.setItems(customerList);
            resultListView.refresh();
        } else if (value.equals("Last Name")) {
            customerList = modelController.getCustomerByName(keyword);
            if (customerList == null || customerList.isEmpty()) {

                alert.showAndWait();
                return;
            }
            customerList.forEach(c -> System.out.println(c.getFirstName()));
            resultListView.setItems(customerList);
            resultListView.refresh();
        } else { //client type
            customerList = modelController.getCustomerByType(keyword);
            if (customerList == null || customerList.isEmpty()) {
                alert.showAndWait();
                return;
            }
            customerList.forEach(c -> System.out.println(c.getFirstName()));
            resultListView.setItems(customerList);
            resultListView.refresh();
        }
        System.out.println(value);
    }

    @FXML
    private void onclearSearchButtonClicked(ActionEvent event) {
        searchTF.setText("");
        customerList.clear();
        resultListView.refresh();
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        Customer customer1 = getDetails();

        if (customer1 == null) {
            return;
        }
        System.out.println(customer1.toString());

        boolean result = modelController.updateCustomer(customer1);
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull");
            alert.setHeaderText(null);
            alert.setContentText("Customer update successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Error updating customer. Please check details");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        if (resultListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Customer customer1 = resultListView.getSelectionModel().getSelectedItem();

        boolean result = modelController.deleteCustomer(customer1);

        if (result) {
            customerList.remove(customer1);
            resultListView.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull");
            alert.setHeaderText(null);
            alert.setContentText("Customer deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Error deleteing customer. Please check details");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearButton(ActionEvent event) {
        clearDetails();
    }

    @FXML
    private void addNewButton(ActionEvent event) {
        Customer customer1 = getDetails();

        if (customer1 == null) {
            return;
        }
        boolean result = modelController.addNewCustomer(customer1);
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull");
            alert.setHeaderText(null);
            alert.setContentText("Customer added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Error inserting customer. Please check details");
            alert.showAndWait();
        }

    }

    public Customer getDetails() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!!!!!!");
        alert.setHeaderText(null);

        int Id = Integer.parseInt(clientIdTF.getText());
        String firstName = firstNameTF.getText();
        if (firstName.length() > 20) {
            alert.setContentText("First Name cannot be more than 20 characters!!");
            alert.showAndWait();
            return null;
        }

        String lastName = lastNameTF.getText();
        if (lastName.length() > 20) {
            alert.setContentText("Last Name cannot be more than 20 characters!!");
            alert.showAndWait();
            return null;
        }

        String phone = phoneNoTF.getText();
        if (phone.length()>12) {
            alert.setContentText("Phone no cannot be more than 12 characters!!");
            alert.showAndWait();
            return null;
        }
        
        String postalCode = postalCodeTF.getText();
        if (postalCode.length()>7) {
            alert.setContentText("Postal cannot be more than 7 characters!!");
            alert.showAndWait();
            return null;
        }
        
        String address = addressTF.getText();
        if (address.length() > 50) {
            alert.setContentText("Address cannot be more than 50 characters!!");
            alert.showAndWait();
            return null;
        }

        String value = clientTypeCB.getValue();
        String type = "R";
        if (clientTypeCB.getValue().equals("Commercial")) {
            type = "C";
        }

        Customer newCustomer = new Customer(Id, firstName, lastName, phone, address, postalCode, type);

        return newCustomer;
    }

    public void displayCustomerDetails() {
        clientIdTF.setText(customer.getCustomerId() + "");
        firstNameTF.setText(customer.getFirstName());
        lastNameTF.setText(customer.getLastName());
        phoneNoTF.setText(customer.getPhoneNum());
        postalCodeTF.setText(customer.getPostalCode());
        addressTF.setText(customer.getAddress());

        if (customer.getType().equals("R")) {
            clientTypeCB.setValue("Residential");
        } else {
            clientTypeCB.setValue("Commercial");
        }
    }

    public void clearDetails() {
        clientIdTF.setText("");
        firstNameTF.setText("");
        lastNameTF.setText("");
        phoneNoTF.setText("");
        addressTF.setText("");
        postalCodeTF.setText("");
        //set default value of combo box to residential
        clientTypeCB.setValue("Residential");
    }

    //cell factory
    static class customerCell<T> extends ListCell<T> {

        HBox hbox = new HBox();
        Label label = new Label();

        public customerCell() {
            super();

            hbox.getChildren().addAll(label);
            HBox.setMargin(label, new Insets(5, 5, 5, 5));
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                setGraphic(null);
            } else {
                Customer c = (Customer) getItem();
                label.setText(c.getCustomerId() + "   " + c.getFirstName());
                setGraphic(hbox);
            }
        }
    }
}
