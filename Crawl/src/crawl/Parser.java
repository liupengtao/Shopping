package crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: liupengtao
 * Date: 12-8-24
 * Time: 下午8:04
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    private String url = "jdbc:mysql://localhost:3306/Shopping_development?useUnicode=true&characterEncoding=utf-8";
    private String username = "root";
    private String password = "";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private HttpClient httpclient = new DefaultHttpClient();
    private SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");

    public Parser() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        preparedStatement = connection.prepareStatement("insert into items(item_id,link_url,small_img_url,middle_img_url,big_img_url,intro,title,price,evaluate_score,sale_amount,review_count,created_at,updated_at) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }

    @Deprecated()
    public void parse(File dir, FileWriter out, FileWriter log) throws IOException, SQLException {
        if (!dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        String linkUrl, smallImgUrl, itemId = null, desc, price = null, middleImgUrl, bigImgUrl, evaluateScore = null;
        int saleAmount, reviewCount;
        Document document;
        Pattern idRe = Pattern.compile("\\?.*?id=(\\d+)");
        Pattern floatRe = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Pattern scoreRe = Pattern.compile("(\\d+(?:(?:\\.\\d+)?分))");
        Matcher matcher;
        for (File file : files) {
            document = Jsoup.parse(file, "utf-8");
            Elements items = document.select("ul.shop-list li .item");
            for (Element item : items) {
                linkUrl = item.select(".pic a").first().attr("href");
                matcher = idRe.matcher(linkUrl);
                if (matcher.find()) {
                    itemId = matcher.group(1);
                }

                smallImgUrl = item.select(".pic a img").first().attr("src");
                desc = item.select(".desc a").first().text();
                matcher = floatRe.matcher(item.select(".price strong").first().text());
                if (matcher.find()) {
                    price = matcher.group(1);
                }

                saleAmount = Integer.parseInt(item.select(".sales-amount em").first().text());

                HttpGet httpget = new HttpGet(linkUrl);
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                StringBuilder sb = new StringBuilder();
                String line = null;
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(instream, "gbk"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                    } finally {
                        instream.close();
                        if (br != null) {
                            br.close();
                        }
                    }
                }

                Document detailDoc = Jsoup.parse(sb.toString());
                middleImgUrl = detailDoc.select(".tb-booth:eq(0) img:eq(0)").first().attr("src");

                Element itemPros = detailDoc.select(".tb-property").first();

                String commonDataUrl = itemPros.select("#J_RateStar").first().attr("data-commonapi");
                Document commonDataDoc = Jsoup.connect(commonDataUrl).get();
                String commonData = commonDataDoc.select("body").text().trim();
                commonData = commonData.substring(1, commonData.length() - 1);
                JSONObject commonObject = JSON.parseObject(commonData);

                evaluateScore = commonObject.getJSONObject("data").getString("correspond");
                reviewCount = Integer.parseInt(commonObject.getJSONObject("data").getJSONObject("count").getString("total"));
                bigImgUrl = null;//detailDoc.select(".ks-imagezoom-viewer img").first().attr("src");

                this.preparedStatement.setString(1, itemId);
                this.preparedStatement.setString(2, linkUrl);
                this.preparedStatement.setString(3, smallImgUrl);
                this.preparedStatement.setString(4, middleImgUrl);
                this.preparedStatement.setString(5, bigImgUrl);
                this.preparedStatement.setString(6, desc);
                this.preparedStatement.setString(7, "");
                this.preparedStatement.setString(8, price);
                this.preparedStatement.setString(9, evaluateScore);
                this.preparedStatement.setInt(10, saleAmount);
                this.preparedStatement.setInt(11, reviewCount);
                this.preparedStatement.setTimestamp(12, new Timestamp(Calendar.getInstance().getTimeInMillis()));
                this.preparedStatement.setTimestamp(13, new Timestamp(Calendar.getInstance().getTimeInMillis()));

                this.preparedStatement.addBatch();
            }
            this.preparedStatement.executeBatch();
        }
    }

    public void parse(List<String> ids, List<String> urls, PrintStream out) throws IOException, SQLException, InterruptedException {
        String linkUrl = null, smallImgUrl = null, itemId = null, desc, price = null, middleImgUrl, bigImgUrl, evaluateScore = null, color;
        int saleAmount = 0, reviewCount, index = 0;
        Pattern idRe = Pattern.compile("\\?.*?id=(\\d+)");
        Pattern floatRe = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher;

        for (String url : urls) {
            index++;
            while (true) {
                try {
                    Document detailDoc = Jsoup.connect(url).get();
                    if (detailDoc == null) {
                        this.log("linkUrl错误或相应的商品已经被删除，linkUrl是" + url + ".At Time:", out);
                        continue;
                    }
                    Element itemPros = null;
//                    try {
                        desc = detailDoc.select(".tb-detail-hd h3").first().text();
                        middleImgUrl = detailDoc.select(".tb-booth:eq(0) img:eq(0)").first().attr("src");
                        itemPros = detailDoc.select(".tb-property").first();
                        price = itemPros.select("#J_StrPrice").first().text();
//                    } catch (Exception e) {
//                        this.log("详细页面dom结构变化！itemId是" + itemId + ".At Time:", out);
//                        e.printStackTrace(out);
//                        out.append(File.separator);
//                        return;
//                    }
                    String commonDataUrl = itemPros.select("#J_RateStar").first().attr("data-commonapi");
                    Document commonDataDoc = Jsoup.connect(commonDataUrl).get();
//                    try {
                        String commonData = commonDataDoc.select("body").text().trim();
                        commonData = commonData.substring(1, commonData.length() - 1);
                        JSONObject commonObject = JSON.parseObject(commonData);

                        evaluateScore = commonObject.getJSONObject("data").getString("correspond");
                        reviewCount = Integer.parseInt(commonObject.getJSONObject("data").getJSONObject("count").getString("total"));
//                    } catch (Exception e) {
//                        this.log("json结构变化！" + ".At Time:", out);
//                        e.printStackTrace(out);
//                        out.append(File.separator);
//                        return;
//                    }
                    bigImgUrl = null;//detailDoc.select(".ks-imagezoom-viewer img").first().attr("src");

                    this.preparedStatement.setString(1, itemId);
                    this.preparedStatement.setString(2, linkUrl);
                    this.preparedStatement.setString(3, smallImgUrl);
                    this.preparedStatement.setString(4, middleImgUrl);
                    this.preparedStatement.setString(5, bigImgUrl);
                    this.preparedStatement.setString(6, desc);
                    this.preparedStatement.setString(7, "");
                    this.preparedStatement.setString(8, price);
                    this.preparedStatement.setString(9, evaluateScore);
                    this.preparedStatement.setInt(10, saleAmount);
                    this.preparedStatement.setInt(11, reviewCount);
                    this.preparedStatement.setTimestamp(12, new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    this.preparedStatement.setTimestamp(13, new Timestamp(Calendar.getInstance().getTimeInMillis()));

                    this.preparedStatement.addBatch();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    TimeUnit.SECONDS.sleep(2);
                    continue;
                }
            }
            if (index % 30 == 0) {
                this.preparedStatement.executeBatch();
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    private void log(String msg, PrintStream out) {
        out.append(msg + this.format.format(new Date()));
    }

    public void fetchItemIds(Document document, Map<String, Map<String, String>> simpleInfo, List<String> ids, List<String> urls, PrintStream out) {
        String linkUrl = null, smallImgUrl, itemId = null, desc, price = null, middleImgUrl, bigImgUrl, evaluateScore = null;
        int saleAmount, reviewCount;
        Pattern idRe = Pattern.compile("\\?.*?id=(\\d+)");
        Pattern floatRe = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher;
        Elements items = null;
        try {
            items = document.select("ul.shop-list li .item");
        } finally {
            if (items == null) {
                this.log("页面dom结构发生变化，请尽快修改解析策略！" + ".At Time:", out);
                return;
            }
        }
        for (Element item : items) {
            try {
                linkUrl = item.select(".pic a").first().attr("href");
                matcher = idRe.matcher(linkUrl);
                if (matcher.find()) {
                    itemId = matcher.group(1);
                }

                smallImgUrl = item.select(".pic a img").first().attr("src");
                Map<String, String> attrs = new HashMap<String, String>();
                attrs.put("link_url", linkUrl);
                attrs.put("small_img_url", smallImgUrl);
                simpleInfo.put(itemId, attrs);
                ids.add(itemId);
                urls.add(linkUrl);
            } catch (NumberFormatException e) {
                this.log("主页面中每个商品的dom结构发生变化，请尽快修改解析策略！" + ".At Time:", out);
                e.printStackTrace(out);
                out.append(File.separator);
                return;
            }
        }
    }
}
