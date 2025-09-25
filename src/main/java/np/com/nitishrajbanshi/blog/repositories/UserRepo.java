package np.com.nitishrajbanshi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import np.com.nitishrajbanshi.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

}
