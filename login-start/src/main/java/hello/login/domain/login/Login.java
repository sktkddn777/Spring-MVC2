package hello.login.domain.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Login {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
