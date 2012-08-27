class Category < ActiveRecord::Base
  attr_accessible :c_id, :category_id, :name
  acts_as_tree
  has_many :items

  has_many :shop_categorieses,:class_name => 'ShopCategories'
  has_many :shops,:through => :shop_categorieses
end
