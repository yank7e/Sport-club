package duikt.pd.sportclub.service;

import duikt.pd.sportclub.dto.ProfileEditDto;
import duikt.pd.sportclub.dto.UserRegistrationDto;
import duikt.pd.sportclub.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User registerUser(UserRegistrationDto registrationDto);
    void deleteUserById(Long id);
    User findUserByPhoneNumber(String phoneNumber);
    User updateProfile(String currentPhoneNumber, ProfileEditDto profileDto);
    User findUserById(Long id);
    void updateUserRoles(Long userId, List<Long> roleIds);
}
