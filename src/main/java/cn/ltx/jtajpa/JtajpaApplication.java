package cn.ltx.jtajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Description:在springbootApplication上加上"@ServletComponentScan"注解后，
 * Servlet、Filter、Listener可以直接通过
 * "@WebServlet"、"@WebFilter"、"@WebListener"注解自动注册，无需其他代码
 *
 * @author litianxiang
 * @date 2019-10-31
 */
@SpringBootApplication
@ServletComponentScan
public class JtajpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtajpaApplication.class, args);
    }

}
