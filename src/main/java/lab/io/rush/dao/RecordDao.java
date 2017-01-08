package lab.io.rush.dao;

import javax.annotation.Resource;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.stereotype.Component;

import lab.io.rush.model.Record;

@Component("recordDao")
public class RecordDao {
	@Resource(name="persistenceManagerFactory")
	private PersistenceManagerFactory persistenceManagerFactory;
	
	
	//新增购买记录
	public void addRecord(Record record){ 
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			pm.makePersistent(record);
		} finally {
			pm.close();
		}
		
	}
}
