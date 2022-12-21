package net.gymsrote.controller.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Data<T>{

	private int currentPage;
	private int totalPage;
	private List<T> data;
}