package polyclinic.srs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polyclinic.srs.entities.DoctorEntity;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    List<DoctorEntity> findAllByDeletedAtNull();

//    DoctorEntity findByDeletedAtNullAndId(Long id);

}
