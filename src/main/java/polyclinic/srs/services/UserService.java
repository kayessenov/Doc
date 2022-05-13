package polyclinic.srs.services;

import polyclinic.srs.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users registerUser(Users user);

    Users updateAvatar(Users user);
    boolean updatePassword(Users user, String oldPassword, String newPassword);

}
