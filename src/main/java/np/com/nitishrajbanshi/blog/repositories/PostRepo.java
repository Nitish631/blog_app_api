package np.com.nitishrajbanshi.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import np.com.nitishrajbanshi.blog.entities.Category;
import np.com.nitishrajbanshi.blog.entities.Post;
import np.com.nitishrajbanshi.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
