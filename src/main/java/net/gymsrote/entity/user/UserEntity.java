package net.gymsrote.entity.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EUserStatus;
import net.gymsrote.entity.address.AddressEntity;
import net.gymsrote.entity.cart.CartDetailEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

	public UserEntity(String email, String username, String password, String fullname, String phone,
			Collection<UserRoleEntity> userRoles) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.phone = phone;
		this.userRoles = userRoles;
	}

	public UserEntity(String email, String username, String password, String fullname, String phone,
			EUserStatus status) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.status = status;
	}
	
    public UserEntity(String fullname, String username, String email, String password) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
    }


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

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private EUserStatus status;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cloud_resource_id", referencedColumnName = "id")
	private MediaResource avatar;


    @OneToMany(mappedBy = "users")
    @JsonManagedReference
    private Collection<UserRoleEntity> userRoles = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	private List<AddressEntity> addresses = new ArrayList<AddressEntity>();
	

	@OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
	private List<CartDetailEntity> cart;
	

}
