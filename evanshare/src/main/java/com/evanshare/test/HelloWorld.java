package com.evanshare.test;



import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;


public class HelloWorld {
	// 使用框架的自动建表功能（不提供配置文件）
	@Test
	public void testCreateTablesAutomaticallyWithoutConfigFiles() {
		// 创建流程引擎配置对象
		ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		// 设置jdbc连接参数信息
		config.setJdbcDriver("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql:///eam-mirror");
		config.setJdbcUsername("root");
		config.setJdbcPassword("root");
		// 设置自动建表
		config.setDatabaseSchemaUpdate("true");
		// 使用配置对象创建流程引擎对象，创建对象过程中会自动建表
		ProcessEngine processEngine = config.buildProcessEngine();
	}

	// 使用框架的自动建表功能（提供配置文件）
	@Test
	public void testCreateTablesAutomaticallyWithConfigFiles() {
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti-context.xml", "processEngineConfiguration");
		ProcessEngine pe = config.buildProcessEngine();
	}

	// 使用框架的自动建表功能（提供配置文件---使用默认配置）
	@Test
	public void testCreateTablesAutomatically() {
		ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	}

	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();

	// 将设计的流程图部署到数据库中
	@Test
	public void testDeploy() {
		// 创建部署构建器对象，用于加载流程定义文件(UserInfoAudit.bpmn,UserInfoAudit.myProcess.png)，部署流程定义
		DeploymentBuilder deploymentBuilder = pe.getRepositoryService().createDeployment();
		deploymentBuilder.addClasspathResource("UserInfoAudit.bpmn");
		Deployment deployment = deploymentBuilder.deploy();
		System.out.println(deployment.getId());
	}

	// 查询流程定义
	@Test
	public void testQueryProcessDefinition() {
		// 流程定义查询对象，用于查询流程定义表----act_re_procdef
		ProcessDefinitionQuery query = pe.getRepositoryService().createProcessDefinitionQuery();
		// 添加过滤条件,取最新版本
		query.latestVersion();
		// query.processDefinitionId(processDefinitionId)
		// query.processDefinitionKey(processDefinitionKey);
		// 添加排序条件
		query.orderByProcessDefinitionVersion().desc();

		// 添加分页条件
		// query.listPage(firstResult, maxResults);

		// 查询所有流程
		List<ProcessDefinition> processDefinitionList = query.list();
		for (ProcessDefinition pd : processDefinitionList) {
			System.out.println(pd.getId());
		}

	}
	// 根据流程定义启动流程实例----操作的数据表：act_ru_execution act_ru_task
	@Test
	public void testStartProcess(){
		String processDefinitionId = "UserAuditProcess:2:504";
		//根据流程实例ID去启动流程
		ProcessInstance pInstance = pe.getRuntimeService().startProcessInstanceById(processDefinitionId);
		System.out.println(pInstance.getId());
	}
	
	//查询任务列表
	@Test
	public void testQueryTaskList(){
		//创建任务查询对象，查询表act_ru_task
		TaskQuery query = pe.getTaskService().createTaskQuery();
		String assignee ="张三";
		//添加过滤条件
		query.taskAssignee(assignee);
		//排序
		query.orderByTaskCreateTime().desc();
		List<Task> taskList = query.list();
		for(Task task : taskList){
			System.out.println("taskId:"+task.getId()+",taskName:"+task.getName());
		}
	}
	//办理任务
	//办理个人任务，操作的表是act_ru_execution,act_ru_task
	@Test
	public void testExecuteTask(){
		String taskId= "604";
		pe.getTaskService().complete(taskId);
	}
	
}
