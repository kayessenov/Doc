package polyclinic.srs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polyclinic.srs.entities.Departments;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Long> {

    Departments findDepartmentsByDeletedAtNullAndId(Long id);

    List<Departments> findByDeletedAtNull();

}
