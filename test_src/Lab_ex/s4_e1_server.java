package Lab_ex;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class s4_e1_server {
    public static Socket Soc;
    public static void main(String args[]) throws IOException {
        ServerSocket svr = new ServerSocket(22222);
        Soc = svr.accept();
        accesClient();
    }

    public static void accesClient() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(Soc.getInputStream()));
        BufferedWriter ouput = new BufferedWriter(new OutputStreamWriter(Soc.getOutputStream()));

        Scanner userEntry = new Scanner(System.in);

        String message, response;
        ouput.write("Sever is ready for the connection");
        ouput.newLine();
        ouput.flush();

        do {

            response = input.readLine();
            System.out.println("\nCLIENT> "+response);

            if (response.equals("BYE")){
                break;
            }

            System.out.println("Piece of information which client want to send to the server: ");
            message = userEntry.nextLine();

            ouput.write(message);
            ouput.newLine();
            ouput.flush();

        }while (true);

        ouput.close();
        input.close();
        Soc.close();

    }
}
