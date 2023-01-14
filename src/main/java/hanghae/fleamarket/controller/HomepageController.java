package hanghae.fleamarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api")
public class HomepageController {

    @GetMapping("/homepage")
    public ModelAndView homePage() {
        return new ModelAndView("index");
    }
}

