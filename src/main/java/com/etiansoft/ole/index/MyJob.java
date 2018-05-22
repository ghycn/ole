package com.etiansoft.ole.index;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.etiansoft.ole.wk.service.WkLeaveService;

/**
 * 每年1月1号清空请假记录
 */
@Component
public class MyJob {
	private static  final Log log = LogFactory.getLog(MyJob.class);
	@Autowired
	private WkLeaveService wkLeaveService;
	
	public MyJob() {
		System.out.println("MyJob创建成功");
	}

	@Scheduled(cron = "0 0 1 1 1 ? ") // 每年一月1日凌晨1点执行一次
	public void run() {
		log.info("-----------------Hello MyJob  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));
		log.info("-----------------每年一月1日凌晨1点执行一次，清空请假记录");
		wkLeaveService.clearWkLeaveRecord();
	}

}