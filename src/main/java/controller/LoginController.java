package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

@Controller
@RequestMapping("/login")
public class LoginController {

    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String form(LoginCommand loginCommand,
            @CookieValue(value="REMEMBER", required = false) Cookie rCookie) {
        if (rCookie != null) {
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }

    @PostMapping
    public String submit(@Valid LoginCommand loginCommand, Errors errors, HttpServletRequest request,
            HttpServletResponse response) {
        if (errors.hasErrors()) {
            return "login/loginForm";
        }

        try {
            AuthInfo authInfo = authService.authenticate(
                    loginCommand.getEmail(), loginCommand.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("authInfo", authInfo);
            response.addCookie(bakeCookie(loginCommand));
            return "login/loginSuccess";
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "login/loginForm";
        }
    }
    private Cookie bakeCookie(LoginCommand loginCommand) {
        Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
        rememberCookie.setPath("/");
        if (loginCommand.isRememberEmail()) {
            rememberCookie.setMaxAge(60*60*24*30);
        } else {
            rememberCookie.setMaxAge(0);
        }
        return rememberCookie;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new LoginCommandValidator());
    }
}
