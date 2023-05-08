package com.studymatching.account;

import com.studymatching.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "account/sign-up";
        }
        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);
        return "redirect:/";
    }

    //이메일 인증
    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        //이메일 여부
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";
        if(account == null) {
            //에러가 있으면
            model.addAttribute("error","wrong.email");
            return "account/check-email";
        }
        //이메일에 토큰이 맞는지 확인이 된다면
       if(account.isValidToken(token)) {
           model.addAttribute("error","wrong.token");
           return "account/checked-email";
       }
        account.completeSignUp();


        model.addAttribute("numberOfUser",accountRepository.count());
        model.addAttribute("nickname", account.getNickname());
        return view;
    }
}
