
import com.sun.net.httpserver.HttpServer;
import http_handler.*;
import http_handler.Error;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;



public class main_http_server {

    public static void main(String[] args) throws IOException {

        InetSocketAddress addr = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(addr, 0);

        server.createContext("/", new mainpage());
        server.createContext("/login/", new login());
        server.createContext("/signup/", new signup());
        server.createContext("/404/", new Error());
        server.createContext("/css/bulma.min.css", new upload_css());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        System.out.println("Server is listening on port 8080" );
    }
}