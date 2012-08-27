class HomeController < ApplicationController
  def index
    @top_ctys = Category.where(:parent_id => nil)
    @shops = Shop.limit(3)
  end
end
