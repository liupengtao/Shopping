class ItemImgs < ActiveRecord::Base
  attr_accessible :img_url, :item_id
  belongs_to :item,:class_name => "Item",:foreign_key => "item_id"
end
