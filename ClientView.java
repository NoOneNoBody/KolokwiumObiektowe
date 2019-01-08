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



    public Scene createTicTacToeScene(final int width, final int height, AppClient controler){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        controler.createContent(gridPane);

        infoLabel = new Label();
        gridPane.add(infoLabel, 9, 0);

        return new Scene(gridPane, width, height);
    }
}
