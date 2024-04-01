package controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.DuplicateMemberException;
import spring.Member;
import spring.MemberDao;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@RestController
public class RestMemberController {

    private MemberDao memberDao;

    private MemberRegisterService registerService;

    @GetMapping("/api/members")
    public List<Member> members() {
        return memberDao.selectAll();
    }

    @PostMapping("/api/members")
    public ResponseEntity<Object> newMember(
            @RequestBody @Valid RegisterRequest regReq) {
        try {
            Long newMemberId = registerService.regist(regReq);
            URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build();
        } catch (DuplicateMemberException dupEx) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Object> newMember2(
            @RequestBody @Valid RegisterRequest regReq,
            Errors errors) {
        if (errors.hasErrors()) {
            String errorCodes = errors.getAllErrors()
                    .stream()
                    .map(error -> Objects.requireNonNull(error.getCodes())[0])
                    .collect(Collectors.joining(","));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("errorCode = " + errorCodes));
        }
        try {
            Long newMemberId = registerService.regist(regReq);
            URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build();
        } catch (DuplicateMemberException dupEx) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/api/members/{id}")
    public ResponseEntity<Object> member(@PathVariable(value = "id") Long id) {
        Member member = memberDao.selectById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("no member"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setRegisterService(MemberRegisterService registerService) {
        this.registerService = registerService;
    }
}
