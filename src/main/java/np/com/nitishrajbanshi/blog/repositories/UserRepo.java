package np.com.nitishrajbanshi.blog.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import np.com.nitishrajbanshi.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
    public Optional<User> findByEmail(String email);
}
