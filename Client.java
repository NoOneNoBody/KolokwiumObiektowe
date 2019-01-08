import java.io.*;
import java.net.*;
public class Client{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connect(){
        try {
            socket = new Socket("localhost", 6666);
            in = null;
            out = null;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }
    }

    public void sendObject(Object object){
        try {
            if(out == null) {
                out = new ObjectOutputStream(socket.getOutputStream());
            }
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForObject(ReceiveObjectListener listener){
        try {
            if(in == null) {
                in = new ObjectInputStream(socket.getInputStream());
            }
            Object inputObject;
            inputObject = in.readObject();
            listener.onReceive(inputObject);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listenForObjects(ReceiveObjectListener listener){
        try {
            if(in == null) {
                in = new ObjectInputStream(socket.getInputStream());
            }
            Object inputObject;
            while ((inputObject = in.readObject()) != null) {
                listener.onReceive(inputObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
