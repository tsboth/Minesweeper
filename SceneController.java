package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SceneController {
    private Stage menuStage;
    private Stage chooseStage;

    public final void startMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        GridPane gridPane = loader.load();
        Scene scene = new Scene(gridPane);
        MenuController menuController = loader.getController();

        menuController.setParentController(this);
        menuStage =  new Stage();
        menuStage.setScene(scene);
        menuStage.setResizable(false);
        showMenu();
    }

    public void showMenu() {
        menuStage.show();
    }

    public void hideMenu() {
        menuStage.hide();
    }

    public void startChoose() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("choose.fxml"));
        GridPane gridPane = loader.load();
        Scene scene = new Scene(gridPane);
        ChooseController chooseController = loader.getController();

        chooseController.setParentController(this);
        chooseStage =  new Stage();
        chooseStage.setScene(scene);
        chooseStage.setResizable(false);
        showChoose();
    }

    public void showChoose() {
        chooseStage.show();
    }

    public void closeChoose() {
        chooseStage.close();
    }

    public void hideChoose() {
        chooseStage.hide();
    }
}
