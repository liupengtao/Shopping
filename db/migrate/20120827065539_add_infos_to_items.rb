class AddInfosToItems < ActiveRecord::Migration
  def change
    add_column :items, :category_id, :integer
    add_column :items, :shop_id, :integer
    add_column :items, :colors, :string
  end
end
