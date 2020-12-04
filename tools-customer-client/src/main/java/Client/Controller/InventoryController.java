/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Controller;

import Server.Model.Customer;
import Server.Model.Item;
import Server.Model.Order;
import Server.Model.OrderLine;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Alan Joseph Aaron Joseph
 */
public class InventoryController implements Initializable {

    @FXML
    private Button listAllButton;
    @FXML
    private Button printOrderButton;
    @FXML
    private RadioButton toolIdRB;
    @FXML
    private TextField searchTF;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
    @FXML
    private ListView<Item> resultListView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addNewButton;
    @FXML
    private TextField toolIdTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField quantityTF;
    @FXML
    private TextField priceTF;
    @FXML
    private TextField supplierIdTF;
    @FXML
    private ComboBox<String> toolTypeCB;
    @FXML
    private TextField powerTypeTF;
    @FXML
    private TextField decreaseQtyTF;
    @FXML
    private Button decreaseButton;
    @FXML
    private RadioButton toolNameRB;

    ModelController modelController;
    Item item;
    ToggleGroup toggleGroup;
    private ObservableList<Item> itemList;
    Order order;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modelController = new ModelController();

        //Add R and C values to combo box
        ObservableList<String> clientTypes = FXCollections.observableArrayList();
        clientTypes.addAll("Electrical", "Non-Electrical");
        toolTypeCB.setItems(clientTypes);

        //set default value of combo box to residential
        toolTypeCB.setValue("Electrical");

        //Add all radio buttons to same toggle group
        toggleGroup = new ToggleGroup();
        toolIdRB.setToggleGroup(toggleGroup);
        toolNameRB.setToggleGroup(toggleGroup);

        itemList = FXCollections.observableArrayList();

        //configure listview        
        resultListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                return new InventoryController.itemCell<>();
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

                item = resultListView.getSelectionModel().getSelectedItem();
                System.out.println("clicked on " + item.getName());
                displayItemDetails();
            }
        });

        //set decrease quantityTF to 1 by default
        decreaseQtyTF.setText("1");
    }

    @FXML
    private void listAllButtonClicked(ActionEvent event) {
        itemList = modelController.getAllItems();
        if (itemList == null || itemList.isEmpty()) {
            //Alert box if no data
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("No Data Found");
            alert.showAndWait();
            return;
        }
        itemList.forEach(c -> System.out.println(c.getName()));
        resultListView.setItems(itemList);
        resultListView.refresh();
    }

    @FXML
    private void printOrderButtonClicked(ActionEvent event) {
        if (order == null) {
            System.out.println("There are no orders today!!");
        }else{
            System.out.println(order.toString());
            order.writeOrderLinesToFile();
        }
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

        if (value.equals("Tool Id")) {
            itemList = modelController.getToolById(keyword);
            if (itemList == null || itemList.isEmpty()) {
                alert.showAndWait();
                return;
            }
            itemList.forEach(c -> System.out.println(c.getName()));
            resultListView.setItems(itemList);
            resultListView.refresh();
        } else { //tool name
            itemList = modelController.getToolByName(keyword);
            if (itemList == null || itemList.isEmpty()) {
                alert.showAndWait();
                return;
            }
            itemList.forEach(c -> System.out.println(c.getName()));
            resultListView.setItems(itemList);
            resultListView.refresh();
        }
        System.out.println(value);
    }

    @FXML
    private void onclearSearchButtonClicked(ActionEvent event) {
        searchTF.setText("");
        itemList.clear();
        resultListView.refresh();
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        if (resultListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Item item1 = resultListView.getSelectionModel().getSelectedItem();

        boolean result = modelController.deleteItem(item1);

        if (result) {
            itemList.remove(item1);
            resultListView.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull");
            alert.setHeaderText(null);
            alert.setContentText("Deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Error Deleteing. Please check details");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearButton(ActionEvent event) {
        clearDetails();
    }

    @FXML
    private void addNewButton(ActionEvent event) {
        Item item1 = getDetails();

        if (item1 == null) {
            return;
        }
        boolean result = modelController.addNewItem(item1);
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull");
            alert.setHeaderText(null);
            alert.setContentText("Added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Error Inserting. Please check details");
            alert.showAndWait();
        }
    }

    @FXML
    private void decreaseButtonClicked(ActionEvent event) {
        //if no item is selected, return
        if (item == null) {
            return;
        }

        int value = 1;

        //if value is not an integer, return
        try {
            value = Integer.parseInt(decreaseQtyTF.getText());
        } catch (Exception e) {
            Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, e);
            return;
        }

        if (value > item.getQuantity()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Quantity to sell cannot be more than Available Quantity");
            alert.showAndWait();
            return;
        }
        boolean result = modelController.sellItem(item, value);

        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull");
            alert.setHeaderText(null);
            alert.setContentText(value + " of the item sold successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Error selling item. Please check details");
            alert.showAndWait();
        }

        int updatedQty = item.getQuantity() - value;
        //do nothing if remaining qty is more than 40
        if (updatedQty > 40) {
            return;
        }

        int qtyToOrder = 50 - updatedQty;
        System.out.println("qty to torder - " + qtyToOrder);
        
        if (order == null) {
            System.out.println("Create new order");
            order = new Order();
            modelController.createOrder(order);
        }
        
        System.out.println("Create new order line");
        OrderLine ol = new OrderLine(item, order.getOrderId(), qtyToOrder);
        modelController.createOrderLine(ol);
        order.getOrderLines().add(ol);
    }

    public Item getDetails() {
        int Id = Integer.parseInt(toolIdTF.getText());
        String name = nameTF.getText();
        int quantity = Integer.parseInt(quantityTF.getText());
        double price = Double.parseDouble(priceTF.getText());
        int supplierId = Integer.parseInt(supplierIdTF.getText());

        String powerType = null;
        String type = "NE";
        if (toolTypeCB.getValue().equals("Electrical")) {
            type = "E";
            if (powerTypeTF.getText() == null || powerTypeTF.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!!!!");
                alert.setHeaderText(null);
                alert.setContentText("Power Type cannot be empty");
                alert.showAndWait();
                return null;
            }

            powerType = powerTypeTF.getText();
        }

        Item newItem = new Item(Id, name, quantity, price, type, powerType, supplierId);

        return newItem;
    }

    public void displayItemDetails() {
        toolIdTF.setText(item.getId() + "");
        nameTF.setText(item.getName());
        quantityTF.setText(item.getQuantity() + "");
        priceTF.setText(item.getPrice() + "");
        supplierIdTF.setText(item.getSupplierId() + "");

        if (item.getType().equals("E")) {
            toolTypeCB.setValue("Electrical");
            powerTypeTF.setText(item.getPowerType());
        } else {
            toolTypeCB.setValue("Non-Electrical");
            powerTypeTF.setText("");
        }
    }

    public void clearDetails() {
        toolIdTF.setText("");
        nameTF.setText("");
        quantityTF.setText("");
        priceTF.setText("");
        supplierIdTF.setText("");
        powerTypeTF.setText("");
        //set default value of combo box to residential
        toolTypeCB.setValue("Electrical");
    }

    //cell factory
    static class itemCell<T> extends ListCell<T> {

        HBox hbox = new HBox();
        Label label = new Label();

        public itemCell() {
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
                Item i = (Item) getItem();
                label.setText(i.getId() + "   " + i.getName());
                setGraphic(hbox);
            }
        }
    }
}
