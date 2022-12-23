package net.gymsrote.controller.payload.request.order;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductOrderRequest {
	@NotNull
	private Long id;
	@NotNull
	@Range(min = 1)
	private Long quantity;

}
