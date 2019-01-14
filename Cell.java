package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Cell {
    private int x;
    private int y;
    private int content;
    private Color color;
    private Label label;
    private ImageView imageViewBomb;
    private ImageView imageViewFlag;
    private Game game;
    private boolean hidden;
    private boolean flagged;
    private boolean clicked;

    public Cell(int x, int y, int content, Game game) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.game = game;
        hidden = true;
        flagged = false;
        clicked = false;
        label = new Label();
        label.setFont(new Font(25));
        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setAlignment(Pos.CENTER);

        imageViewBomb = new ImageView(new Image(getClass().getResourceAsStream("../bomb.png")));
        imageViewBomb.setFitHeight(20);
        imageViewBomb.setFitWidth(20);

        imageViewFlag = new ImageView(new Image(getClass().getResourceAsStream("../flag.png")));
        imageViewFlag.setFitHeight(20);
        imageViewFlag.setFitWidth(20);

        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (!clicked) {
                        if (!game.getStarted()) {
                            game.setClickedFirst(x, y);
                            game.revealNeighbours(x, y);
                        }
                        if (content == 9)
                            game.gameOver();
                        else {
                            game.revealNeighbours(x, y);
                            game.checkIfWin();
                        }

                        if (hidden)
                            showContent();
                    }

                    clicked = true;
                }

                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (!clicked)
                        changeFlagged();
                }
            }
        });
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (!clicked) {
                        if (!game.getStarted()) {
                            game.setClickedFirst(x, y);
                            game.revealNeighbours(x, y);
                        }
                        if (content == 9)
                            game.gameOver();
                        else {
                            game.revealNeighbours(x, y);
                            game.checkIfWin();
                        }

                        if (hidden)
                            showContent();
                    }

                    clicked = true;
                }

                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (!clicked)
                        changeFlagged();
                }
            }
        });
    }

    public Label getLabel() {
        return label;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void hideContent() {
        label.setBackground(new Background(new BackgroundFill(Color.rgb(12, 126, 204), new CornerRadii(10), Insets.EMPTY)));
        label.setText("");
    }

    public void showContent() {
        hidden = false;
        clicked = true;
        label.setGraphic(null);
        if (flagged)
            game.incMine();

        label.setText("" + content);
        label.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)));

        switch (content) {
            case 0:
                label.setText("");
                break;
            case 1:
                label.setTextFill(Color.BLUE);
                break;
            case 2:
                label.setTextFill(Color.GREEN);
                break;
            case 3:
                label.setTextFill(Color.BROWN);
                break;
            case 4:
                label.setTextFill(Color.RED);
                break;
            case 9:
                label.setText("");
                label.setGraphic(imageViewBomb);
                break;
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    private void changeFlagged() {
        if (!flagged) {
            flagged = true;
            label.setGraphic(imageViewFlag);
            game.decMine();
        }
        else {
            flagged = false;
            label.setGraphic(null);
            game.incMine();
        }
    }

    public void disableClick() {
        label.setOnMouseClicked(null);
    }

    public boolean isClicked() {
        return clicked;
    }
}
