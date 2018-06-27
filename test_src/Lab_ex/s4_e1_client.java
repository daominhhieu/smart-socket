package Lab_ex;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class s4_e1_client {

    private static InetAddress host;
    private static final int PORT = 22222;

    public static void main(String[] args) throws IOException {
        host = InetAddress.getLocalHost();
        accessServer();
    }

    private static void accessServer() throws IOException {
        Socket link = new Socket(host, PORT);

        BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        BufferedWriter ouput = new BufferedWriter(new OutputStreamWriter(link.getOutputStream()));

        Scanner userEntry = new Scanner(System.in);

        String message, response;

        do {
            response = input.readLine();
            System.out.println("\nSERVER> "+response);

            System.out.println("Piece of information which client want to send to the server");
            message = userEntry.nextLine();

            ouput.write(message);
            ouput.newLine();
            ouput.flush();

        }while (!message.equals("BYE"));

        System.out.println("\n* Connection has been released *");
        ouput.close();
        input.close();
        link.close();

    }
}
