package np.com.nitishrajbanshi.blog.services;
import java.util.List;

import np.com.nitishrajbanshi.blog.payloads.UserDto;

public interface UserService {
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUser(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
}
