package clb_hu.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模板渲染
 * 在之前所有的@RequestMapping注解的方法中，返回值字符串都被直接传送到浏览器端并显示给用户。
 * 但是为了能够呈现更加丰富、美观的页面，我们需要将HTML代码返回给浏览器，浏览器再进行页面的渲染、显示。
 * 一种很直观的方法是在处理请求的方法中，直接返回HTML代码，但是这样做的问题在于——一个复杂的页面HTML
 * 代码往往也非常复杂，并且嵌入在Java代码中十分不利于维护。更好的做法是将页面的HTML代码写在模板文件中，
 * 渲染后再返回给用户。为了能够进行模板渲染，需要将@RestController改成@Controller：
 * 
 * 
 * 在上述例子中，返回值"hello"并非直接将字符串返回给浏览器，而是寻找名字为hello的模板进行渲染，
 * 我们使用Thymeleaf模板引擎进行模板渲染，需要引入依赖：
 * <dependency>
 *   <groupId>org.springframework.boot</groupId>
 *   <artifactId>spring-boot-starter-thymeleaf</artifactId>
 * </dependency>
 */
@SpringBootApplication
@Controller
public class Template_rendering {


    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Template_rendering.class, args);

	}

}
