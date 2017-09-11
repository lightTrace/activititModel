package cn.com.wavenet.activiti.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.wavenet.activiti.Exception.NoConditionException;
import cn.com.wavenet.activiti.util.JsonUtils;

@Service
public class FlowFrame {
	
    @Resource
	private FlowUtils flowUtils;
    @Resource
    private JsonUtils jsonUtils;
	   //部署流程
	   public void deploymentProcess(){
		   
		   JSONObject jsonObject = jsonUtils.getJson();
		   String name = jsonObject.getJSONObject("deployProcess").getString("name");
		   String bpmnPath = jsonObject.getJSONObject("deployProcess").getString("bpmnPath");
		   String pngPath = jsonObject.getJSONObject("deployProcess").getString("pngPath");
		   System.out.println(name+bpmnPath+pngPath);
		   flowUtils.deploymentProcessDefinition(name,bpmnPath,pngPath);
	   }
       public void startTask(String condition){
    	   //启动流程
		   JSONObject jsonObject = jsonUtils.getJson();
           String processDefinitionKey = jsonObject.getString("processDefinitionKey");
    	   String processId = flowUtils.startFlow(processDefinitionKey);
    	   //开始任务
    	   String currentRole = jsonObject.getJSONObject("startTask").getString("currentRole");
    	   JSONArray startArr = jsonObject.getJSONObject("startTask").getJSONArray("startNextArr");
    	   String nextRole = "";
    	   String nextNodeId = "";
    	   for (Iterator iterator = startArr.iterator();iterator.hasNext();) {
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
       }
       public void dealTask(String condition , String processId){
    	   String group_id_ = flowUtils.selectGroupId(processId);
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
       }
       
       public void endTask(String condition , String processId){
    	   String group_id_ = flowUtils.selectGroupId(processId);
    	   //处理任务
    	   String currentRole = group_id_;
		   JSONObject jsonObject = jsonUtils.getJson();
    	   JSONArray dealArr = jsonObject.getJSONObject("endTask").getJSONArray("endNextArr");
    	   String nextRole = "";
    	   String nextNodeId = "";
    	   
    	   Map<String,Object> conditionMap = new HashMap<String,Object>();
    	   conditionMap.put("state", condition);
    	   for (Iterator iterator = dealArr.iterator();iterator.hasNext();) {
			 String preCondition = (String) iterator.next();
			 String[] preConditionArr = preCondition.split(",");
             if(preConditionArr[0].equals(condition)&&preConditionArr.length>1){
            	 nextRole = preConditionArr[1];
            	 nextNodeId = preConditionArr[2];
            	 flowUtils.finishWork(processId, currentRole, nextRole, nextNodeId,conditionMap);
             }else if(preConditionArr[0].equals(condition)&&preConditionArr.length<2){
            	 flowUtils.endFlow(processId, currentRole, conditionMap);
             }
		}
       }
}
