package duikt.pd.sportclub.service.impl;

import duikt.pd.sportclub.dto.MembershipTypeDto;
import duikt.pd.sportclub.entity.MembershipType;
import duikt.pd.sportclub.exception.ResourceNotFoundException;
import duikt.pd.sportclub.repository.MembershipTypeRepository;
import duikt.pd.sportclub.service.MembershipTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MembershipTypeServiceImpl implements MembershipTypeService {

    private final MembershipTypeRepository repository;

    @Override
    public List<MembershipType> findAll() {
        return repository.findAll();
    }

    @Override
    public MembershipType findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MembershipType not found with id: " + id));
    }

    @Override
    public MembershipType create(MembershipTypeDto dto) {
        MembershipType entity = new MembershipType();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDurationDays(dto.getDurationDays());
        return repository.save(entity);
    }

    @Override
    public MembershipType update(Long id, MembershipTypeDto dto) {
        MembershipType entity = findById(id);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDurationDays(dto.getDurationDays());
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("MembershipType not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public MembershipTypeDto mapEntityToDto(MembershipType entity) {
        MembershipTypeDto dto = new MembershipTypeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDurationDays(entity.getDurationDays());
        return dto;
    }
}
