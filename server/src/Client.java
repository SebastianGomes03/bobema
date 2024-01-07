import java.net.*;
import java.util.Scanner;
import java.io.*;

class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.0.103", 4444);
            System.out.println("Connected to server");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username");
            String username = scanner.nextLine();

            Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();
            scanner.close();

        } catch (Exception e) {
            System.out.println("Error connecting to server");
        }
    }

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Client(Socket socket, String username) {
        try {

            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = username;

        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            // the first message is the username
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // the second and consequent message is the message
            // while the conetction stays, it will send messages
            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }

            scanner.close();

        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String msgFromChat;

                while (socket.isConnected()) {
                    try {
                        msgFromChat = bufferedReader.readLine();
                        if (msgFromChat != null) {
                            System.out.println(msgFromChat);
                        }
                    } catch (Exception e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }

            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        } catch (Exception e) {
            System.out.println("Error closing client handler");
        }
    }

}