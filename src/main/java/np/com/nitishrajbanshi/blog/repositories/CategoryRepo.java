package np.com.nitishrajbanshi.blog.repositories;

import np.com.nitishrajbanshi.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
