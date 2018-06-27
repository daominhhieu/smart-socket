import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class testSocket {
    public static void main(String[] args) throws IOException {

        System.out.println("Waiting for socket");

        ServerSocket svr = new ServerSocket(8080);
        Socket a = svr.accept();

        String data = null;

        System.out.println("Connected! to " + a.getRemoteSocketAddress());
        BufferedReader bf = new BufferedReader(new InputStreamReader(a.getInputStream()));

        while(true){
            try{
                data = bf.readLine();
                if(data.contains("&p")){

                }
            }catch(NullPointerException e){
                a.close();
                a = svr.accept();
                bf = new BufferedReader(new InputStreamReader(a.getInputStream()));
            }
        }
    }
}
