import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppClient extends Application {

    private TicTacToeLogic gameLogic;
    private Client client;
    int playerId;

    Button[] buttons;
    Label infoLabel;

    private void setButtonText(int buttonId, String buttonText){
        buttons[buttonId].setText(buttonText);
    }

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
        infoLabel = new Label();
        gridPane.add(infoLabel, 3, 3);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        client = new Client();
        client.connect();

        ClientView view = new ClientView();
        gameLogic = new TicTacToeLogic();
        client.listenForObject( object -> {
            playerId = (int)object;
        });
        int opponentId;
        if(playerId == 0){
            opponentId = 1;
        } else {
            opponentId = 0;
        }
        primaryStage.setScene(view.createTicTacToeScene(500, 500, this));
        primaryStage.show();

        Thread listeningThread = new Thread(){
            public void run(){
                client.listenForObjects( (object) -> {
                    byte point = (byte) object;
                    System.out.println(point);
                    final int p = (int)point;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(point == 10 || point == 11) {
                                if((point-10) == playerId){
                                    infoLabel.setText("You Win!");
                                } else {
                                    infoLabel.setText("You Lost!");
                                }
                            } else {
                                setButtonText(point, gameLogic.OnFieldClick(point, opponentId));
                                int gameState = gameLogic.getGameState();
                                if(gameState != -1){
                                    client.sendObject((byte)(gameState+10));
                                }
                            }
                        }
                    });
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