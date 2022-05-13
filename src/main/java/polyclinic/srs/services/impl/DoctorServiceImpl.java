package polyclinic.srs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polyclinic.srs.entities.DoctorEntity;
import polyclinic.srs.repositories.DoctorRepository;
import polyclinic.srs.services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorEntity saveDoc(DoctorEntity doc) {
        return doctorRepository.save(doc);
    }

//    @Override
//    public List<DoctorEntity> getAllSpec() {
//        return doctorRepository.findAllByDeletedAtNull();
//    }
}
