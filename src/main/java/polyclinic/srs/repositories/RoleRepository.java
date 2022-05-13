package polyclinic.srs.repositories;

import polyclinic.srs.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByRole(String role);

}