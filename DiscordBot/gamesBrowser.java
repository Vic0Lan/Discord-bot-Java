package DiscordBot;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

interface IgetRequest2{
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

    public ArrayList<String> retrieveData(ArrayList<String> userInput);
}

class Browser implements IgetRequest2{
    private ArrayList<String> sites = new ArrayList<>();

    private static String processPayload(String userInput){
        String[] arr = userInput.split(" ");
        return String.join("-", arr).toLowerCase();
    }

    @Override
    public ArrayList<String> retrieveData(ArrayList<String> userInput){
        StringBuffer input = new StringBuffer();
        
        for(String str : userInput){
            input.append(str + " ");
        }
        String payload = processPayload(input.toString());

        try {
            Document page = IgetRequest2.get_request("https://steamunlocked.net/?s=", payload);
            Elements elements = page.select("a[href]");

            for(Element link  : elements){
                if(link.absUrl("href").contains(payload) == true){
                    sites.add(link.absUrl("href"));
                }
                // else if(sites.isEmpty() == true){
                //     sites.add("Non ho potuto trovare quello che cercavi...");
                // }
                // else{
                //     sites.clear();
                // }
            }
            return sites;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


public class gamesBrowser{
    private String[] params;

    gamesBrowser(String[] params){
        this.params = params;
    }

    public ArrayList<String> getContent(){
        Browser obj = new Browser();
        ArrayList<String> arr = new ArrayList<>();

        for(String str : params){
            arr.add(str);
        }
        arr.remove(0);
        return obj.retrieveData(arr);
    }
}
