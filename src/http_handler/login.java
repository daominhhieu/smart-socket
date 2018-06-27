package http_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class login implements HttpHandler {
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
            System.out.println("Here!");
            p.page(0);
        }

        if (requestMethod.equalsIgnoreCase("POST")) {

            push_data(p);
        }
    }


    private void push_data(push_to_web err) throws IOException {
        System.out.println("Waitting for user to input information");
        Socket gate_Soc = Login_Gate.accept();

        System.out.println("Collecting....");

        BufferedReader data_collect_buffer = new BufferedReader(new InputStreamReader(gate_Soc.getInputStream()));
        String data = null;

        while(true){
            try{
                data = data_collect_buffer.readLine();
                if(data.contains("&p")){
                    checkdata(split(data),err);
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


    //Checking login information Might wanna put some more condition in the future !!!!!!
    private void checkdata(String[] s, push_to_web error){
        if(s.length == 2){
            if(!(s[0].equals("") || s[1].equals(""))){
                System.out.println(s[0]);
                System.out.println(s[1]);
            }else {

                System.out.println("Please input something!!");
            }
        }
        else{
            System.out.println(" Error");
        }
    }

    private String[] split(String in){

        String Password =null;
        String Username =null;

        if(in.indexOf("&p=")!=-1 && in.indexOf("u=")!=-1){
            int len_of_username = in.indexOf("&p=") - in.indexOf("u=") - 2;
            char[] testdata1 = new char[len_of_username];

            in.getChars(in.indexOf("u=")+2,in.indexOf("&p="),testdata1,0);
            Password = String.copyValueOf(testdata1);


            int len_testdata2 = in.length() - in.indexOf("p=") - 2;
            char[] testdata2 = new char[len_testdata2];

            in.getChars(in.indexOf("p=")+2,in.length(),testdata2,0);
            Username = String.copyValueOf(testdata2);

        }else{
            System.out.println("Message Invalid");
        }
        return new String[]{Password, Username};
    }

}
