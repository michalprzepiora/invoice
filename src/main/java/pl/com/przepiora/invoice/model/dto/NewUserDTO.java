package pl.com.przepiora.invoice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewUserDTO {
    @NotEmpty(message = "The email field cannot be empty.")
    private String email;
    @NotEmpty(message = "The password field cannot be empty.")
    private String password1;
    @NotEmpty(message = "The re-type field cannot be empty.")
    private String password2;

}
