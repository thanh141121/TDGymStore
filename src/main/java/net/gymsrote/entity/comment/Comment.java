package net.gymsrote.entity.comment;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.user.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

	
    @EmbeddedId
    private CommentKey id;
    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    
    @Column
    private Long rate;
    
    @Column
    private String description;

	public Comment(User user, Product product, Long rate, String description) {
		super();
		this.user = user;
		this.product = product;
		this.rate = rate;
		this.description = description;
		
		this.id = new CommentKey(user.getId(), product.getId());
	}
    
    
    

}
