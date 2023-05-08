package com.studymatching.main;

import com.studymatching.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


    //첫 패이지로 가는 핸들러 요청
    @Controller
    public class MainController {

        @GetMapping("/")
        public String home(Account account, Model model) {
            if (account != null) {
                model.addAttribute("account", account);
            }

            return "index";
        }
    }

