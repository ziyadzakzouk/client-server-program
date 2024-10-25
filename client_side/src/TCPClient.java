import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] argv) throws Exception {
    	
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Type 'CONNECT' to connect to the server");
        String sentence = inFromUser.readLine();

        if ("CONNECT".equals(sentence)) {
            Socket clientSocket = new Socket("192.168.103.12", 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            
            System.out.println("Connected to server.");
            
            Thread readerThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = inFromServer.readLine()) != null) {
                        System.out.println("SERVER: " + serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readerThread.start();

            while (true) {

                String userInput = inFromUser.readLine();
                outToServer.writeBytes(userInput + '\n');
            }

        }else {
            System.out.println("Invalid input. Type 'CONNECT' to connect to the server.");
        }
    }
}
