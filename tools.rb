# encoding:utf-8
class Tools
  shops = <<-data
    女装
1.	百分之一女装店
http://shop279839.taobao.com/
2.	CATWORLD女装
http://shop34489791.taobao.com/
3.	天使之城
http://shop217225.taobao.com/
4.	当当阁正品服饰
http://shop36061565.taobao.com/
5.	as安都女装店
http://shop33066234.taobao.com/
6.	沐乃衣日韩女装
http://shop33495993.taobao.com/
7.	妖精的口袋
http://shop33756187.taobao.com/
8.	蚂蚁阿宅
http://shop36192275.taobao.com/
9.	桔熊
http://shop34939047.taobao.com/
10.	粉红大布娃娃
http://shop34565346.taobao.com/
11.	蝶恋
http://shop33019079.taobao.com/
12.	朱迪的房间
http://shop4509.taobao.com/
13.	双金冠小维人气女装潮店
http://shop33771053.taobao.com/
14.	JUJU美衣
http://shop33070710.taobao.com/
15.	r.beauty瑞丽依佳人
http://shop36201743.taobao.com/
16.	七格格TOP潮品
http://shop33847557.taobao.com/
17.	木木家
http://shop35732013.taobao.com/
18.	魅力印象
http://shop35041409.taobao.com/
19.	卡卡是公主
http://shop34298732.taobao.com/
20.	Vivi小妹
http://shop60945846.taobao.com/
21.	又见小夕
http://shop35891627.taobao.com/
22.	KUOSE 阔色 夜衣馆
http://shop33827216.taobao.com/
23.	花信年华 淑女屋
http://shop36762138.taobao.com/
24.	天之大 瑟之鸣
http://shop56634.taobao.com/
25.	衣依衣
http://shop33859144.taobao.com/
26.	百武西
http://shop34032628.taobao.com/
27.	美妍时尚
http://shop33707554.taobao.com/
28.	我是优衣
http://shop34074509.taobao.com/
29.	VS.tw
http://shop33426048.taobao.com/
30.	ciao 俏
http://shop33905840.taobao.com/
31.	妞妞旺铺
http://shop33003356.taobao.com/
32.	hmanh红漫红
http://shop36956091.taobao.com/
33.	911M 日韩风
http://shop36341347.taobao.com/
34.	流行解码
http://shop33369039.taobao.com/
35.	UPON呵呵也也
http://shop36121713.taobao.com/


男装
1.	卡哇伊时尚小铺
http://shop33822288.taobao.com/
2.	LIHOMME历织造
http://shop33207704.taobao.com/
3.	拉风阁
http://shop58075105.taobao.com/
4.	潮衣色
http://shop36979287.taobao.com/
5.	KUEGOU/酷衣购
http://shop33227145.taobao.com/
6.	ACE C RED胡四哥
http://shop36115917.taobao.com/
7.	懒客男装 LAKO Homme
http://shop36588772.taobao.com/
8.	no1dara官方男装店
http://shop34954879.taobao.com/
9.	酷炫非主流男装
http://shop34472414.taobao.com/
10.	一号商城
http://shop33753786.taobao.com/
11.	随心索衣男装
http://shop37122187.taobao.com/
12.	@Sir7 舍弃
http://shop57073658.taobao.com/
13.	E尚小站男装
http://shop34302428.taobao.com/
14.	Dultony
http://shop33352447.taobao.com/
15.	虎门镇牛仔潮品
http://shop36542178.taobao.com/
16.	衣格良品
http://shop35860897.taobao.com/
17.	ZJ-Body
http://shop1003559.taobao.com/
18.	禾子男装
http://shop35928747.taobao.com/
19.	soul-city
http://shop58426022.taobao.com/
20.	JUSKEN 杰仕克恩
http://shop36672787.taobao.com/
21.	兑服饰
http://shop36455830.taobao.com/
22.	纺布者
http://shop57502798.taobao.com/
23.	KokoZone官方男装店
http://shop34185392.taobao.com/
24.	Dancer Jack街头嘻哈
http://shop33082938.taobao.com/
25.	COUK商务休闲男装
http://shop34196533.taobao.com/
26.	17画
http://shop36446699.taobao.com/
27.	优衣裤之家
http://shop60068735.taobao.com/
28.	完美男人服饰店
http://shop60682743.taobao.com/
29.	男孩很忙
http://shop59401266.taobao.com/
30.	加勒比
http://shop62510299.taobao.com/
31.	富柏官方男装店
http://shop33230938.taobao.com/
32.	酷男服饰
http://shop36545641.taobao.com/
33.	ycy519ycy
http://shop34823816.taobao.com/
34.	prince4413
http://shop34366844.taobao.com/
35.	Mr.chen
http://shop36950851.taobao.com/
  data
result = []
  shops.scan(/(?:(?:[\d\.]+)\s*(.*?))\s*(http:\/\/[\w\.]+\/)/).each do |item|
    result << "#{item[1]};#{item[0]}"
  end
  p result
end