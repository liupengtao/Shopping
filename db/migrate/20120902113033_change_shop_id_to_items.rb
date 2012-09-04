class ChangeShopIdToItems < ActiveRecord::Migration
  def change
    change_table :items do |t|
      t.remove :shop_id
      t.string :shop_id
    end
  end
end
