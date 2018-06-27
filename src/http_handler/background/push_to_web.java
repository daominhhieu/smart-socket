package http_handler.background;

import com.sun.net.httpserver.HttpExchange;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class push_to_web {
    private HttpExchange e = null;

    public push_to_web(HttpExchange ex) throws IOException {
        e = ex;
    }

    public void page(int dir) throws IOException {
        main_structure(new jsoup_distributor().page_doc_list().get(dir).html());
    }

    public void error(String error_message, String error_type, int dir) throws IOException {
        Document doc = new jsoup_distributor().page_doc_list().get(dir);
        Document err = new jsoup_distributor().error_doc(doc, error_message, error_type);
        main_structure(err.html());

    }

    private void main_structure(String content) throws IOException {
        BufferedWriter html_out = new BufferedWriter(new OutputStreamWriter(e.getResponseBody()));

        e.getResponseHeaders().set("Content-Type", "text/html");
        e.sendResponseHeaders(200, 0);

        html_out.write(content);
        html_out.newLine();
        html_out.flush();

        html_out.close();
    }
}
