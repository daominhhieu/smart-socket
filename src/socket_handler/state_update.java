package socket_handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class state_update {

    public state_update(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fetch_data();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    arduino();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

    private void fetch_data() throws IOException {
        System.out.println("Waiting for socket");

        ServerSocket svr = new ServerSocket(8083);
        Socket a = svr.accept();

        String data = null;

        System.out.println("Connected! to " + a.getRemoteSocketAddress());
        BufferedReader bf = new BufferedReader(new InputStreamReader(a.getInputStream()));

        while(true){
            try{
                data = bf.readLine();
                if(data != null){
                    if(data.contains("btn1"))
                        System.out.println(data);
                }else{
                    a.close();
                    a = svr.accept();
                    bf = new BufferedReader(new InputStreamReader(a.getInputStream()));
                }
            }catch(NullPointerException e){
                a.close();
                a = svr.accept();
                bf = new BufferedReader(new InputStreamReader(a.getInputStream()));
            }
        }
    }

    private void arduino() throws IOException {
        Scanner i = new Scanner(System.in);

        ServerSocket svr = new ServerSocket(2312);
        System.out.println("Listening");
        Socket a = svr.accept();

        BufferedReader r = new BufferedReader(new InputStreamReader(a.getInputStream()));
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(a.getOutputStream()));

        String data = null;

        while(true){
            try{
                data = r.readLine();
                if(data!=null) {
                    System.out.println(data);
                    w.write(read_from_database(data));
                    w.flush();
                }else{
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

    private String read_from_database(String device){
        Connection c = null;
        Statement stmt = null;
        String a = "0000";

        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/Database/socket_database.db");
            System.out.println("Opened database successfully");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM "+device+";" );

            while(rs.next()){
                a = a + rs.getString("STATE");
            }
            c.commit();

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }



}
