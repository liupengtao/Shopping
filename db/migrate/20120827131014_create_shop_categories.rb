class CreateShopCategories < ActiveRecord::Migration
  def change
    create_table :shop_categories do |t|
      t.integer :shop_id
      t.integer :category_id

      t.timestamps
    end
  end
end
