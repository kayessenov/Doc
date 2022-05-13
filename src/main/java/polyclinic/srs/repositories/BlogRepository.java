package polyclinic.srs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import polyclinic.srs.entities.Blogs;

public interface BlogRepository extends JpaRepository<Blogs, Long> {
}
