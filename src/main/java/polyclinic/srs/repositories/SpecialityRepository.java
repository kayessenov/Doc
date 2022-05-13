package polyclinic.srs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polyclinic.srs.entities.DoctorEntity;
import polyclinic.srs.entities.Speciality;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    List<Speciality> findAllByDeletedAtNull();

    Speciality findByDeletedAtNullAndId(Long id);


}
