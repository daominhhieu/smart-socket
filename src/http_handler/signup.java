package http_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;
import http_handler.background.upload_to_database;
import http_handler.background.data_processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class signup implements HttpHandler {

    private String successful_signup_flag = null;


    private ServerSocket Login_Gate;
    {
        try {
            Login_Gate = new ServerSocket(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        push_to_web p = new push_to_web(httpExchange);
        String requestMethod = httpExchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("GET")){
            System.out.println("here");
            if(successful_signup_flag!=null){
                p.error(successful_signup_flag, "danger", 3);
            }else{
                p.page(3);
            }
            successful_signup_flag = null;
        }

        if (requestMethod.equalsIgnoreCase("POST")) {
            push_data();

        }
    }

    private void push_data() throws IOException {

        data_processing d = new data_processing();

        System.out.println("Waitting for user to input information");
        Socket gate_Soc = Login_Gate.accept();

        System.out.println("Collecting....");

        BufferedReader data_collect_buffer = new BufferedReader(new InputStreamReader(gate_Soc.getInputStream()));
        String data = null;

        while(true){
            try{
                data = data_collect_buffer.readLine();
                if(data.contains("&sp")){
                    check_data(d.split(data,"su=","&sp="));
                    break;
                }
            }catch(NullPointerException e){
                gate_Soc.close();
                gate_Soc = Login_Gate.accept();
                data_collect_buffer = new BufferedReader(new InputStreamReader(gate_Soc.getInputStream()));
            }
        }

        data_collect_buffer.close();
        gate_Soc.close();
    }

    private void check_data(String[] s) throws IOException {
        if(s.length == 2){
            if(!(s[0].equals("") || s[1].equals(""))){
                System.out.println(s[0]);
                System.out.println(s[1]);


                successful_signup_flag = new upload_to_database().login(s[0],s[1]);

            }else {
                successful_signup_flag = "Please input S O M E   T H I N G";
                System.out.println("Please input something!!");
            }
        }
        else{
            System.out.println(" Error");
        }

    }

}
