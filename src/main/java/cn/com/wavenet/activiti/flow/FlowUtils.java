package cn.com.wavenet.activiti.flow;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.com.wavenet.bean.SelectSqlBean;
import cn.com.wavenet.service.DataManager;

/**
 * @author liwd
 * @date 2017/9/6
 */
@Service
public class FlowUtils {
	
	@Resource
	private DataManager dataManagerImpl;
	
	//部署activiti
	public void deploymentProcessDefinition(String name , String bpmnPath , String pngPath){
	    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		Deployment deployment = processEngine.getRepositoryService() //与流程定义和部署对象有关的service
				.createDeployment()		//创建一个部署对象
				.name(name)//添加部署对象的名称
				.addClasspathResource(bpmnPath)	//从classpath的资源中加载，每次只能加载一个文件
				.addClasspathResource(pngPath)//从classpath的资源中加载，每次只能加载一个文件
				.deploy();
	}
	//启动流程
	public String startFlow(String processDefinitionKey){
		  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	  	  ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
	      String process_id = processInstance.getProcessInstanceId();
	      return process_id;

	}
	//查询当前流程id指定角色是否有任务，并完成当前任务
	public void finishWork(String processId,String currentRole,String nextRole,String nextNodeId,Map<String,Object> conditionMap){
   	 ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
     TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processId).taskCandidateGroup(currentRole).singleResult();
        if(task!=null){
	    		 String [] candidateUsers={nextRole};
	    		 taskService.setVariable(task.getId(), nextNodeId,  Arrays.asList(candidateUsers));
				 taskService.complete(task.getId(),conditionMap);					           
        }
	}
	//查询当前流程id指定角色是否有任务，并结案
	public void endFlow(String processId,String currentRole , Map<String,Object> conditionMap){
   	 ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
     TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processId).taskCandidateGroup(currentRole).singleResult();
        if(task!=null){   		
				 taskService.complete(task.getId(),conditionMap);					           
        }
	}
	//查找当前流程实例下的流程节点group_id
	public String selectGroupId(String processId){
		String group_id_ = "";
		SelectSqlBean bean = new SelectSqlBean();
		bean.setSql("SELECT group_id_ FROM ACT_RU_TASK  t JOIN ACT_RU_IDENTITYLINK i ON t.id_ = i.task_id_ WHERE t.proc_inst_id_="+processId);
		bean.setDataSource("jdbc154");
        try {
        	group_id_ = dataManagerImpl.findBySqlToList(bean).get(0).get("GROUP_ID_").toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        return group_id_;
	}
}
