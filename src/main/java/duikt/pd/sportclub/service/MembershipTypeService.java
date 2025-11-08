package duikt.pd.sportclub.service;

import duikt.pd.sportclub.dto.MembershipTypeDto;
import duikt.pd.sportclub.entity.MembershipType;
import java.util.List;

public interface MembershipTypeService {
    List<MembershipType> findAll();
    MembershipType findById(Long id);
    MembershipType create(MembershipTypeDto dto);
    MembershipType update(Long id, MembershipTypeDto dto);
    void deleteById(Long id);
    MembershipTypeDto mapEntityToDto(MembershipType entity);
}
