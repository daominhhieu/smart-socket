package http_handler;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;

import java.io.IOException;

public class Error implements HttpHandler {

    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        push_to_web p = new push_to_web(exchange);


        if (requestMethod.equalsIgnoreCase("GET")){
            p.page(0);
        }

        if (requestMethod.equalsIgnoreCase("POST")) {
            post();
        }
    }

    private void post(){

    }
}
