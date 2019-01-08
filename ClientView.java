import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ClientView {

    private Label infoLabel;

    private void createButtons(GridPane gridPane, int playerId, TicTacToeLogic gameLogic){
        Button[] buttons = new Button[9];
        for(int i=0; i<9; ++i){
            buttons[i] = new Button(" ");
            final int id = i;
            buttons[i].setOnAction(event -> {
                String content = gameLogic.OnFieldClick(id, playerId);
                if(content != null)
                    buttons[id].setText(content);
            });
            gridPane.add(buttons[i], i/3, i%3);
        }
    }

    public Scene createTicTacToeScene(final int width, final int height, int playerId, TicTacToeLogic gameLogic){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        createButtons(gridPane, playerId, gameLogic);

        infoLabel = new Label();
        gridPane.add(infoLabel, 9, 0);

        return new Scene(gridPane, width, height);
    }
}
