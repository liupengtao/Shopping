# coding: utf-8
require "net/http"
require "hpricot"
require "iconv"

class Crawl
  #Net::HTTPHeader.content_type('text/html',{'charset' => 'gb2312'})
  content = Net::HTTP.get(URI('http://mixqueen.taobao.com/search.htm?spm=a1z10.3.17.88&search=y&viewType=grid&orderType=_hotsell&pageNum=9#anchor'))
  #Iconv.iconv("GBK//IGNORE","UTF-8//IGNORE",content)
  p content.force_encoding('gbk')
  doc = Hpricot content
  p doc.search("//ul[@class='shop-list']")
end