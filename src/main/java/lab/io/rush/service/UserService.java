package lab.io.rush.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lab.io.rush.dao.UserDao;
import lab.io.rush.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	
	public User addUser(User user){
		return userDao.addUser(user);
	}
	
	public User getUserByWeiboID(String weibo_id){
		return userDao.getUserByWeiboID(weibo_id);
	}
	
	public boolean bindMailByWeibo(String mail, String weibo_id){
		return userDao.bindMailByWeibo(mail, weibo_id);
	}
}
