package cn.com.wavenet.activiti.Exception;

public class NoConditionException extends RuntimeException {
   
	public NoConditionException(String message){
		super(message);
	}
	public NoConditionException(String message , Throwable cause){
		super(message , cause);
	}
}
