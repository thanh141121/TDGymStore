package net.gymsrote.controller.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListResponse<T> extends BaseResponse {
	private List<T> data;

	public ListResponse(List<T> data) {
		super(true, "");
		this.data = data;
	}
}
