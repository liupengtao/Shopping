class ChangeDetailsToItems < ActiveRecord::Migration
  def up
    change_table :items do|t|
      t.rename :desc, :intro
    end
  end

  def down
    change_table :items do |t|
     t.rename :intro, :desc
    end
  end
end
