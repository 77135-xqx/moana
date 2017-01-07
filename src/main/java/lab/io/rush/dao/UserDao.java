package lab.io.rush.dao;


import java.util.List;

import javax.annotation.Resource;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import lab.io.rush.model.User;


@Component("userDao")
public class UserDao  {
	@Resource(name="persistenceManagerFactory")
	private PersistenceManagerFactory persistenceManagerFactory;
	
	//新增用户
	public User addUser(User user){ 
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {	
			return pm.makePersistent(user);
		} finally {
			System.out.println("123");
			pm.close();
		}	
	}
	
	//通过微博ID获取用户
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public User getUserByWeiboID(String weibo_id) {
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			Query query = pm.newQuery("javax.jdo.query.SQL", "SELECT * FROM user WHERE weibo_id = '"+weibo_id+"'"); 
			query.setClass(User.class);
			List<User> userList = (List<User>) query.execute();
			if(userList.size()>0){
				return userList.get(0);
			}
			else {
				return null;
			}
		} finally {
			pm.close();
		}
	}
	
	//绑定邮箱
	//写法比较臃肿，应该会有更好的写法
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean bindMailByWeibo(String mail, String weibo_id){
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			Query query = pm.newQuery("javax.jdo.query.SQL", "SELECT * FROM user WHERE weibo_id = '"+weibo_id+"'"); 
			query.setClass(User.class);
			query.setUnique(true);
			User user = (User) query.execute();
			user.setMail(mail);
			pm.refresh(user);
			//暂时未找到如何判断更新失败
			return true;
		} finally {
			pm.close();
		}
	}

	
}
