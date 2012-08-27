package dao;

import tools.Utils;

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
    public static void main(String[] args) throws SQLException {
        String[] datas = {"http://shop279839.taobao.com/;百分之一女装店", "http://shop34489791.taobao.com/;CATWORLD女装", "http://shop217225.taobao.com/;天使之城", "http://shop36061565.taobao.com/;当当阁正品服饰", "http://shop33066234.taobao.com/;as安都女装店", "http://shop33495993.taobao.com/;沐乃衣日韩女装", "http://shop33756187.taobao.com/;妖精的口袋", "http://shop36192275.taobao.com/;蚂蚁阿宅", "http://shop34939047.taobao.com/;桔熊", "http://shop34565346.taobao.com/;粉红大布娃娃", "http://shop33019079.taobao.com/;蝶恋", "http://shop4509.taobao.com/;朱迪的房间", "http://shop33771053.taobao.com/;双金冠小维人气女装潮店", "http://shop33070710.taobao.com/;JUJU美衣", "http://shop36201743.taobao.com/;r.beauty瑞丽依佳人", "http://shop33847557.taobao.com/;七格格TOP潮品", "http://shop35732013.taobao.com/;木木家", "http://shop35041409.taobao.com/;魅力印象", "http://shop34298732.taobao.com/;卡卡是公主", "http://shop60945846.taobao.com/;Vivi小妹", "http://shop35891627.taobao.com/;又见小夕", "http://shop33827216.taobao.com/;KUOSE 阔色 夜衣馆", "http://shop36762138.taobao.com/;花信年华 淑女屋", "http://shop56634.taobao.com/;天之大 瑟之鸣", "http://shop33859144.taobao.com/;衣依衣", "http://shop34032628.taobao.com/;百武西", "http://shop33707554.taobao.com/;美妍时尚", "http://shop34074509.taobao.com/;我是优衣", "http://shop33426048.taobao.com/;VS.tw", "http://shop33905840.taobao.com/;ciao 俏", "http://shop33003356.taobao.com/;妞妞旺铺", "http://shop36956091.taobao.com/;hmanh红漫红", "http://shop36341347.taobao.com/;911M 日韩风", "http://shop33369039.taobao.com/;流行解码", "http://shop36121713.taobao.com/;UPON呵呵也也", "http://shop33822288.taobao.com/;卡哇伊时尚小铺", "http://shop33207704.taobao.com/;LIHOMME历织造", "http://shop58075105.taobao.com/;拉风阁", "http://shop36979287.taobao.com/;潮衣色", "http://shop33227145.taobao.com/;KUEGOU/酷衣购", "http://shop36115917.taobao.com/;ACE C RED胡四哥", "http://shop36588772.taobao.com/;懒客男装 LAKO Homme", "http://shop34954879.taobao.com/;no1dara官方男装店", "http://shop34472414.taobao.com/;酷炫非主流男装", "http://shop33753786.taobao.com/;一号商城", "http://shop37122187.taobao.com/;随心索衣男装", "http://shop57073658.taobao.com/;@Sir7 舍弃", "http://shop34302428.taobao.com/;E尚小站男装", "http://shop33352447.taobao.com/;Dultony", "http://shop36542178.taobao.com/;虎门镇牛仔潮品", "http://shop35860897.taobao.com/;衣格良品", "http://shop1003559.taobao.com/;ZJ-Body", "http://shop35928747.taobao.com/;禾子男装", "http://shop58426022.taobao.com/;soul-city", "http://shop36672787.taobao.com/;JUSKEN 杰仕克恩", "http://shop36455830.taobao.com/;兑服饰", "http://shop57502798.taobao.com/;纺布者", "http://shop34185392.taobao.com/;KokoZone官方男装店", "http://shop33082938.taobao.com/;Dancer Jack街头嘻哈", "http://shop34196533.taobao.com/;COUK商务休闲男装", "http://shop36446699.taobao.com/;17画", "http://shop60068735.taobao.com/;优衣裤之家", "http://shop60682743.taobao.com/;完美男人服饰店", "http://shop59401266.taobao.com/;男孩很忙", "http://shop62510299.taobao.com/;加勒比", "http://shop33230938.taobao.com/;富柏官方男装店", "http://shop36545641.taobao.com/;酷男服饰", "http://shop34823816.taobao.com/;ycy519ycy", "http://shop34366844.taobao.com/;prince4413", "http://shop36950851.taobao.com/;Mr.chen"};
        insert(datas);
    }

    private static void insert(String[] datas) throws SQLException {
        Connection connection = Utils.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into shops(url,name,created_at,updated_at) values(?,?,?,?)");
        String[] fields;
        for (String data : datas) {
            fields = data.split(";");
            System.out.println(fields.length);
            for (int i = 0,len = fields.length; i < len; i++) {
                preparedStatement.setString(i + 1,fields[i]);
            }
            preparedStatement.setTimestamp(3,new Timestamp(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.setTimestamp(4,new Timestamp(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        Utils.colseConn();
    }
}
