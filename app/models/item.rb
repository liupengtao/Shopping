class Item < ActiveRecord::Base
  attr_accessible :big_img_url, :desc, :evaluate_score, :item_id, :link_url, :middle_img_url, :price, :review_count, :sale_amount, :small_img_url, :title

  belongs_to :category
  belongs_to :shop,:class_name => "Shop",:foreign_key => "shop_id",:foreign_type => "string"
  has_many :item_imgses,:class_name => "ItemImgs"

  def item_imgses
    ItemImgs.where("item_id=#{item_id}")
  end
end
