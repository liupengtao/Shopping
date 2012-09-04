#encoding: utf-8
class User < ActiveRecord::Base
  attr_accessible :email, :nickname, :password, :username,:password_confirmation
  validates_confirmation_of :password,:message => "两次密码输入不一致！"
  validates_presence_of :password_confirmation,:message => "密码确认不能为空！"
  validates_presence_of :username,:message => "用户名不能为空！"
  validates_presence_of :password,:message => "密码不能为空！"
  validates_presence_of :email,:message => "邮箱不能为空！"
  validates_uniqueness_of :email,:message => "此邮箱已经注册！"
  validates_uniqueness_of :username,:message => "此用户名已经被注册！"
end
