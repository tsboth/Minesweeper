package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
    @FXML
    private Button helpClose;

    @FXML
    public void helpCloseClicked() {
        Stage stage = (Stage) helpClose.getScene().getWindow();
        stage.close();
    }
}
