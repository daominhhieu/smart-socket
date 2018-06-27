package http_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;

import java.io.IOException;

public class upload_css implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        push_to_web p = new push_to_web(httpExchange);
        String requestMethod = httpExchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")){
            p.main_structure(new File);
        }

        if (requestMethod.equalsIgnoreCase("POST")) {

        }

    }
}
