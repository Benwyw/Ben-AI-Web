package com.benwyw.payload;

import com.benwyw.constant.MessageCodeConstants;
import com.benwyw.constant.MessageConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private Boolean success = true;
	private String message = MessageConstants.MESSAGE_SUCCESS;
	private String code = MessageCodeConstants.CODE_SUCCESS;
	private T data;
	
	public ApiResponse(T data) {
		this.setData(data);
	}
	
	public ApiResponse(Boolean success, String message, String code) {
		super();
		this.setSuccess(success);
		this.setMessage(message);
		this.setCode(code);
	}
}
