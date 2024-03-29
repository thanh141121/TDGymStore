package net.gymsrote.controller.payload.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BaseResponse {
	@NonNull
	private Boolean success;

	@NonNull
	private String message;

	public BaseResponse() {
		this.success = true;
		this.message = "";
	}

	public BaseResponse(Boolean success) {
		this.success = success;
		this.message = "";
	}
}
