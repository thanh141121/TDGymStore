package net.gymsrote.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequest {

	@NotNull
	private Long productId;

	@NotNull
	private Long rate;

	@NotBlank
	private String description;

}
