package duikt.pd.sportclub.service.impl;

import duikt.pd.sportclub.entity.Membership;
import duikt.pd.sportclub.entity.MembershipType;
import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.exception.ResourceNotFoundException;
import duikt.pd.sportclub.repository.MembershipRepository;
import duikt.pd.sportclub.repository.MembershipTypeRepository;
import duikt.pd.sportclub.repository.UserRepository;
import duikt.pd.sportclub.service.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final MembershipTypeRepository membershipTypeRepository;

    @Override
    public Membership subscribeUserToMembership(Long userId, Long membershipTypeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MembershipType type = membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("MembershipType not found"));

        Membership newMembership = new Membership();
        newMembership.setUser(user);
        newMembership.setMembershipType(type);
        newMembership.setStartDate(LocalDate.now());
        newMembership.setEndDate(LocalDate.now().plusDays(type.getDurationDays()));
        newMembership.setActive(true);

        return membershipRepository.save(newMembership);
    }

    @Override
    public List<Membership> findMembershipsByUserId(Long userId) {
        return membershipRepository.findByUserId(userId);
    }
}
