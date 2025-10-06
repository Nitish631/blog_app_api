package np.com.nitishrajbanshi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import np.com.nitishrajbanshi.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{
    
}
