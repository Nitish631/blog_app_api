package np.com.nitishrajbanshi.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import np.com.nitishrajbanshi.blog.entities.User;
import np.com.nitishrajbanshi.blog.exception.ResourceNotFoundException;
import np.com.nitishrajbanshi.blog.payloads.UserDto;
import np.com.nitishrajbanshi.blog.repositories.UserRepo;
import np.com.nitishrajbanshi.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser= this.userRepo.save(user);
		return this.userToDto(savedUser);
	}
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		// user.setAbout(userDto.getAbout());
		// user.setEmail(userDto.getEmail());
		// user.setName(userDto.getName());
		// user.setPassword(userDto.getPassword());
		this.modelMapper.map(userDto, user);
		User updatedUser=this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users= this.userRepo.findAll();
		List<UserDto> userDtos=users.stream().map((user)->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		this.userRepo.delete(user);
	}
	private User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		return user;
		// User user=new User();
		// user.setName(userDto.getName());
		// user.setEmail(userDto.getEmail());
		// user.setPassword(userDto.getPassword());
		// user.setAbout(userDto.getAbout());
		// return user;
	}
	private UserDto userToDto(User user) {
		// UserDto userDto=new UserDto();
		// userDto.setAbout(user.getAbout());
		// userDto.setEmail(user.getEmail());
		// userDto.setId(user.getId());
		// userDto.setName(user.getName());
		// userDto.setPassword(user.getPassword());
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
		return userDto;
	}

}
