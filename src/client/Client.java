package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket client;

    public void connect(String[] args) {
        System.out.println("Establishing connection. Please wait ...");

        String serverName = args[0];
        int serverPort = Integer.parseInt(args[1]);
        String filename = args[2];
        
        try {
            BufferedReader csvFile = new BufferedReader(new FileReader(filename));

            client = new Socket(serverName, serverPort);
            System.out.println("Connected: " + client.getLocalAddress().getHostName());

            PrintStream output = new PrintStream(client.getOutputStream());

            String line;
            while ((line = csvFile.readLine()) != null) {
                output.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        
        if (args.length != 3) {
            System.out.println("Server hostname, port and csv filepath needed.");
            return;
        }

        Client client = new Client();
        client.connect(args);

    }
}
