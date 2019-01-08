import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppClient extends Application {

    private TicTacToeLogic gameLogic;
    private Client client;
    int playerId;
    Button[] buttons;

    public void createContent(GridPane gridPane){
        buttons = new Button[9];
        for(int i=0; i<9; ++i){
            buttons[i] = new Button(" ");
            final int id = i;
            buttons[i].setOnAction(event -> {
                String content = gameLogic.OnFieldClick(id, playerId);
                if(content != null){
                    buttons[id].setText(content);
                    client.sendObject((byte)id);
                }
            });
            gridPane.add(buttons[i], i/3, i%3);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        client = new Client();
        client.connect();

        ClientView view = new ClientView();
        gameLogic = new TicTacToeLogic();
        playerId = 0;
        int opponentId = 1;
        primaryStage.setScene(view.createTicTacToeScene(500, 500, this));
        primaryStage.show();

        Thread listeningThread = new Thread(){
            public void run(){
                client.listenForObjects( (object) -> {
                    byte point = (byte) object;
                    buttons[point].setText(gameLogic.OnFieldClick(point, opponentId));
                });
                client.disconnect();
            }
        };

        listeningThread.start();

        primaryStage.setOnCloseRequest( (event) -> {
            client.sendObject(null);
            listeningThread.interrupt();
            client.disconnect();
        });

    }

    public static void main(String[] args){
        launch(args);
    }
}