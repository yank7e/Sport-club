package duikt.pd.sportclub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
