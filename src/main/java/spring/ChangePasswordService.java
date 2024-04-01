package spring;


import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {

    private MemberDao memberDao;

    /* Service 메서드의 기능은 모든 중간 과정을 성공적으로 진행했을 때만 완료 가능,
    *  따라서 Service의 메서드는 트랜잭션 범위에서 수행 */
    @Transactional
    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        member.changePassword(oldPwd, newPwd);
        memberDao.update(member);
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
