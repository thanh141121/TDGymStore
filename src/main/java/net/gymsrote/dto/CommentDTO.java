package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDTO {
	private String fullnameOfUser;
	private Long userId;
	private Long productId;
	private Long rate;
	private String description;

}
