package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import spring.RegisterRequest;

public class RegisterRequestValidator implements Validator {

    /* 이메일 유효성 검증용 정규표현식 */
    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-\\+]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern;
    public RegisterRequestValidator() {
        pattern = Pattern.compile(emailRegExp);
    }

    /* 파라미터로 전달받은 클래스를 RegisterRequest 클래스 타입으로 변환 가능한지 확인 */
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    /* 회원 가입 Form 입력값 검증 수행 */
    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest regReq = (RegisterRequest) target;

        /* 이메일 유효성 검사: 이 값은 입력 필수, 중복 불가. */
        if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "required");
        } else {
            Matcher matcher = pattern.matcher(regReq.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue("email", "bad");
            }
        }

        /* 이름 유효성 검사: 이 값은 입력 필수, 공백이나 빈 문자열 입력 불가. */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");

        /* 비밀번호 유효성 검사: 이 값은 입력 필수, 빈 문자열를 입력 불가 */
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");

        /* 비밀번호 확인 유효성 검사: 처음 입력한 비밀번호와 재입력한 비밀번호가 서로 일치해야 유효 */
        if (!regReq.getPassword().isEmpty()) {
            if (!regReq.isPasswordEqualToConfirmPassword()) {
                errors.rejectValue("confirmPassword", "nomatch");
            }
        }
    }
}
