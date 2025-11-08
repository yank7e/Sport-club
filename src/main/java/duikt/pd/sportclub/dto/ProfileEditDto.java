package duikt.pd.sportclub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileEditDto {
    private String firstName;
    private String lastName;
    private String email;
}
