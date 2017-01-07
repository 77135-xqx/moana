package lab.io.rush.model;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@SuppressWarnings("serial")
@PersistenceCapable(table="user")
public class User implements Serializable {

	@PrimaryKey
	@Column(name="user_id")
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private int user_id;
	
	//微博ID
	@Column(name="weibo_id")
	private String weibo_id;
	
	//微博名
	@Column(name="weibo_name")
	private String weibo_name;
	
	//此系统暂未实现QQ登录，留着备用
	//qqID
	@Column(name="qq_id")
	private String qq_id;
	
	//QQ名
	@Column(name="qq_name")
	private String qq_name;
	
	//邮箱
	@Column(name="mail")
	private String mail;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getWeibo_id() {
		return weibo_id;
	}
	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}
	public String getWeibo_name() {
		return weibo_name;
	}
	public void setWeibo_name(String weibo_name) {
		this.weibo_name = weibo_name;
	}
	public String getQq_id() {
		return qq_id;
	}
	public void setQq_id(String qq_id) {
		this.qq_id = qq_id;
	}
	public String getQq_name() {
		return qq_name;
	}
	public void setQq_name(String qq_name) {
		this.qq_name = qq_name;
	}
	
}
