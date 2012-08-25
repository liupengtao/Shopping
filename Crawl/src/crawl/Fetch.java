/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author liupengtao
 */
public class Fetch {

    public static void main(String[] args) throws URISyntaxException, Exception {
        String[] sellerSites = {
            "http://mixqueen.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120824"
        }, tokens, params,paramItem;
        Parser parser = new Parser();
        int[] pageNums = {40};
        String site, scheme, host, path, paramkey, paramValue, searches, url, firstRe = "(http|https)\\://(.+?)(/.*)", secondRe = "", line;
        HttpClient httpclient = new DefaultHttpClient();
        URIBuilder builder = new URIBuilder();
        PrintStream printStream = new PrintStream("/home/liupengtao/Projects/Shopping/logs.txt");
        for (int i = 0, len = sellerSites.length; i < len; i++) {
//            File dir;
            site = sellerSites[i];
            List<String> ids = new ArrayList<String>(),urls = new ArrayList<String>();
            Map<String,Map<String ,String >> simpleInfo = new HashMap<String, Map<String, String>>();
//            parser.parse(Jsoup.connect(site).get(), printStream,1);
//            tokens = site.split("\\?");
//            Pattern p = Pattern.compile(firstRe);
//            Matcher m = p.matcher(tokens[0]);
//
//            if (m.find()) {
//                builder.setScheme(m.group(1)).setHost(m.group(2)).setPath(m.group(3));
////                dir = new File("/home/liupengtao/Projects/Shopping/datas/" + m.group(2));
////                if (!dir.exists()) {
////                    dir.mkdir();
////                }
//            } else {
//                throw new Exception("url error");
//            }
//
//            params = tokens[1].split("&");
//            System.out.println(params.length);
//
//            for (int j = 0, l = params.length; j < l; j++) {
//                paramItem = params[j].split("=");
//                paramkey = paramItem[0];
//                if (paramItem.length == 2) {
//                    paramValue = paramItem[1];
//                } else {
//                    paramValue = "";
//                }
//                builder.setParameter(paramkey,paramValue);
//            }
//
//            load(httpclient,builder,dir,1,m.group(2));
//            TimeUnit.SECONDS.sleep(10);
            for (int page = 1; page <= pageNums[i]; page++) {
                parser.fetchItemIds(Jsoup.connect(site).data("pageNum",page + "").get(),simpleInfo,ids,urls,printStream);
//                builder.setParameter("pageNum", page + "");
//                load(httpclient,builder,dir,page,m.group(2));
//                parser.parse(Jsoup.connect(site).data("pageNum",page + "").get(), printStream,page);
//                TimeUnit.SECONDS.sleep(10);
            }
            System.out.println(ids.size());
            parser.parse(ids,urls,printStream);
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
