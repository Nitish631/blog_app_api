package np.com.nitishrajbanshi.blog;

import org.springframework.boot.SpringApplication;

import np.com.nitishrajbanshi.blog.entities.*;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import np.com.nitishrajbanshi.blog.entities.User;
import np.com.nitishrajbanshi.blog.payloads.CategoryDto;
import np.com.nitishrajbanshi.blog.payloads.CommentDto;
import np.com.nitishrajbanshi.blog.payloads.PostDto;
import np.com.nitishrajbanshi.blog.payloads.UserDto;

@SpringBootApplication
public class BlogAppApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper=new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true);
		mapper.typeMap(UserDto.class, User.class).addMappings(m->m.skip(User::setId));
		mapper.typeMap(CategoryDto.class, Category.class).addMappings(m->m.skip(Category::setCategoryId));
		mapper.typeMap(PostDto.class, Post.class).addMappings(m->m.skip(Post::setPostId));
		mapper.typeMap(CommentDto.class, Comment.class).addMappings(m->m.skip(Comment::setCommentId));
		return mapper;
	}
}
