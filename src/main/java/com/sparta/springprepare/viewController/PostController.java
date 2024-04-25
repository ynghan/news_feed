package com.sparta.springprepare.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    // 게시판 페이지
    @GetMapping("/feed")
    public String feedPage() {
        return "feed";
    }

}
