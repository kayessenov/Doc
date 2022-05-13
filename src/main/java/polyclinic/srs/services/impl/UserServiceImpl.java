package polyclinic.srs.services.impl;

import polyclinic.srs.entities.Roles;
import polyclinic.srs.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import polyclinic.srs.repositories.UserRepository;
import polyclinic.srs.services.UserService;
import polyclinic.srs.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if(user!=null){
            return user;
        }
        else{
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public Users registerUser(Users user) {

        Users checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser==null){

            Roles userRole = rolesRepository.findByRole("ROLE_USER");
            List<Roles> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }

        return null;

    }

    @Override
    public boolean updatePassword(Users user, String oldPassword, String newPassword) {

        Users checkUser = userRepository.findByEmail(user.getEmail());

        if(passwordEncoder.matches(oldPassword,checkUser.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Users updateAvatar(Users user) {
        Users checUser = userRepository.findByEmail(user.getEmail());

        if(checUser!=null){
            checUser.setAvatar(user.getAvatar());
            return userRepository.save(checUser);
        }

        return null;

    }
}
