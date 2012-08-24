package crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: liupengtao
 * Date: 12-8-24
 * Time: 下午8:04
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    public void parse(File dir,FileWriter out) throws IOException {
        File[] files = dir.listFiles();
        String linkUrl,smallImgUrl,itemId,desc,price,middleImgUrl,bigImgUrl,evaluateScore;
        int saleAmount,reviewCount;
        Document document;
        Pattern idRe = Pattern.compile("\\?.*?id=(\\d+)");
        Pattern floatRe = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Pattern scoreRe = Pattern.compile("(\\d+(?:(?:\\.\\d+)?分))");
        for (File file : files) {
            document = Jsoup.parse(file,"gbk");
           Elements items = document.select("ul.shop-list li .item");
            for (Element item : items) {
                linkUrl = item.select(".pic a").first().attr("href");
                itemId = idRe.matcher(linkUrl).group(1);
                smallImgUrl = item.select(".pic a img").first().attr("src");
                desc = item.select(".desc a").first().text();
                price = floatRe.matcher(item.select(".price strong").first().text()).group(1);
                saleAmount = Integer.parseInt(item.select(".sales-amount em").first().text());

                Document detailDoc = Jsoup.connect(linkUrl).get();
                middleImgUrl = detailDoc.select(".tb-booth:eq(0) img:eq(0)").first().attr("src");

                Element itemPros = detailDoc.select(".tb-property:eq(0)").first();
                evaluateScore = scoreRe.matcher(itemPros.select(".tb-evaluate:eq(0)").text()).group(1);
                reviewCount = Integer.parseInt(detailDoc.select(".J_ReviewsCount:eq(0)").first().text());
                bigImgUrl = detailDoc.select(".ks-imagezoom-viewer:eq(0) img:eq(0)").first().attr("src");
                //TODO insert or update to database;
            }
        }
    }
}

class GoodItem {
    private String itemId,linkUrl,smallImgUrl,desc,price,middleImgUrl,bigImgUrl,evaluateScore;
    private int saleAmount,reviewCount;
    class Comment {

    }


}
