package net.gymsrote.entity.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
    
	@OneToOne
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
	}
    
    
    

}
