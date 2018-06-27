package http_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;


import java.io.*;

public class mainpage implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {

        String requestMethod = httpExchange.getRequestMethod();
        push_to_web p = new push_to_web(httpExchange);

        if (requestMethod.equalsIgnoreCase("GET")){
            p.page(0);
        }
        if (requestMethod.equalsIgnoreCase("POST")){
            post();
        }

    }
    private void post() throws IOException{

    }
}
