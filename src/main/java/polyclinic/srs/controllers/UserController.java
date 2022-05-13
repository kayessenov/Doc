package polyclinic.srs.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import polyclinic.srs.entities.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import polyclinic.srs.services.UserService;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UserController {

    @Value("${baseAva}")
    private String baseAva;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }


    @GetMapping(value = "/signin")
    public String signin(Model model){
        model.addAttribute("currentUser", getUser());
        return "/auth/signin";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser", getUser());
        return "/auth/profile";
    }

    @GetMapping(value = "/signup")
    public String signUpPage(Model model){
        model.addAttribute("currentUser",getUser());
        return "/auth/signup";
    }

    @PostMapping("/tosignup")
    public String signUp(@RequestParam(name = "user_email") String email,
                         @RequestParam(name = "user_password")String password,
                         @RequestParam(name = "re_user_password")String rePassword,
                         @RequestParam(name = "user_fullName")String fullName){
        if(password.equals(rePassword)){

            Users newUser = new Users();
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setFullname(fullName);
            userService.registerUser(newUser);
            if(newUser.getId() != null){
                return "redirect:/signin";
            }

        }
        return "redirect:/signup?error";
    }


    @PostMapping("/updatepassword")
    public String updatePassword(@RequestParam(name = "old_password")String old_Pass,
                                 @RequestParam(name = "new_password")String newPass,
                                 @RequestParam(name = "retype_new_password")String reNewPass){
        if(newPass.equals(reNewPass)){
            if(userService.updatePassword(getUser(),old_Pass,newPass)){
                return "redirect:/profile?success";
            }
        }
        return "redirect:/profile?error";
    }

    @PostMapping(value = "/uploadava")
    @PreAuthorize("isAuthenticated()")
    public String uploadAva(@RequestParam(name = "user_avatar") MultipartFile file){

        Users currentUser = getUser();

        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(Objects.requireNonNull(getUser()).getId()+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(baseAva+fileName+".jpg");

                Files.write(path, bytes);

                assert currentUser != null;
                currentUser.setAvatar(fileName);
                userService.updateAvatar(currentUser);

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return "redirect:/profile";

    }

}
