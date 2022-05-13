package polyclinic.srs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polyclinic.srs.entities.Comments;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findAllByBlogs_Id(Long id);

}
