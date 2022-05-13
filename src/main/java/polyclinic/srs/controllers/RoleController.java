package polyclinic.srs.controllers;


import groovy.lang.Lazy;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import polyclinic.srs.entities.*;
import polyclinic.srs.repositories.BlogRepository;
import polyclinic.srs.repositories.DepartmentRepository;
import polyclinic.srs.repositories.DoctorRepository;
import polyclinic.srs.repositories.SpecialityRepository;
import polyclinic.srs.services.DepartmentService;
import polyclinic.srs.services.DoctorService;
import polyclinic.srs.services.SpecialityService;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
@RequestMapping("/admin")
public class RoleController {

    @Value("${baseURL}")
    private String baseURL;

    @Value("${baseDocUrl}")
    private String baseDocUrl;

    @Value("${baseBlogUrl}")
    private String baseBlogUrl;

    @Lazy
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private BlogRepository blogRepository;
    @Lazy
    @Autowired
    private DepartmentService departmentService;

    @Lazy
    @Autowired
    private DoctorRepository doctorRepository;
    @Lazy
    @Autowired
    private SpecialityService specialityService;

    @Lazy
    @Autowired
    private SpecialityRepository specialityRepository;
    @Lazy
    @Autowired
    private DoctorService doctorService;

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping()
    public String AdminPage(Model model){
        model.addAttribute("currentUser", getUser());
        return "/admin/admin";
    }

    @GetMapping("/addSpeciality")
    public String addSpecialityPage(Model model){
        model.addAttribute("currentUser", getUser());
        return "/admin/addSpeciality";
    }

    @GetMapping("/viewSpeciality")
    public String viewSpecialityPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Speciality> specialities = specialityService.getAllSpec();
        model.addAttribute("spec", specialities);
        return "/admin/viewSpeciality";
    }

    @PostMapping("/addDoctorForm")
    public String addDoctorFormPage(Model model,
                                    @RequestParam(name = "first_name")String name,
                                    @RequestParam(name = "last_name")String surname,
                                    @RequestParam(name = "spec_id")Long id,
                                    @RequestParam(name = "doc_img")MultipartFile file){
        model.addAttribute("currentUser", getUser());
        DoctorEntity doc = new DoctorEntity();
        doc.setName(name);
        doc.setSurname(surname);
        Speciality spec = specialityService.getSpec(id);
        doc.setSpeciality(spec);
        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(name+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(baseDocUrl+fileName+".jpg");

                Files.write(path, bytes);

                doc.setImage(fileName);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        doctorService.saveDoc(doc);
        return "redirect:/admin/addDoctor";
    }

    @PostMapping("/removedocForm")
    public String removeDocForm(Model model,
                                @RequestParam(name = "id_doc")Long id){
        DoctorEntity doc = doctorRepository.getById(id);
        doctorRepository.delete(doc);
        return "redirect:/admin/viewDoctor";
    }

    @PostMapping("/addSpec")
    public String addSpec(@RequestParam(name = "name")String name,
                          @RequestParam(name = "code_name")String code_name,Model model){
        model.addAttribute("currentUser", getUser());
        Speciality spec = new Speciality();
        spec.setName(name);
        spec.setShort_code(code_name);
        specialityService.saveSpec(spec);
        return "redirect:/admin/addSpeciality";
    }

    @GetMapping("/addDepartments")
    public String departmentsAddPage(Model model){
        model.addAttribute("currentUser", getUser());
        return "/admin/addDepartments";
    }
    @PostMapping("/formdepartment")
    public String formdepartment(Model model,
                                 @RequestParam(name = "name")String name,
                                 @RequestParam(name = "description")String description,
                                 @RequestParam(name = "department_img")MultipartFile file,
                                 @RequestParam(name = "short_desc")String short_desc){
        model.addAttribute("currentUser", getUser());
        Departments dep = new Departments();
        dep.setName(name);
        dep.setShortDesc(short_desc);
        dep.setDescription(description);
        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(name+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(baseURL+fileName+".jpg");

                Files.write(path, bytes);

                dep.setImage(fileName);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        departmentService.saveDepartment(dep);
        return "redirect:/admin/addDepartments";
    }


    @GetMapping("/viewDepartments")
    public String departmentsViewPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Departments> deps = departmentService.getAllDepartments();
        model.addAttribute("deps", deps);
        return "/admin/viewDepartments";
    }
    @GetMapping("/viewDepartments/departments/{depId}")
    public String depDetailsPage(Model model,
                          @PathVariable(name = "depId") Long id) {
        model.addAttribute("currentUser", getUser());
        Departments dep = departmentService.getDepartment(id);
        model.addAttribute("dep", dep);
        return "details/depdetails";
    }
    @PostMapping("/savedepdetails")
    public String SaveDepDetailsForm(Model model,
                                     @RequestParam(name = "id")Long id,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "shortDesc")String shortDesc,
                                     @RequestParam(name = "description")String desc,
                                     @RequestParam(name = "department_img")MultipartFile file){
        model.addAttribute("currentUser", getUser());
        Departments dep = departmentService.getDepartment(id);
        dep.setDescription(desc);
        dep.setName(name);
        dep.setShortDesc(shortDesc);
        dep.setName(name);
        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(name+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(baseURL+fileName+".jpg");

                Files.write(path, bytes);

                dep.setImage(fileName);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        departmentService.saveDepartment(dep);
        return "redirect:/admin/viewDepartments/departments/"+id;
    }
    @PostMapping("/removedepartment")
    public String deleteDepartment(@RequestParam(name = "id")Long id,
                                   Model model){
        model.addAttribute("currentUser", getUser());
        Departments dep = departmentService.getDepartment(id);
        departmentService.deleteDepartment(dep);
        return "redirect:/admin/viewDepartments";
    }


    @GetMapping("/addDoctor")
    public String DoctorAddPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Speciality> specialities = specialityService.getAllSpec();
        model.addAttribute("spec",specialities);
        return "admin/addDoctor";
    }

    @GetMapping("/viewDoctor")
    public String viewDoctorPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Speciality> specialities = specialityService.getAllSpec();
        model.addAttribute("spec",specialities);
        List<DoctorEntity> doctorEntityList = doctorRepository.findAllByDeletedAtNull();
        model.addAttribute("doc",doctorEntityList);
        return "admin/viewDoctor";
    }

    @GetMapping("/addBlog")
    public String addBlogPage(Model model){
        model.addAttribute("currentUser", getUser());
        return "admin/addBlog";
    }

    @PostMapping("/addblogform")
    public String addBlog(Model model,
                          @RequestParam(name = "id")Long id,
                          @RequestParam(name = "title")String title,
                          @RequestParam(name = "short_title")String short_title,
                          @RequestParam(name = "description")String description,
                          @RequestParam(name = "blog_img")MultipartFile file){
        model.addAttribute("currentUser", getUser());
        Blogs blogs = new Blogs();
        blogs.setUsers(getUser());
        blogs.setDescription(description);
        blogs.setShort_title(short_title);
        blogs.setTitle(title);
        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(short_title+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(baseBlogUrl+fileName+".jpg");

                Files.write(path, bytes);

                blogs.setImage(fileName);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        blogRepository.save(blogs);
        return "redirect:/admin/addBlog";
    }

    @GetMapping("/viewBlog")
    public String viewBlogPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Blogs> bl = blogRepository.findAll();
        model.addAttribute("allblogs", bl);
        return "admin/viewBlog";
    }

}
