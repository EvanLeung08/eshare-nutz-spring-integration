package com.evanshare.main;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.Ioc2;
import org.nutz.ioc.ObjectProxy;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evanshare.bean.CustomGroupEntityManager;
import com.evanshare.bean.CustomGroupEntityManagerFactory;
import com.evanshare.bean.CustomUserEntityManager;
import com.evanshare.bean.CustomUserEntityManagerFactory;
import com.evanshare.bean.User;

public class MainSetup implements Setup{
	Logger log = LoggerFactory.getLogger(MainSetup.class);
	/* (non-Javadoc)
	 * @see org.nutz.mvc.Setup#init(org.nutz.mvc.NutConfig)
	 */
	@Override
	public void init(NutConfig conf) {
		Ioc ioc = conf.getIoc();
		Dao dao = ioc.get(Dao.class);
		Daos.createTablesInPackage(dao, "com.evanshare", false);
		//初始化默认用户
		if(dao.count(User.class)==0){
			User user = new User();
			user.setName("admin");
			user.setPassword("123456");
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			dao.insert(user);
		}
		
		activitiInit(conf);
		
//		 ioc.get(NutQuartzCronJobFactory.class);
		
	}

	@Override
	public void destroy(NutConfig conf) {
		// TODO Auto-generated method stub
		
	}
	
	private void activitiInit(NutConfig config) {
        log.info("Activiti Init Start...");
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration();

        processEngineConfiguration.setDataSource(config.getIoc().get(DataSource.class));
        processEngineConfiguration.setDatabaseSchemaUpdate("false");
        processEngineConfiguration.setJobExecutorActivate(false);
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setXmlEncoding("utf-8");
        
        List<SessionFactory> list=new ArrayList<SessionFactory>();
		CustomGroupEntityManagerFactory customGroupManagerFactory=new CustomGroupEntityManagerFactory();
		customGroupManagerFactory.setGroupEntityManager(new CustomGroupEntityManager());
		CustomUserEntityManagerFactory customUserEntityManagerFactory=new CustomUserEntityManagerFactory();
		customUserEntityManagerFactory.setUserEntityManager(new CustomUserEntityManager());
		list.add(customGroupManagerFactory);
		list.add(customUserEntityManagerFactory);
		processEngineConfiguration.setCustomSessionFactories(list);
        
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        ((Ioc2) config.getIoc()).getIocContext().save("app", "processEngine", new ObjectProxy(processEngine));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "repositoryService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getRepositoryService()));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "runtimeService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getRuntimeService()));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "taskService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getTaskService()));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "formService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getFormService()));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "historyService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getHistoryService()));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "managementService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getManagementService()));
        ((Ioc2) config.getIoc()).getIocContext().save("app", "identityService", new ObjectProxy(processEngine.getProcessEngineConfiguration().getIdentityService()));
        
       
        log.info("Activiti Init End.");

    }
	
	

}
