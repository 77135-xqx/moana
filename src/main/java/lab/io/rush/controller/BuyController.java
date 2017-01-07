package lab.io.rush.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lab.io.rush.model.User;
import lab.io.rush.service.BuyService;

@Controller
@RequestMapping("/buy")
public class BuyController {
	//余票数
	//由于此应用目前就部署于一台机子上，Spring中的类又是单例，所以直接用一个类保存余票数，如果是多台机子就应该用数据库保存或做其他同步处理
	private Integer ticketsLeft = 2;
	//抢购结束的标志，0表示未结束，1表示已经结束但数据没持久化到数据库，-1表示持久化完成
	private int endFlag = 0;
	
	@Autowired
	private BuyService buyService; 
	
	
	
	@RequestMapping("/getTicketsLeft")
	public void getTicketsLeft(HttpServletResponse response){
		try {
			response.getWriter().print(ticketsLeft.intValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//endFlag为抢购结束的标志，0表示未结束，1表示已经结束但数据没持久化到数据库，-1表示持久化完成
	@RequestMapping("/buyTicket")
	public void buyTicket(HttpServletResponse response, HttpSession session){
		User user = (User) session.getAttribute("user");
		//是否购买成功，1为成功，0为失败
		int state = 0;
		if(endFlag==0){
			synchronized (ticketsLeft) {
				if(ticketsLeft>0){
					buyService.addRecordToRedis(user);
					
					
					ticketsLeft=ticketsLeft-1;
					//若最后一次修改后，余票为0，则设置结束标志威1
					if(ticketsLeft==0){
						endFlag=1;
					}
					state = 1;
				}
			}	
		}
		if(endFlag==1){
			buyService.redisToMariadb();
			endFlag=-1;
		}
		if(state==1){
			buyService.sendSuccessMail(user);
		}
		try {
			response.getWriter().print(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
