require 'test_helper'

class ItemsControllerTest < ActionController::TestCase
  setup do
    @item = items(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:items)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create item" do
    assert_difference('Item.count') do
      post :create, item: { big_img_url: @item.big_img_url, desc: @item.desc, evaluate_score: @item.evaluate_score, item_id: @item.item_id, link_url: @item.link_url, middle_img_url: @item.middle_img_url, price: @item.price, review_count: @item.review_count, sale_amount: @item.sale_amount, small_img_url: @item.small_img_url, title: @item.title }
    end

    assert_redirected_to item_path(assigns(:item))
  end

  test "should show item" do
    get :show, id: @item
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @item
    assert_response :success
  end

  test "should update item" do
    put :update, id: @item, item: { big_img_url: @item.big_img_url, desc: @item.desc, evaluate_score: @item.evaluate_score, item_id: @item.item_id, link_url: @item.link_url, middle_img_url: @item.middle_img_url, price: @item.price, review_count: @item.review_count, sale_amount: @item.sale_amount, small_img_url: @item.small_img_url, title: @item.title }
    assert_redirected_to item_path(assigns(:item))
  end

  test "should destroy item" do
    assert_difference('Item.count', -1) do
      delete :destroy, id: @item
    end

    assert_redirected_to items_path
  end
end
