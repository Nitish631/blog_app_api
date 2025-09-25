package np.com.nitishrajbanshi.blog.payloads;

import jakarta.validation.constraints.*;

import lombok.*;
import np.com.nitishrajbanshi.blog.Validator.ValidEmail;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty(message = "Name field mustnot be empty")
	@Size(min = 3,message = "Username must be minimum of 3 characters")
	private String name;
	@ValidEmail
	private String email;
	private String about;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*?]).*$",message = "The password must contain one uppercase ,one lowercase,one number,and one special character" )
	@Size(min=6,max = 20,message = "Password must be between 6 characters to 20 characters")
	private String password;

}
