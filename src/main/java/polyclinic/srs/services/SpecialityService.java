package polyclinic.srs.services;

import polyclinic.srs.entities.Departments;
import polyclinic.srs.entities.Speciality;

import java.util.List;

public interface SpecialityService {

    Speciality getSpec(Long id);

    Speciality saveSpec(Speciality spec);

    List<Speciality> getAllSpec();

    void deleteSpec(Speciality sec);

}
