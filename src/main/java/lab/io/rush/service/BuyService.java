package lab.io.rush.service;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lab.io.rush.dao.RecordDao;
import lab.io.rush.model.Record;
import lab.io.rush.model.User;

@Service
public class BuyService {
	@Autowired
	private RecordDao recordDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Resource(name = "redisTemplate") 
	private ListOperations<String, Record> listOps;
	
	public void addRecordToRedis(User user){
		Record record = new Record();
		record.setDate(new Date());
		record.setUser_id(user.getUser_id());
		listOps.leftPush("record", record);
	}
	
	public void sendSuccessMail(User user){
		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo("771350067@qq.com");//接受者  
		simpleMail.setFrom("qx77135@sina.com");//发送者,这里还可以另起Email别名，不用和xml里的username一致  
		simpleMail.setSubject("购票成功通知");//主题  
		simpleMail.setText("尊敬的"+user.getWeibo_name()+ "您好，您于成功购买电影票一张，请注意观看！");//邮件内容  
		mailSender.send(simpleMail);
	}
	
	public void redisToMariadb(){
		Record record = null;
		while((record=listOps.leftPop("record"))!=null){
			recordDao.addRecord(record);
		}
	}
}
