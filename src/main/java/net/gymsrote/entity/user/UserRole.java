package net.gymsrote.entity.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EUserRole;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class UserRole {
	public UserRole(EUserRole name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	@Enumerated(EnumType.STRING)
	private EUserRole name;

//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//	private Set<User> users = new HashSet<>();
	
}
