import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawl {
    public static void main(String[] args) {
try{
       Document doc = Jsoup.connect("http://mixqueen.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120824").get();
       Elements newsHeadlines = doc.select("ul.shop-list");
       System.out.println(newsHeadlines);}
catch(Exception e){}
    }
}
