package net.gymsrote.entity.comment;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CommentKey implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	Long userId;

	@Column(name="product_id")
	private Long productId;

}
