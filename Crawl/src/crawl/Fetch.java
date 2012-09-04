/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import tools.Utils;

/**
 *
 * @author liupengtao
 */
public class Fetch {

    public static void main(String[] args) throws URISyntaxException, Exception {
        String[] sellerSites = {
            "http://mixqueen.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120824"
        }, tokens, params,paramItem;
        final Parser parser = new Parser();
        final int pageNums = 20;
        String site, scheme, host, path, paramkey, paramValue, searches, url, firstRe = "(http|https)\\://(.+?)(/.*)", secondRe = "", line,shop_id;
        HttpClient httpclient = new DefaultHttpClient();
        URIBuilder builder = new URIBuilder();
        final PrintStream printStream = new PrintStream("/home/liupengtao/Projects/Shopping/logs.txt");
        Connection shopConn = Utils.getConn();
        PreparedStatement preparedStatement = shopConn.prepareStatement("select * from shops");
         final ResultSet resultSet = preparedStatement.executeQuery();
        ExecutorService executors = Executors.newCachedThreadPool();

//        ResultSet itemIdsRS = shopConn.prepareStatement("select item_id from items").executeQuery();

        while (resultSet.next()) {
            System.out.println("begin loop");
            executors.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        Parser parser = new Parser();
                        String shop_id = resultSet.getString("shop_id");
                        String site = resultSet.getString("url");
                        System.out.println("get out");

                        List<String> ids = new ArrayList<String>(),urls = new ArrayList<String>();
                        Map<String,Map<String ,String >> simpleInfo = new HashMap<String, Map<String, String>>();
                        for (int page = 1; page <= pageNums; page++) {
                            parser.fetchItemIds(Jsoup.connect(site).data("pageNum",page + "").get(),simpleInfo,ids,urls,printStream);
                        }
                        System.out.println(ids.size());
                        parser.parse(ids,urls,shop_id,printStream);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (SQLException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (URISyntaxException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            });
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Deprecated
    private static void load(HttpClient httpclient, URIBuilder builder, File dir,int page,String filePrefix) throws URISyntaxException, IOException {
        String line = null;
        URI uri = builder.build();
        HttpGet httpget = new HttpGet(uri);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        FileWriter fw = new FileWriter(new File(dir, filePrefix + "_" + page + ".txt"));
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(instream,"gbk"));
                while ((line = br.readLine()) != null) {
                    fw.write(line);
                }
            } finally {
                instream.close();
                if (br != null) {
                    br.close();
                }
            }
        }
    }
}
