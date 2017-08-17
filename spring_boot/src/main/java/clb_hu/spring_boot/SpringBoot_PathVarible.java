package clb_hu.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在Web应用中URL通常不是一成不变的，例如微博两个不同用户的个人主页对应两个不同的URL:http://weibo.com/user1，http://weibo.com/user2。
 * 我们不可能对于每一个用户都编写一个被@RequestMapping注解的方法来处理其请求，
 * Spring MVC提供了一套机制来处理这种情况：
 *
 */

@SpringBootApplication
@RestController
public class SpringBoot_PathVarible {

	/**
	 * http://localhost:8080/user/hujianjun   访问结果：user hujianjun
	 */
	@RequestMapping("/user/{username}")
	public String userProfile(@PathVariable("username") String username){
		return String.format("user %s", username);
	}
	
	/**
	 * http://localhost:8080/posts/1001   访问结果：post 1001
	 */
	@RequestMapping("/posts/{id}")
	public String post(@PathVariable("id") int id) {
	    return String.format("post %d", id);
	}
	
	/**
	 * 在上述例子中，URL中的变量可以用{variableName}来表示，
	 * 同时在方法的参数中加上@PathVariable("variableName")，
	 * 那么当请求被转发给该方法处理时，对应的URL中的变量会被自动赋值给
	 * 被@PathVariable注解的参数（能够自动根据参数类型赋值，例如上例中的int）
	 */
	public static void main(String[] args) {

		SpringApplication.run(SpringBoot_PathVarible.class, args);
		
	}

}
