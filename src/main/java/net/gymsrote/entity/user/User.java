package net.gymsrote.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.cart.CartDetail;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserInfo{

	public User(String email, String username, String password, String fullname, String phone,
			UserRole role) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.phone = phone;
		this.role = role;
	}

	public User(String email, String username, String password, String fullname, String phone) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.isEnabled = false;
	}
	
//    public User(String fullname, String username, String email, String password) {
//        this.fullname = fullname;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "phone", unique = true)
	private String phone;
	
	@Column
	private Boolean isEnabled;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	
	@OneToOne
	@JoinColumn(name = "default_address")
	private UserAddress defaultAddress;
	
	@OneToMany(mappedBy = "user")//, fetch = FetchType.LAZY)
	private List<UserAddress> userAddress;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cloud_resource_id", referencedColumnName = "id")
	private MediaResource avatar;


	@ManyToOne
	private UserRole role;
	

	@OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
	private List<CartDetail> cart;

}
