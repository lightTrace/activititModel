package cn.com.wavenet.activiti.util;

import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import cn.com.wavenet.activiti.flow.FlowFrame;
@Service
public class JsonUtils {
	
	public  JSONObject getJson(){
		   String path = JsonUtils.class.getClassLoader().getResource("activiti.json").getFile();
		   String jsonString = FileUtil.readFile(path, true);	
		   System.out.println(jsonString);
		   JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonString);
		   return jsonObject;
	}
}
