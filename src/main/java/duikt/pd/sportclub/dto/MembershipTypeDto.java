package duikt.pd.sportclub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MembershipTypeDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationDays;
}
