import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.wavenet.activiti.flow.FlowFrame;
import cn.com.wavenet.service.DataManager;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application.xml"})  
public class JunitTest {
	 @Resource
	 private DataManager dataManagerImpl;

	 @Resource
	 private FlowFrame flowFrame;

    @Test
    public void testFlowFrame(){
//    	flowFrame.deploymentProcess();
    	flowFrame.startTask("102102");
//    	flowFrame.dealTask("102204", "12501");
//    	flowFrame.endTask("102303", "12501");
    }
}