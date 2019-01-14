package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class Game {
    private int cellCount;
    private int mineCount;
    private int mineCountChange;
    private int height;
    private int width;
    private int clickedFirstX;
    private int clickedFirstY;
    private Vector<Vector<Cell>> cells;
    private GridPane gridPane;
    private GridPane gridPaneBorder;
    private BorderPane borderPane;
    private Button back;
    private Label label;
    private Scene scene;
    private Stage stage;
    private SceneController sceneController;
    private boolean started;
    private Stack<Cell> stack;

    public Game(int mineCount, int height, int width) {
        this.mineCount = mineCount;
        this.height = height;
        this.width = width;
        this.cellCount = height * width;
        this.cells = new Vector<>();
        started = false;
        mineCountChange = mineCount;

        // allocates space for the cells
        for (int i = 0; i < height; i++)
            cells.add(new Vector<>());

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                cells.get(i).add(new Cell(i, j, 0, this));

        setScene();
    }

    private void setScene() {
        gridPane = new GridPane();
        borderPane = new BorderPane();
        back = new Button("Give up");
        back.setFont(new Font(20));
        back.setMinSize(120, 60);
        label = new Label(String.valueOf(mineCount + ""));
        label.setFont(new Font(20));
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), Insets.EMPTY)));
        label.setMinSize(50, 30);
        label.setAlignment(Pos.CENTER);
        gridPaneBorder = new GridPane();

        borderPane.setMinSize(120 + width * 35, 200 + height * 35);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPaneBorder.setAlignment(Pos.CENTER);
        gridPane.setMaxSize(width * 25, height * 25);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                gridPane.add(cells.get(i).get(j).getLabel(), j, i);
                cells.get(i).get(j).hideContent();
            }

        gridPaneBorder.add(label, 0, 1);
        gridPaneBorder.add(new Label(), 0, 0);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(back);
        borderPane.setTop(gridPaneBorder);
        BorderPane.setAlignment(gridPaneBorder, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(back, Pos.BASELINE_CENTER);
        label.setAlignment(Pos.CENTER);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                sceneController.showChoose();
            }
        });

        scene = new Scene(borderPane);
        stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // sets the value of each cell
    private void initialize() {
        Random random = new Random();
        int x;
        int y;
        int content;

        // chooses random places for the mines
        for (int i = 0; i < mineCount; i++) {
            do {
                x = random.nextInt(height);
                y = random.nextInt(width);
            } while (cells.get(x).get(y).getContent() == 9 || wrongPlace(x, y));

            cells.get(x).get(y).setContent(9);
        }

        // sets the value of each cells without mine

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (cells.get(i).get(j).getContent() != 9) {
                    content = 0;

                    try {
                        content += cells.get(i + 1).get(j).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i - 1).get(j).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i).get(j + 1).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i).get(j - 1).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i + 1).get(j + 1).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i + 1).get(j - 1).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i - 1).get(j + 1).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    try {
                        content += cells.get(i - 1).get(j - 1).getContent() / 9;
                    } catch (ArrayIndexOutOfBoundsException e) {}

                    cells.get(i).get(j).setContent(content);
                }

        // sets the color of each cell
        int c = 0;
        Color color1 = Color.rgb(192, 192, 192);
        Color color2 = Color.rgb(169, 169, 169);

        for (int i = 0; i < height; i++) {
            c++;

            for (int j = 0; j < width; j++) {
                if (c % 2 == 0)
                    cells.get(i).get(j).setColor(color1);
                else
                    cells.get(i).get(j).setColor(color2);
                c++;
            }
        }
    }

    public void setParentController(SceneController sceneController)  {
        this.sceneController = sceneController;
    }

    public void setClickedFirst(int x, int y) {
        clickedFirstX = x;
        clickedFirstY = y;
        started = true;
        initialize();
    }

    public boolean getStarted() {
        return started;
    }

    public void revealNeighbours(int x, int y) {
        Cell cell;

        stack = new Stack<>();

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (i == 0 || j == 0) {
                    try {
                        stack.push(cells.get(x + i).get(y + j));
                    } catch (Exception e) {}
                }

        while (!stack.empty()) {
            cell = stack.pop();

            if (cell.getContent() != 9 && cell.isHidden()) {
                cell.showContent();

                if (cell.getContent() == 0) {
                    for (int i = -1; i <= 1; i++)
                        for (int j = -1; j <= 1; j++)
                            if (i == 0 || j == 0)
                                try {
                                    stack.push(cells.get(cell.getX() + i).get(cell.getY() + j));
                                } catch (Exception e) {}
                }
            }
        }
    }

    private boolean wrongPlace(int x, int y) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                try {
                    if (clickedFirstX + i == x && clickedFirstY + j == y)
                        return true;
                } catch (Exception e) {}

        return false;
    }

    public void gameOver() {
        back.setText("Back");
        label.setText(" Game over ");

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                cells.get(i).get(j).disableClick();
    }

    public void incMine() {
        if (mineCountChange < mineCount) {
            mineCountChange++;
            label.setText(mineCountChange + "");
        }
    }

    public void decMine() {
        if (mineCountChange > 0) {
            mineCountChange--;
            label.setText(mineCountChange + "");
        }
    }

    public void checkIfWin() {
        int c = 0;

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (!cells.get(i).get(j).isClicked())
                    c++;

        if (c == mineCount) {
            back.setText("Back");
            label.setText(" Congratulations ");

            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    cells.get(i).get(j).disableClick();
        }
    }
}
