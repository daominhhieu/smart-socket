package http_handler.background;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//This class is for providing convenient way of reference html files
public class jsoup_distributor{

    private String[] pages = {"404", "main", "login","signup", "about","error_message"};// the order of html files implemented
    private String[] outline_directory = {"src/HTML_Templates/", ".html"};

    public static String invalid_username = "invalid username";
    public static String invalid_pasword = "invalid password";


    // This method is for generating a list of html files path for later implemented purpose
    public ArrayList<String> path_list(){
        ArrayList list_of_path = new ArrayList();

        for(int i=0; i<pages.length; i++){
            list_of_path.add(outline_directory[0] + pages[i] + outline_directory[1]);
        }

       return list_of_path;
    }

    // This method is for generate a list of Jsoup Documents for later implemented purpose
    public ArrayList<Document> page_doc_list() throws IOException {
        ArrayList<Document> list_of_doc = new ArrayList<Document>();
        Iterator<String> path_list_itr = path_list().iterator();

        while (path_list_itr.hasNext()){
            list_of_doc.add(Jsoup.parse(new File(path_list_itr.next()), "UTF-8"));
        }

        return list_of_doc;
    }

    public Document error_doc(Document target, String error_mess, String error_type)throws IOException{
        Element main_message = page_doc_list().get(5).getElementById(error_type);
        main_message.getElementsByClass("message-header").append(error_mess);

        target.body().append(main_message.html());


        return target;
    }
}
