import java.net.*;
import java.io.*;

public class server {
    // The server will be listening on this port
    private ServerSocket serverSocket;

    public server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        while (!serverSocket.isClosed()) {
            try {
                // Server waits for a client to connect
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                // Create a new thread for the client
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();

            } catch (IOException e) {
                System.out.println("Server closed");
            }
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing server socket");
        }
    }

    public static void main(String args[]) throws Exception {
        // Create a server socket listening on port 4444
        ServerSocket serverSocket = new ServerSocket(4444);
        server server = new server(serverSocket);
        server.startServer();
    }
}