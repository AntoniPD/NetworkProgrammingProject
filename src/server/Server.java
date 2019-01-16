package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.opencsv.CSVReader;

public class Server {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Server port needed.");
            return;
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket server = new ServerSocket(port);

        while (true) {
            System.out.println("Waiting for client to connect...");

            Socket connectionToClient = server.accept();
            File tempCsvFile = File.createTempFile("received-csv", ".tmp");
            tempCsvFile.deleteOnExit();
            inputStreamToFile(tempCsvFile, connectionToClient.getInputStream());
            connectionToClient.close();

            // Parse csv
            CSVReader csvReader = new CSVReader(new FileReader(tempCsvFile));
            List<String[]> csvRows = csvReader.readAll();

            FrequentItemsetMiner miner = new FrequentItemsetMiner();
            Results results = miner.mine(csvRows);

            System.out.println(results);
        }
    }

    private static void inputStreamToFile(File file, InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(file);

        String line;
        while ((line = in.readLine()) != null) {
            out.println(line);
        }

        out.close();
        in.close();
    }
}