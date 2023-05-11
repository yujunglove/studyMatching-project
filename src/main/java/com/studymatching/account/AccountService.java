package com.studymatching.account;

import com.studymatching.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.security.Security;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService  {

    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    //이메일 보냈는지 안보냈는지는 컨트롤러에서 알 필요가 없고
    //서비스에서만 알게 된다.
    public Account processNewAccount(SignUpForm signUpForm) {
        //어카운트 저장
        Account newAccount = saveNewAccount(signUpForm);
        //
        newAccount.generateEmailCheckToken();
        //이메일 확인 보내기
        sendSignUpConfirmEmail(newAccount);

        return newAccount;
    }

    private Account saveNewAccount(@Valid SignUpForm signUpForm) {
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(signUpForm.getPassword())
                .studyCreatedByWeb(true)
                .studyEnrollmentResultByWeb(true)
                .studyUpdateByWeb(true)
                .build();
        return accountRepository.save(account);
    }

    void sendSignUpConfirmEmail(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setText("스터디 매칭, 회원 가입 인증하기");
        //UUID가 제대로 된 값이 들어오는지 확인하기
        mailMessage.setSubject("/check-email-token?token="+ newAccount.getEmailCheckToken()+
                "&email="+ newAccount.getEmail());
        //위에가 메세지 보내기

        //심플 메세지
        javaMailSender.send(mailMessage);
    }

//로그인
public void login(Account account) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            new com.studyolle.modules.account.UserAccount(account),
            account.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
    SecurityContextHolder.getContext().setAuthentication(token);
}

    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(emailOrNickname);
        if (account == null) {
            account = accountRepository.findByNickname(emailOrNickname);
        }
        //그래도 어카운트가 널이면 예외를 던지기
        if (account == null) {
            throw new UsernameNotFoundException(emailOrNickname);
        }

        return new com.studyolle.modules.account.UserAccount(account);
    }
}
