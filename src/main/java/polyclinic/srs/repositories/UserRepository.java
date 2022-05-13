package polyclinic.srs.repositories;

import polyclinic.srs.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

}
