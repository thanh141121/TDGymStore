package net.gymsrote.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListWithPagingResponse<T> extends BaseResponse {

	private Data<T> data;

	public ListWithPagingResponse(Data<T> data) {
		super(true, "");
		this.data = data;
	}
}

