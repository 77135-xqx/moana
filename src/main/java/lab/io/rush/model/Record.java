package lab.io.rush.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


//购买记录
@SuppressWarnings("serial")
@PersistenceCapable(table="record")
public class Record implements Serializable{
	
	@PrimaryKey
	@Column(name="record_id")
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private int record_id;
	
	//关联到某个用户
	@Column(name="user_id")
	private int user_id;
	
	//购买时间
	@Column(name="date")
	private Date date;

	public int getRecord_id() {
		return record_id;
	}

	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


}
