package cn.com.wavenet.activiti.util;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
//	private Calendar calNow=Calendar.getInstance();
	public static String FILE_SEPARATOR=System.getProperty("file.separator") ;

	/**
	 * 读取文件
	 * @param filePath
	 * @param flag
	 * @return
	 */
	public static  String readFile(String filePath,boolean flag) {
		// TODO Auto-generated method stub
		File file=new File(filePath);
		return readFile(file,flag);
	}
	public static  String readFile(File file,boolean flag){
		StringBuffer sb=new StringBuffer();
		BufferedReader br=null;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line=null;
			while((line=br.readLine())!=null){
				sb.append(line);
			}
		} catch (Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
		return sb.toString();		
	}
}
