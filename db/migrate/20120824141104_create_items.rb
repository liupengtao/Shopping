class CreateItems < ActiveRecord::Migration
  def change
    create_table :items do |t|
      t.string :item_id
      t.string :link_url
      t.string :small_img_url
      t.string :middle_img_url
      t.string :big_img_url
      t.text :desc
      t.string :title
      t.string :price
      t.string :evaluate_score
      t.integer :sale_amount
      t.integer :review_count

      t.timestamps
    end
  end
end
