package http_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http_handler.background.push_to_web;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class signup_css implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        push_to_web p = new push_to_web(httpExchange);
        String requestMethod = httpExchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")){
            String content = new Scanner(new File("src/HTML_Templates/css/signup.css")).useDelimiter("\\Z").next();
            //System.out.println(content);
            p.main_structure(content,"stylesheet");
        }

        if (requestMethod.equalsIgnoreCase("POST")) {

        }

    }
}
