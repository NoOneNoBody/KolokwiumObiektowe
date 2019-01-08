import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    int index;
    Thread connectionThread;
    public boolean connected;

    public ClientConnection(Server server, ServerSocket serverSocket, int index) throws IOException {
        this.index = index;
        socket = serverSocket.accept();

        connectionThread = new Thread(() -> {
            connected = true;
            try {
                if(in == null) {
                    in = new ObjectInputStream(socket.getInputStream());
                }
                Object inputObject;
                while ((inputObject = in.readObject()) != null) {
                    server.sendToAllClients(index, inputObject);
                }
                sendObjectToClient(null);
                connected = false;
                server.removeClient(index);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        connectionThread.start();
    }

    public void sendObjectToClient(Object object) throws IOException {
        if(out == null) {
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        out.writeObject(object);
    }

}
