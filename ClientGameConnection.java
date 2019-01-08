import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGameConnection {

    Socket socket1;
    Socket socket2;
    ObjectOutputStream out1;
    ObjectOutputStream out2;
    ObjectInputStream in1;
    ObjectInputStream in2;
    Thread connectionThread1;
    Thread connectionThread2;
    int index;

    public ClientGameConnection(Server server, ServerSocket serverSocket, int index) throws IOException {
        this.index = index;
        socket1 = serverSocket.accept();
        sendObjectToClient(0, 0);
        socket2 = serverSocket.accept();
        sendObjectToClient(1, 1);

        connectionThread1 = new Thread(() -> {
            try {
                if(in1 == null) {
                    in1 = new ObjectInputStream(socket1.getInputStream());
                }
                Object inputObject;
                while ((inputObject = in1.readObject()) != null) {
                    sendObjectToClient(1 , inputObject);
                }
                sendObjectToClient(0, null);
                server.removeClient(index);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        connectionThread2 = new Thread(() -> {
            try {
                if(in2 == null) {
                    in2 = new ObjectInputStream(socket2.getInputStream());
                }
                Object inputObject;
                while ((inputObject = in2.readObject()) != null) {
                    sendObjectToClient(0 , inputObject);
                }
                sendObjectToClient( 1, null);
                server.removeClient(index);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        connectionThread1.start();
        connectionThread2.start();
    }

    public void sendObjectToClient(int id, Object object) throws IOException {
        if(id == 0){
            if(out1 == null) {
                out1 = new ObjectOutputStream(socket1.getOutputStream());
            }
            out1.writeObject(object);
        } else if(id == 1){
            if(out2 == null) {
                out2 = new ObjectOutputStream(socket2.getOutputStream());
            }
            out2.writeObject(object);
        }
    }

}
