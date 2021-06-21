import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void start(){
        try {

            ServerSocket serverSocket = new ServerSocket(8888);
            while (true){
                Socket client = serverSocket.accept();
                new Thread(new ClientListener(client)).start();
                System.out.println("connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
