package com.teaming.TeamingServer.Domain.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.(공백 없이 입력해주세요.)")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.(공백 없이 입력해주세요.)")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.(공백 없이 입력해주세요.)")
//    @Length(min=8, max=16, message="비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "위에 입력한 비밀번호와 일치하게 입력해주세요.(공백 없이 입력해주세요.)")
    private String checkPassword;
}
