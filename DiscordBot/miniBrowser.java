package DiscordBot;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.lang.StringBuffer;

interface IgetRequest{
    static Document get_request(String url, String userInput) throws IOException, MalformedURLException{
        Connection con = Jsoup.connect(url + userInput);
        Document page = con.get();

        if(con.response().statusCode() == 200){
            return page;
        }
        else{
            return null;
        } 
    }

    public ArrayList<String> getData(ArrayList<String> userInput);
}

class ContentHandler implements IgetRequest{
    private ArrayList<String> sites = new ArrayList<>();
    
    @Override
    public ArrayList<String> getData(ArrayList<String> userInput){
        StringBuffer Input = new StringBuffer();
        
        for(String str : userInput){
            Input.append(str + " ");
        }

        try {
            Document page = IgetRequest.get_request("https://www.google.it/search?q=", Input.toString());
            Elements elements = page.select("a[href]");

            for(int link = 13; link <= 18; link++){
                sites.add(elements.get(link).absUrl("href"));
            }
            return sites;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class miniBrowser{
    public ArrayList<String> getContent(String[] args){
        ContentHandler obj = new ContentHandler();
        ArrayList<String> arr = new ArrayList<>();

        for(String str : args){
            arr.add(str);
        }
        arr.remove(0);
        return obj.getData(arr);
    }
}
