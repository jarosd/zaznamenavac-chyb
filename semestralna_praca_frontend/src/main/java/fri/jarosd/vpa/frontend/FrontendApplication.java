package fri.jarosd.vpa.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpSession;

@SpringBootApplication
public class FrontendApplication {

    public static String cestaNieSomPrihlaseny = "redirect:/";

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    public static boolean somPrihlaseny(HttpSession session) {
        return session.getAttribute("nick") != null;
    }

}
