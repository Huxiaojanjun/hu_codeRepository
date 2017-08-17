package clb_hu.spring_boot;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 利用Spring Boot快速的搭建一个简单的web应用。
 * 
 * 使用Spring Boot框架可以大大加速Web应用的开发过程，
 * 首先在Maven项目依赖中引入spring-boot-starter-web：pom.xml
 *
 */

/**
 * 1.SpringApplication是Spring Boot框架中描述Spring应用的类，它的run()方法会创建一个Spring应用上下文（Application Context）。
 * 另一方面它会扫描当前应用类路径上的依赖，例如本例中发现spring-webmvc（由 spring-boot-starter-web传递引入）在类路径中，
 * 那么Spring Boot会判断这是一个Web应用，并启动一个内嵌的Servlet容器（默认是Tomcat）用于处理HTTP请求。
 * 
 * 2.Spring WebMvc框架会将Servlet容器里收到的HTTP请求根据路径分发给对应的@Controller类进行处理，
 * @RestController是一类特殊的@Controller，它的返回值直接作为HTTP Response的Body部分返回给浏览器。
 * 
 * 3.@RequestMapping注解表明该方法处理那些URL对应的HTTP请求，也就是我们常说的URL路由（routing)，
 * 请求的分发工作是有Spring完成的。例如上面的代码中http://localhost:8080/根路径就被路由至showName()方法进行处理。
 * 如果访问http://localhost:8080/hi，则会出现404 Not Found错误，因为我们并没有编写任何方法来处理/hi请求。
 *
 * 4.现代Web应用往往包括很多页面，不同的页面也对应着不同的URL。对于不同的URL，通常需要不同的方法进行处理并返回不同的内容。
 */

@SpringBootApplication
@RestController
public class SpringApplicationDemo {
	/**
	 * showName()   可以在页面显示控制台输入的名字
	 */
	@RequestMapping("/")
	public String showName(){
		System.out.println("请输入你的名字：");
		Scanner sc= new Scanner(System.in);
		String nextLine = sc.nextLine();
		return nextLine;
	}
	
	@RequestMapping("/hello")
	public String hello(){
		return "Hello World!!!";
	}
	
	@RequestMapping("/page")
	public String index(){
		return "Index Page!!!";
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SpringApplicationDemo.class, args);
	}

}
