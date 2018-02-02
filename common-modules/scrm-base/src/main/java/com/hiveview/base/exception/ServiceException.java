package com.hiveview.base.exception;
/**
 * 业务异常
 * @author leo
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6926247165110964054L;
	
	private String code;
	
	public ServiceException(String msg){
		super(msg);
	}
	
	public ServiceException(String code,String msg){
		super(msg);
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
