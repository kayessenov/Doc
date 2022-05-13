package polyclinic.srs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import polyclinic.srs.entities.Blogs;
import polyclinic.srs.entities.Comments;
import polyclinic.srs.entities.Departments;
import polyclinic.srs.entities.Users;
import polyclinic.srs.repositories.BlogRepository;
import polyclinic.srs.repositories.CommentsRepository;
import polyclinic.srs.repositories.DepartmentRepository;
import polyclinic.srs.repositories.UserRepository;
import polyclinic.srs.services.DepartmentService;

import java.util.*;

@Controller
@RequestMapping("/element")
public class ElementController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;


    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping("/department/{depId}")
    public String ElementDepartmentsPage(Model model,
                                         @PathVariable(name = "depId") Long id)
    {
        model.addAttribute("currentUser", getUser());
        Departments dep = departmentService.getDepartment(id);
        model.addAttribute("dep", dep);
        return "/elements/departments";
    }


}
