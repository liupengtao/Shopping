package dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import tools.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liupengtao
 * Date: 12-8-27
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class ShopDao {
    public static void main(String[] args) throws SQLException, IOException {
        String[] datas = {"http://100f1.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;百分之一女装店", "http://catworld.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;CATWORLD女装", "http://mixqueen.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;天使之城", "http://dang.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;当当阁正品服饰", "http://asando.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;as安都女装店", "http://munaiyi007.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;沐乃衣日韩女装", "http://koudai.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;妖精的口袋", "http://mayiazhai.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;蚂蚁阿宅", "http://shop34939047.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;桔熊", "http://fenhongdabuwawa.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;粉红大布娃娃", "http://yxjy.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;蝶恋", "http://judyroom.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;朱迪的房间", "http://xiaoweitongxue.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;双金冠小维人气女装潮店", "http://jujujuju.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;JUJU美衣", "http://rbeauty.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;r.beauty瑞丽依佳人", "http://sugargirl.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;七格格TOP潮品", "http://mumuhome.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;木木家", "http://9mg-cn.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;魅力印象", "http://tomgoods.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;卡卡是公主", "http://vvxiaomei.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;Vivi小妹", "http://youjianxiaoxi.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;又见小夕", "http://yeyiguan.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;KUOSE 阔色 夜衣馆", "http://hxnh.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;花信年华 淑女屋", "http://shop56634.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;天之大 瑟之鸣", "http://shop33859144.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;衣依衣", "http://baiwuxi.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;百武西", "http://myss9969.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;美妍时尚", "http://99youi.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;我是优衣", "http://gdhouse.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;VS.tw", "http://ciaodafanfan.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;ciao 俏", "http://niu88.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;妞妞旺铺", "http://hmanh.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;hmanh红漫红", "http://911m.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;911M 日韩风", "http://mix-studio.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;流行解码", "http://love-upon.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;UPON呵呵也也", "http://kwyyy.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;卡哇伊时尚小铺", "http://lihomme.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;LIHOMME历织造", "http://297892008.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;拉风阁", "http://941mai.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;潮衣色", "http://shop33227145.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;KUEGOU/酷衣购", "http://husige.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;ACE C RED胡四哥", "http://lako.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;懒客男装 LAKO Homme", "http://dara2010.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;no1dara官方男装店", "http://shop34472414.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;酷炫非主流男装", "http://three-gun.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;一号商城", "http://taotao10000.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;随心索衣男装", "http://sir7.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=5-e&search=y&initiative_id=shopz_20120827;@Sir7 舍弃", "http://es365.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;E尚小站男装", "http://limusa.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;Dultony", "http://cheapmonday.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;虎门镇牛仔潮品", "http://es-tee.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;衣格良品", "http://1314.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;ZJ-Body", "http://jimiao.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;禾子男装", "http://soul-city.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;soul-city", "http://jusken.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;JUSKEN 杰仕克恩", "http://dten.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;兑服饰", "http://pennypei.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;纺布者", "http://kokozone.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;KokoZone官方男装店", "http://hiphoper.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;Dancer Jack街头嘻哈", "http://couk.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;COUK商务休闲男装", "http://17hua.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;17画", "http://yykzj.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;优衣裤之家", "http://gg1188.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;完美男人服饰店", "http://454198248.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;男孩很忙", "http://caribbean-x.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;加勒比", "http://shop33230938.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;富柏官方男装店", "http://820609.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;酷男服饰", "http://ycy519ycy.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;ycy519ycy", "http://p4413.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;prince4413", "http://2008mrchen.taobao.com/?q=&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&initiative_id=shopz_20120827;Mr.chen"};
        insert(datas);
    }

    private static void insert(String[] datas) throws SQLException, IOException {
        Connection connection = Utils.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into shops(url,name,created_at,updated_at,logo,shop_id) values(?,?,?,?,?,?)");
        String[] fields;
        for (String data : datas) {
            fields = data.split(";");
            System.out.println(fields.length);
            for (int i = 0,len = fields.length; i < len; i++) {
                preparedStatement.setString(i + 1,fields[i]);
            }
            Document document = Jsoup.connect(fields[0]).get();
            preparedStatement.setTimestamp(3,new Timestamp(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.setTimestamp(4,new Timestamp(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.setString(5,document.select(".logo-img").first().attr("src"));
            preparedStatement.setString(6,document.select("[shopid]").first().attr("shopid"));
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        Utils.colseConn();
    }
}
