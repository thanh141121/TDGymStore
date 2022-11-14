package net.gymsrote.entity.user;

import java.util.Collection;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EUserStatus;
import net.gymsrote.entity.address.Address;
import net.gymsrote.entity.order.OrderDetailKey;
import net.gymsrote.entity.order.Order;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole {
    public UserRole(User users, Role roles) {
		super();
		this.users = users;
		this.roles = roles;
		this.id = new UserRoleKey(users.getId(), roles.getId());
	}

	@EmbeddedId
    private UserRoleKey id;
    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User users;
    
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    @JsonBackReference
    Role roles;

}
