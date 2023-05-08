package com.studymatching.account;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {
    @NotBlank
    @Size(min=8, max=50)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickName;

    @NotBlank
    @Size(min=8, max=50)
    private String passWord;

    @Email
    @NotBlank
    private String email;

}
