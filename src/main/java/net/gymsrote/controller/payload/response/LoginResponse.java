package net.gymsrote.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse<T> extends BaseResponse {
	private String token;
	private final String type = "Bearer";
	private T userInfo;
	
	public LoginResponse(String token, T userInfo) {
		super(true, "");
		this.token = token;
		this.userInfo = userInfo;
	}
}