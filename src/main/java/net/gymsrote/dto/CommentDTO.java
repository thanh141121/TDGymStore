package net.gymsrote.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDTO {
	private Long id;
	private String fullnameOfUser;
	private Long userId;
	private Long productVariationId;
	private String productVariationName;
	private Long rate;
	private String description;
	private Date createdDate;

}
