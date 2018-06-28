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
        upload_to_database u = new upload_to_database();

        while(true){
            try{
                data = data_collect_buffer.readLine();
                if(data.chars().filter(ch -> ch == '&').count() == 2){
                    successful_signup_flag = u.upload_data(u.user_db_file_name,data,"signup");
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
