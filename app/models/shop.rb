class Shop < ActiveRecord::Base
  attr_accessible :name, :url
  has_many :items
  has_many :shop_categorieses,:class_name => 'ShopCategories'

  has_many :categories,:through => :shop_categorieses
end
