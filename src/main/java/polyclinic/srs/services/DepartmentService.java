package polyclinic.srs.services;

import polyclinic.srs.entities.Departments;

import java.util.Date;
import java.util.List;

public interface DepartmentService {

    void deleteDepartment(Departments dep);

    Departments saveDepartment(Departments dep);

    Departments getDepartment(Long id);

    List<Departments> getAllDepartments();

}
