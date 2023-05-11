package com.studymatching.main;


import com.studymatching.account.CurrentUser;
import com.studymatching.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
        }

        return "index";
    }

    @GetMapping("/login")
    public  String login() {
        //줄게 없으니까 일단 보내기만 하면 된다.
        return "login";
    }

}