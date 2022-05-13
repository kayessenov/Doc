package polyclinic.srs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import polyclinic.srs.entities.*;
import polyclinic.srs.repositories.*;
import polyclinic.srs.services.DepartmentService;

import javax.persistence.Convert;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class MainController {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        List<Speciality> spec = specialityRepository.findAllByDeletedAtNull();
        model.addAttribute("spec", spec);
        model.addAttribute("docs", docs);
        return "index";
    }

    @GetMapping("/About")
    public String AboutPage(Model model){
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        model.addAttribute("currentUser", getUser());
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        model.addAttribute("docs", docs);
        return "about";
    }

    @GetMapping("/Blog")
    public String blogPage(Model model){
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        model.addAttribute("currentUser", getUser());
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        model.addAttribute("docs", docs);
        List<Blogs> bl = blogRepository.findAll();
        model.addAttribute("allblogs", bl);
        return "blog";
    }

    @GetMapping("/Contact")
    public String ContactPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        List<Speciality> spec = specialityRepository.findAllByDeletedAtNull();
        model.addAttribute("spec", spec);
        model.addAttribute("docs", docs);
        return "contact";
    }

    @GetMapping("/Department")
    public String DepartmentPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        List<Speciality> spec = specialityRepository.findAllByDeletedAtNull();
        model.addAttribute("spec", spec);
        model.addAttribute("docs", docs);
        return "Department";
    }

    @GetMapping("/Doctors")
    public String DoctorsPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        List<Speciality> spec = specialityRepository.findAllByDeletedAtNull();
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        model.addAttribute("spec", spec);
        model.addAttribute("docs", docs);
        return "Doctors";
    }

    @PostMapping("/main")
    public String mainForm(Model model,
                           @RequestParam(name = "datepick")String datepick,
                           @RequestParam(name = "datepick2")String datepick2,
                           @RequestParam(name = "dpid")Long dpid,
                           @RequestParam(name = "docid")Long docid,
                           @RequestParam(name = "name")String name,
                           @RequestParam(name = "email")String email,
                           @RequestParam(name = "number")String number) throws ParseException {
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        model.addAttribute("currentUser", getUser());
        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        model.addAttribute("docs", docs);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date2  = new SimpleDateFormat("dd/MM/yyyy").parse(datepick2);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(datepick);
        Appointment appointment = new Appointment();
        appointment.setEmail(email);
        appointment.setName(name);
        appointment.setNumber(number);
        appointment.setPickdate(date);
        appointment.setPickdate2(date2);
        DoctorEntity doc = doctorRepository.getById(docid);
        Departments dep = departmentService.getDepartment(dpid);
        appointment.setDoctorEntity(doc);
        appointment.setDepartments(dep);
        appointmentRepository.save(appointment);
        return "redirect:?";
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        List<Departments> departmentsList = departmentService.getAllDepartments();
        model.addAttribute("DepList",departmentsList);
        model.addAttribute("currentUser", getUser());

        List<DoctorEntity> docs = doctorRepository.findAllByDeletedAtNull();
        model.addAttribute("docs", docs);
        return "/layout/main";
    }

    @GetMapping("/Elements")
    public String ElementsPage(Model model){
        model.addAttribute("currentUser", getUser());
        return "elements";
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/SingleBlog/{blogId}")
    public String blogsGet(Model model,
                           @PathVariable(name = "blogId") Long id) {
        Blogs blog = blogRepository.getById(id);
        List<Comments> comments = commentsRepository.findAllByBlogs_Id(id);
        model.addAttribute("currentUser", getUser());
        if(blog!=null) {
            model.addAttribute("blogsget", blog);
        }
        model.addAttribute("comments", comments);
        return "single-blog";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addcomment")
    public String addComment(Model model,
                             @RequestParam(name = "blogid")Long blogid,
                             @RequestParam(name = "userid")Long userid,
                             @RequestParam(name = "comment")String comment){
        Optional<Users> opt = userRepository.findById(userid);
        Optional<Blogs> blg = blogRepository.findById(blogid);
        model.addAttribute("currentUser", getUser());
        Comments comments = new Comments();
        comments.setComment(comment);
        Users users = opt.get();
        Blogs blogs = blg.get();
        comments.setUsers(users);
        comments.setBlogs(blogs);
        commentsRepository.save(comments);
        return "redirect:/SingleBlog/"+blogid;
    }
}
