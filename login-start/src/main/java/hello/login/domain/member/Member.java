package hello.login.domain.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class Member {

    private Long id;

    @NotEmpty
    private String loginId; // 로그인 아이디

    @NotEmpty
    private String name; // 이름

    @NotEmpty
    private String password;

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
