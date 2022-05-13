package polyclinic.srs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polyclinic.srs.entities.Speciality;
import polyclinic.srs.repositories.SpecialityRepository;
import polyclinic.srs.services.SpecialityService;

import java.util.Date;
import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public Speciality getSpec(Long id) {
        return specialityRepository.findByDeletedAtNullAndId(id);
    }

    @Override
    public Speciality saveSpec(Speciality spec) {
        return specialityRepository.save(spec);
    }

    @Override
    public List<Speciality> getAllSpec() {
        return specialityRepository.findAllByDeletedAtNull();
    }

    @Override
    public void deleteSpec(Speciality sec) {
        sec.setDeletedAt(new Date());
        specialityRepository.save(sec);
    }
}
