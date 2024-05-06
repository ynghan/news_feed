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

    @GetMapping("/other")
    public String otherPage() {
        return "other";
    }

    @GetMapping("/boot")
    public String bootPage() {
        return "boot";
    }

    @GetMapping("/t1")
    public String boot2Page() {
        return "t1";
    }


}
