class User < ActiveRecord::Base
  attr_accessible :email, :nickname, :password, :username
end
