package recipes;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity // (name = "User")
@Getter
@Setter
public class User {
    private static final String emailRegex = ".+@.+\\..+";
    @Id
    @NonNull
    @NotBlank
    @Email(regexp = emailRegex)
    private String email;

    @NonNull
    @NotBlank
    @Size(min = 8)
    private String password;

}
