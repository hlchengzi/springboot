package spring_boot_start.demo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/start")
public class HelloController {


    /**
     * spring boot demo
     * http://localhost:8011/start/hello
     * @return
     */
    @GetMapping("/hello")
    @ResponseBody
    public String helloSringBoot(){
        return new String("ＨＥＬＬＯ　ＳＰＲＩＮＧ　ＢＯＯＴ");
    }
}
