package duikt.pd.sportclub.service.impl;

import duikt.pd.sportclub.dto.UserRegistrationDto;
import duikt.pd.sportclub.entity.Role;
import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.exception.ResourceNotFoundException;
import duikt.pd.sportclub.repository.RoleRepository;
import duikt.pd.sportclub.repository.UserRepository;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {

        if (userRepository.findByPhoneNumber(registrationDto.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }
        Role userRole = roleRepository.findByName("ROLE_CLIENT");
        if (userRole == null) {
            throw new RuntimeException("Default role not found");
        }

        User newUser = User.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .phoneNumber(registrationDto.getPhoneNumber())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .roles(Set.of(userRole))
                .qrCodeId(java.util.UUID.randomUUID().toString())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
