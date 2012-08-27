class Item < ActiveRecord::Base
  attr_accessible :big_img_url, :desc, :evaluate_score, :item_id, :link_url, :middle_img_url, :price, :review_count, :sale_amount, :small_img_url, :title

  belongs_to :category
  belongs_to :shop
end
