activiti 后台可配置开发
====
基于公司的业务总是会画流程图去做巡查上报的业务，我就把这个业务封装成可配置的流程业务便于以后的快速开发，针对的是后台程序员的定制化开发，而不是针对客户的自定义定制，那样虽然更加友好，但是开发难度和成本更大，我这种方式是针对不变的流程图的快速开发。
项目使用mybaits+activiti

首先定义一个流程图，复杂程度一般,在cn.com.wavenet.diagrams包下

对应的activitit.json
{<br>  
   "deployProcess":{<br>
   "name":"activiti流程示例",<br>
   "bpmnPath":"cn/com/wavenet/activiti/diagrams/activitiExample.bpmn",<br>
   "pngPath":"cn/com/wavenet/activiti/diagrams/activitiExample.png"<br>
   },<br>
   "processDefinitionKey":"myProcess",<br>
   "startTask":{<br>
   "currentRole":"roleStart",<br>
   "startNextArr":["102101,处理角色1,roleDeal1","102102,处理角色2,roleDeal2"]<br>
   },<br>
   "dealTask":{<br>
   "dealNextArr":["102201,处理角色3,roleDeal3","102202,处理角色4,roleDeal4","102203,结案角色,roleEnd","102204,结案角色,roleEnd"]
   },<br>
   "endTask":{<br>
   "endNextArr":["102301,处理角色5,roleDeal5","102302,处理角色6,roleDeal6","102303"]<br>
   }<br>    
}<br>

为了区分阶段，我将整个流程分为startTask、dealTask、endTask，大家可以根据需求自行调整，主要代码是FlowUtils这个类，而FlowFrame原理就是从json文件中读取预定义的json数据然后调用FlowUtils操作流程，部署就不说了，直接看finishWork()这个方法：
		```Java
		Task task = taskService.createTaskQuery().processInstanceId(processId).taskCandidateGroup(currentRole).singleResult();//首先查询当前流程实例下该角色是否有任务，有任务肯定也是唯一的<br>
		         if(task!=null){
	    		 String [] candidateUsers={nextRole};//流程图中可以看出开始任务后有两个选择，根据前端传入的条件我们可以从activitit.json中找到下一节点的处理角色nextRole以及节点ID nextNodeId<br>
	    		 taskService.setVariable(task.getId(), nextNodeId,  Arrays.asList(candidateUsers));//然后通过以上两行代码设置进去，roleDeal1可以包括处理角色1,你还可以将处理角色1a放进roleDeal1，这样是为了方便扩展角色
				 taskService.complete(task.getId(),conditionMap);//最后完成任务				           
        }```
这个方法无论哪个节点完成任务都可以使用，那么在下一节点开始任务的时候怎么做呢？
我们再看FlowFrame中的dealTask方法：
       ```Java
       public void dealTask(String condition , String processId){
    	   String group_id_ = flowUtils.selectGroupId(processId);//首先通过selectGroupId方法调用mybaits去查询这样一个语句：SELECT group_id_ FROM ACT_RU_TASK t JOIN ACT_RU_IDENTITYLINK i ON t.id_ = i.task_id_ WHERE 
           t.proc_inst_id_=#{nm_processId} ,就是上一节点处理完之后设定的下一处理角色可以通过这个方法找到，然后下面就是重复finishWork()的方法
    	   //处理任务
    	   String currentRole = group_id_;
		   JSONObject jsonObject = jsonUtils.getJson();
    	   JSONArray dealArr = jsonObject.getJSONObject("dealTask").getJSONArray("dealNextArr");
    	   String nextRole = "";
    	   String nextNodeId = "";
    	   for (Iterator iterator = dealArr.iterator();iterator.hasNext();) {
			 String preCondition = (String) iterator.next();
			 String[] preConditionArr = preCondition.split(",");
             if(preConditionArr[0].equals(condition)){
            	 nextRole = preConditionArr[1];
            	 nextNodeId = preConditionArr[2];
             }
		}
    	   if(nextRole.equals("")||nextNodeId.equals("")){
    		   throw new NoConditionException("没有对应的节点出口");
    	   }
    	   Map<String,Object> conditionMap = new HashMap<String,Object>();
    	   conditionMap.put("state", condition);
    	   flowUtils.finishWork(processId, currentRole, nextRole, nextNodeId,conditionMap);
       }```	
至于最后的endTask就更简单了，原理大同小异，主要看flow包下的两个类，activiti.json和流程图的对应关系就可以了，其余的都是mybaits对数据库的操作，虽然我只设置了一个数据源，但是项目里面可以设置多数据源，有兴趣可以在网上看下配置下就可以了，而在项目里面只要在jdbc.properties和activiti.cfg.xml配置154数据源的相关信息就可以了，然后直接运行JunitTest的testFlowFrame方法。
    ```Java
    @Test
    public void testFlowFrame(){
    	flowFrame.deploymentProcess();
//    	flowFrame.startTask("102102");
//    	flowFrame.dealTask("102204", "12501");
//    	flowFrame.endTask("102303", "12501");
    }```
从上到下就是一个完整的部署、开始、处理、结束的流程
