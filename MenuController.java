package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuController {
    private SceneController sceneController;

    @FXML
    public void startClicked() throws Exception {
        sceneController.hideMenu();
        sceneController.startChoose();
    }

    @FXML
    public void helpClicked() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
        BorderPane myApp = loader.load();
        Scene scene = new Scene(myApp);
        Stage helpStage = new Stage();

        helpStage.setScene(scene);
        helpStage.setResizable(false);
        helpStage.show();
    }

    @FXML
    public void exitClicked() {
        System.exit(0);
    }

    public void setParentController(SceneController sceneController)  {
        this.sceneController = sceneController;
    }
}
