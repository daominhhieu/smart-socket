package Lab_ex;

import java.io.*;
import java.net.Socket;

public class s4_e3_client {
    public static void main(String[] args) throws IOException {
        String ipserver = "localhost";

        int portserver = 12345;

        String r;

        Socket cl = new Socket(ipserver, portserver);
        BufferedReader inp = new BufferedReader(new InputStreamReader(cl.getInputStream()));
        BufferedWriter oup = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
        BufferedReader key = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("r = ");
        r = key.readLine();
        oup.write(r);
        oup.newLine();
        oup.flush();

        System.out.println("Area: " + inp.readLine());




        if(inp!=null){
            key.close();
        }

        if(key!=null){
            key.close();
        }

        if(oup!=null){
            oup.close();
        }

        if(cl!=null){
            cl.close();
        }

    }
}
