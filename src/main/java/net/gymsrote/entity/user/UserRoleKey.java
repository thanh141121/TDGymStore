package net.gymsrote.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.gymsrote.entity.order.OrderDetailKey;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class UserRoleKey implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5919896146974036504L;

	@Column(name = "user_id")
    Long userId;

    @Column(name = "role_id")
    Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
