class Shop < ActiveRecord::Base
  attr_accessible :name, :url
  has_many :items,:class_name => "Item",:foreign_key => "shop_id"
  has_many :shop_categorieses,:class_name => 'ShopCategories'

  has_many :categories,:through => :shop_categorieses

  def items
    Item.where("shop_id=#{shop_id}")
  end
end
