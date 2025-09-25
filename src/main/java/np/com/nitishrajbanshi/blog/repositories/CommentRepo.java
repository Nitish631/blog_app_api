package np.com.nitishrajbanshi.blog.repositories;
import np.com.nitishrajbanshi.blog.entities.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import np.com.nitishrajbanshi.blog.entities.Post;
import np.com.nitishrajbanshi.blog.entities.User;

public interface CommentRepo extends JpaRepository<Comment,Integer>{
    Page<Comment> findByUser(User user,Pageable pageable);
    Page<Comment> findByPost(Post post,Pageable pageable);
} 
