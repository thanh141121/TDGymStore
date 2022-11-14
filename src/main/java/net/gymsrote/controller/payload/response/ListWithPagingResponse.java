package net.gymsrote.controller.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListWithPagingResponse<T> extends BaseResponse {
	private int currentPage;

	private int totalPage;

	private List<T> data;

	public ListWithPagingResponse(int currentPage, int totalPage, List<T> data) {
		super(true, "");
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.data = data;
	}
}
