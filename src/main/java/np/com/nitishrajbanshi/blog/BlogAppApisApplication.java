package np.com.nitishrajbanshi.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import np.com.nitishrajbanshi.blog.config.AppConstants;
import np.com.nitishrajbanshi.blog.entities.*;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import np.com.nitishrajbanshi.blog.payloads.CategoryDto;
import np.com.nitishrajbanshi.blog.payloads.CommentDto;
import np.com.nitishrajbanshi.blog.payloads.PostDto;
import np.com.nitishrajbanshi.blog.payloads.UserDto;
import np.com.nitishrajbanshi.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true);
		mapper.typeMap(UserDto.class, User.class).addMappings(m -> m.skip(User::setId));
		mapper.typeMap(CategoryDto.class, Category.class).addMappings(m -> m.skip(Category::setCategoryId));
		mapper.typeMap(PostDto.class, Post.class).addMappings(m -> m.skip(Post::setPostId));
		mapper.typeMap(CommentDto.class, Comment.class).addMappings(m -> m.skip(Comment::setCommentId));
		return mapper;
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			Role roleAdmin = new Role();
			roleAdmin.setRoleId(AppConstants.ADMIN_ROLE_ID);
			roleAdmin.setRoleName("ADMIN");
			Role roleUser = new Role();
			roleUser.setRoleId(AppConstants.USER_ROLE_ID);
			roleUser.setRoleName("USER");
			List<Role> roles = List.of(roleAdmin, roleUser);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r -> {
				System.out.println(r.getRoleName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
