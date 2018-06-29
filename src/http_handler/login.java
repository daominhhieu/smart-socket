package http_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;
import http_handler.background.upload_to_database;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class login implements HttpHandler {
    private String successful_login_flag = null;
    private ServerSocket Login_Gate;
    {
        try {
            Login_Gate = new ServerSocket(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        push_to_web p = new push_to_web(httpExchange);
        String requestMethod = httpExchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("GET")){
            if(successful_login_flag!=null){
                System.out.println(successful_login_flag);
                p.error(successful_login_flag, "danger", 1);
            }else{
                p.page(1);
            }
            successful_login_flag = null;
        }

        if (requestMethod.equalsIgnoreCase("POST")) {
            System.out.println("Here!");
            push_data(p);
        }
    }


    private void push_data(push_to_web err) throws IOException {
        System.out.println("Waitting for user to input information");
        Socket gate_Soc = Login_Gate.accept();

        System.out.println("Collecting....");

        long count = 0;

        BufferedReader data_collect_buffer = new BufferedReader(new InputStreamReader(gate_Soc.getInputStream()));
        String data = null;
        upload_to_database u = new upload_to_database();

        while(true){
            try{
                data = data_collect_buffer.readLine();
                if(data.chars().filter(ch -> ch == '&').count() == 1){
                    successful_login_flag = u.upload_data(u.user_db_file_name,data,"login");
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
}
