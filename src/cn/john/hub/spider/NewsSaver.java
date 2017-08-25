/**  
 * @Title: NewsSaver.java

 * @Package: cn.john.hub.spider

 * @Description: TODO

 * @author: John  

 * @date: 2017年7月28日 上午11:09:06

 * @version: V1.0  

 */
package cn.john.hub.spider;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.john.hub.domain.Heartbeat;
import cn.john.hub.domain.NewsDO;
import cn.john.hub.service.NewsService;

/**
 * 
 * @ClassName: NewsSaver
 * 
 * @Description: TODO
 * 
 * @author: John
 * 
 * @date: 2017年7月28日 上午11:09:06
 * 
 * 
 */
@Component
public class NewsSaver implements Runnable {
	private static Logger log = LogManager.getLogger("logger");
	@Autowired
	private NewsService nService;
	@Autowired
	private Heartbeat hb;
	/*
	 * (non Javadoc)
	 * 
	 * @Title: run
	 * 
	 * @Description: 60秒执行一次
	 * 
	 * 
	 * @see java.lang.Runnable#run()
	 * 
	 */
	@Override
	public void run() {
		
		long timestamp = System.currentTimeMillis();
		hb.setNewsSaverBeat(timestamp);
		if (Queue.newsQueue.size() > 0) {
			List<NewsDO> newsList = null;
			try {
				newsList = Queue.newsQueue.take();
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
			nService.saveNews(newsList);
			log.info(newsList.size() + " news saved!And newsQueue size is " + Queue.newsQueue.size());
		}
	}

}
