package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.lang.NonNull;

public class PreparedMemberCreatorForInsert implements PreparedStatementCreator {

    private final Member member;

    public PreparedMemberCreatorForInsert(Member member) {
        this.member = member;
    }


    @Override
    @NonNull public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        /* 1. 전달받은 Connection으로 PreparedStatement 생성 */
        PreparedStatement pstmt = con.prepareStatement(
                // sql문의 괄호 안 속성은 인덱스 파라미터
                "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) values (?, ?, ?, ?)",
                new String[]{"ID"});

        /* 인덱스 파라미터 값 결정 (아래 update() 메서드와 동일) */
        pstmt.setString(1, member.getEmail());
        pstmt.setString(2, member.getPassword());
        pstmt.setString(3, member.getName());
        pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));

        /* 생성한 PreparedStatement 객체 리턴 */
        return pstmt;
    }
}
