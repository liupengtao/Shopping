class RenameItemIdToItemImgs < ActiveRecord::Migration
  def change
    change_table :item_imgs do |t|
      t.remove :item_id
      t.string :item_id
    end
  end
end
