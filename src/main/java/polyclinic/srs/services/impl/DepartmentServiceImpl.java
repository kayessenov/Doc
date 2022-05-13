package polyclinic.srs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polyclinic.srs.entities.Departments;
import polyclinic.srs.repositories.DepartmentRepository;
import polyclinic.srs.services.DepartmentService;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void deleteDepartment(Departments dep) {
        dep.setDeletedAt(new Date());
        departmentRepository.save(dep);
    }

    @Override
    public Departments saveDepartment(Departments dep) {
        return departmentRepository.save(dep);
    }

    @Override
    public Departments getDepartment(Long id) {
        return departmentRepository.findDepartmentsByDeletedAtNullAndId(id);
    }

    @Override
    public List<Departments> getAllDepartments() {
        return departmentRepository.findByDeletedAtNull();
    }
}
