import java.io.*;
import java.net.*;

public class Server {

    public static final int MAX_THREAD_COUNT = 5;

    public ClientConnection[] clientConnections = new ClientConnection[MAX_THREAD_COUNT];

    public void sendToAllClients(int current, Object object) throws IOException {
        for(int i=0; i<MAX_THREAD_COUNT; ++i){
            if(i == current) continue;
            if(clientConnections[i] != null && clientConnections[i].connected){
                clientConnections[i].sendObjectToClient(object);
            }
        }
    }

    public void removeClient(int id){
        clientConnections[id] = null;
    }

    private int checkForEmptyClientSlots(){
        for(int i=0; i<MAX_THREAD_COUNT; ++i){
            if(clientConnections[i] == null){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        Server server = new Server();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 6666");
            System.exit(-1);
        }
        try {
            int id = -1;
           while(true) {
               if((id = server.checkForEmptyClientSlots()) != -1) {
                   server.clientConnections[id] = new ClientConnection(server, serverSocket, id);
               }
           }
        } catch (IOException e) {
            System.out.println("Accept failed: 6666");
            System.exit(-1);
        }
    }
}