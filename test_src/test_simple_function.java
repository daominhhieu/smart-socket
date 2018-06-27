
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class test_simple_function {

    public static void main( String args[] ) throws IOException {

        Scanner i = new Scanner(System.in);

        ServerSocket svr = new ServerSocket(2312);
        System.out.println("Listening");
        Socket a = svr.accept();

        BufferedReader r = new BufferedReader(new InputStreamReader(a.getInputStream()));
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(a.getOutputStream()));

        String data = null;
        String data_in = null;

        while(true){
            try{
                //System.err.println("here1");
                data = r.readLine();
                if(data!=null) {
                    System.err.println("here1");
                    System.out.println(data);
                    w.write("caca");
                    w.flush();
                    System.err.println("here2");
                    r.close();
                    a.close();
                    a = svr.accept();
                    r = new BufferedReader(new InputStreamReader(a.getInputStream()));
                    w = new BufferedWriter(new OutputStreamWriter(a.getOutputStream()));
                }

            }catch(NullPointerException e){
                System.err.println("err");
                r.close();
                a.close();
                a = svr.accept();
                r = new BufferedReader(new InputStreamReader(a.getInputStream()));
            }
        }
    }
}