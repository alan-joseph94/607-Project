package Client.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    private Button inventoryButton;
    @FXML
    private Button clientButton;

    @FXML
    private void inventoryButtonClicked(ActionEvent event) throws IOException {
        App.setRoot("inventoryGUI");
    }

    @FXML
    private void clientButtonCLicked(ActionEvent event) throws IOException {
        App.setRoot("clientGUI");
    }
}
