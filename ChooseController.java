package sample;

import javafx.fxml.FXML;

public class ChooseController {
    private SceneController sceneController;
    private Game game;

    @FXML
    public void backClicked() {
        sceneController.showMenu();
        sceneController.closeChoose();
    }

    @FXML
    public void easyClicked() {
        game = new Game(10, 8, 8);
        game.setParentController(sceneController);
        sceneController.hideChoose();
    }

    @FXML
    public void mediumClicked() {
        game = new Game(50, 12, 20);
        game.setParentController(sceneController);
        sceneController.hideChoose();
    }

    @FXML
    public void hardClicked() {
        game = new Game(100, 16, 30);
        game.setParentController(sceneController);
        sceneController.hideChoose();
    }

    public void setParentController(SceneController sceneController)  {
        this.sceneController = sceneController;
    }
}
