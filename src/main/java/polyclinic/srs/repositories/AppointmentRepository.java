package polyclinic.srs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polyclinic.srs.entities.Appointment;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
