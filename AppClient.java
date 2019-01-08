import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppClient extends Application {

    static Vec2d lastPos = null;
    static final int WIDTH = 500, HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Client client = new Client();
       // client.connect();

        ClientView view = new ClientView();
        TicTacToeLogic gameLogic = new TicTacToeLogic();

        int playerId = 0;
        primaryStage.setScene(view.createTicTacToeScene(500, 500, playerId, gameLogic));
        primaryStage.show();
/*
        Thread listeningThread = new Thread(){
            public void run(){
                client.listenForObjects( (object) -> {
                    byte line = (byte) object;
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.setLineWidth(1);
                    graphicsContext.moveTo(line.getStartX(), line.getStartY());
                    graphicsContext.lineTo(line.getEndX(), line.getEndY());
                    graphicsContext.stroke();
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
        */
    }

    public static void main(String[] args){
        launch(args);
    }
}