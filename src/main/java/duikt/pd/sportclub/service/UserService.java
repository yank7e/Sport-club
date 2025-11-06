package duikt.pd.sportclub.service;

import duikt.pd.sportclub.dto.UserRegistrationDto;
import duikt.pd.sportclub.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerUser(UserRegistrationDto registrationDto);
    void deleteUserById(Long id);
}
