class CreateItemImgs < ActiveRecord::Migration
  def change
    create_table :item_imgs do |t|
      t.string :item_id
      t.string :img_url

      t.timestamps
    end
  end
end
