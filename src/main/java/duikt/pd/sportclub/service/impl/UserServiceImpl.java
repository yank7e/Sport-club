package duikt.pd.sportclub.service.impl;

import duikt.pd.sportclub.dto.ProfileEditDto;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User updateProfile(String currentPhoneNumber, ProfileEditDto profileDto) {
        User userToUpdate = findUserByPhoneNumber(currentPhoneNumber);

        userToUpdate.setFirstName(profileDto.getFirstName());
        userToUpdate.setLastName(profileDto.getLastName());
        userToUpdate.setEmail(profileDto.getEmail());

        return userRepository.save(userToUpdate);
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone: " + phoneNumber));
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {

        if (userRepository.findByPhoneNumber(registrationDto.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }

        Role userRole;

        if (userRepository.count() == 0) {
            userRole = roleRepository.findByName("ROLE_ADMIN");
            if (userRole == null) {
                throw new RuntimeException("ROLE_ADMIN not found. Please initialize roles.");
            }
        } else {
            userRole = roleRepository.findByName("ROLE_CLIENT");
            if (userRole == null) {
                throw new RuntimeException("ROLE_CLIENT not found. Please initialize roles.");
            }
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
        User user = findUserByPhoneNumber(username);

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

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        User user = findUserById(userId);

        if (roleIds == null || roleIds.isEmpty()) {
            user.getRoles().clear();
        } else {
            List<Role> newRoles = roleRepository.findAllById(roleIds);
            user.setRoles(new HashSet<>(newRoles));
        }

        userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
