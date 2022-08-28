package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
public class UserRequestTO {

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    @Email
    @NotBlank
    @Size(max = 100)
    protected String email;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    protected String password;

}
