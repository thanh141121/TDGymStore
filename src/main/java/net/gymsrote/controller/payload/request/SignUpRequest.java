package net.gymsrote.controller.payload.request;

import javax.persistence.Column;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String fullname;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    
    @NotNull
    @Size(max=10)
	private String phone;
}
