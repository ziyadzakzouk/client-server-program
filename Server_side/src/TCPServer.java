import java.io.*;
import java.net.*;

public class TCPServer {

	public static void main(String args[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;

		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {

			Socket connectionSocket = welcomeSocket.accept();

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));

			Thread readerThread = new Thread(() -> {
				while (true) {
					String serverInput = null;
					try {
						serverInput = inFromServer.readLine();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					try {
						outToClient.writeBytes(serverInput + "\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			readerThread.start();

			while ((clientSentence = inFromClient.readLine()) != null) {
				System.out.println("Client: " + clientSentence);
			}

			System.out.println(clientSentence);

			capitalizedSentence = clientSentence.toUpperCase() + '\n';

			outToClient.writeBytes(capitalizedSentence);

		}
	}
}
