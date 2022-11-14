package net.gymsrote.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRoleKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5919896146974036504L;

	@Column(name = "user_id")
	Long userId;

	@Column(name = "role_id")
	Long roleId;
}
