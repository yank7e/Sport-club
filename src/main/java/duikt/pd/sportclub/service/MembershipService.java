package duikt.pd.sportclub.service;

import duikt.pd.sportclub.entity.Membership;
import java.util.List;

public interface MembershipService {

    Membership subscribeUserToMembership(Long userId, Long membershipTypeId);

    List<Membership> findMembershipsByUserId(Long userId);
}
