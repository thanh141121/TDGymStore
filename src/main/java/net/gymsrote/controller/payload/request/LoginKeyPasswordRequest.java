package net.gymsrote.controller.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
@Getter @Setter
@NoArgsConstructor
public class LoginKeyPasswordRequest {
	@NotEmpty
	private String loginKey;
	@NotEmpty
	private String password;
}
