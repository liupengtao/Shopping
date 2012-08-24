/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author liupengtao
 */
public class Fetch {

    public static void main(String[] args) throws URISyntaxException, Exception {
        String[] sellerSites = {
            "http://mixqueen.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120824"
        }, tokens, params,paramItem;
        int[] pageNums = {40};
        String site, scheme, host, path, paramkey, paramValue, searches, url, firstRe = "(http|https)\\://(.+?)(/.*)", secondRe = "", line;
        HttpClient httpclient = new DefaultHttpClient();
        URIBuilder builder = new URIBuilder();
        for (int i = 0, len = sellerSites.length; i < len; i++) {
            File dir;
            site = sellerSites[i];
            tokens = site.split("\\?");
            Pattern p = Pattern.compile(firstRe);
            Matcher m = p.matcher(tokens[0]);

            if (m.find()) {
                builder.setScheme(m.group(1)).setHost(m.group(2)).setPath(m.group(3));
                dir = new File("/home/liupengtao/Projects/Shopping/datas/" + m.group(2));
                if (!dir.exists()) {
                    dir.mkdir();
                }
            } else {
                throw new Exception("url error");
            }

            params = tokens[1].split("&");
            System.out.println(params.length);

            for (int j = 0, l = params.length; j < l; j++) {
                paramItem = params[j].split("=");
                paramkey = paramItem[0];
                if (paramItem.length == 2) {
                    paramValue = paramItem[1];
                } else {
                    paramValue = "";
                }
                builder.setParameter(paramkey,paramValue);
            }

            load(httpclient,builder,dir,1,m.group(2));

            for (int page = 2; page <= pageNums[i]; page++) {
                builder.setParameter("pageNum", page + "");
                load(httpclient,builder,dir,page,m.group(2));
            }
        }
    }

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
