package com.codewithaman.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	
	private long id;
	
	@NotEmpty
	@Size(min = 4,max = 50,message="Username must be min of 4 characters")
	private String name;
	@Email(message = "Email address is not valid!!")
	private String email;
	@NotEmpty
	@Size(min = 6, max =24,message ="Password must be min of 6 chars and max of 24 chars")
//	@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8, 20}$")
	private String password;
	@NotEmpty
	private String about;
	
	
}
