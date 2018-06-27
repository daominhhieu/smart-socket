package Lab_ex;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class s4_e3_server {
    public static ServerSocket svr;

    public static void main(String[] args) throws IOException {
        svr = new ServerSocket(12345);

        for(int i = 0; i<10; i++){
            Client_Thread_Handler();
        }

        while (true){}
    }

    public static void Client_Thread_Handler(){

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("start");
                    Socket s = svr.accept();
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
                    listen(in, out);
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        System.out.println("end");
    }
    public static void listen(InputStreamReader i, OutputStreamWriter o) throws IOException {
        BufferedReader inp = new BufferedReader(i);
        BufferedWriter oup = new BufferedWriter(o);
        int r = Integer.parseInt(inp.readLine());
        double A = (r*r*6.28);
        oup.write(Double.toString(A));
        oup.newLine();
        oup.flush();
        oup.close();
    }
}
