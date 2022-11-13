package net.gymsrote.entity.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.EnumEntity.EUserStatus;
import net.gymsrote.entity.address.AddressEntity;
import net.gymsrote.entity.cart.CartEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {
	public RoleEntity(EUserRole name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	@Enumerated(EnumType.STRING)
	private EUserRole name;

//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles")
//	private Set<UserEntity> users = new HashSet<>();
	
    @OneToMany(mappedBy = "roles")
    @JsonManagedReference
    private Collection<UserRoleEntity> userRoles = new HashSet<>();
//	@OneToMany(mappedBy = "role") 
//	private List<UserEntity> user = new ArrayList<>();
}
