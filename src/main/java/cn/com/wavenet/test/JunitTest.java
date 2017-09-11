package cn.com.wavenet.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.com.wavenet.service.impl.DataManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:application.xml"})  
public class JunitTest {
	 @Resource
	 private  DataManagerImpl dataManagerImpl; 

}
