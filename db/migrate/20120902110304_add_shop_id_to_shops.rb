class AddShopIdToShops < ActiveRecord::Migration
  def change
    add_column :shops, :shop_id, :string
  end
end
