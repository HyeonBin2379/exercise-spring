package spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        /* 조건을 충족하는 각 tuple의 속성값을 Member 객체의 각 필드에 매핑하여 저장 */
        Member member = new Member(
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getTimestamp("REGDATE").toLocalDateTime());
        member.setId(rs.getLong("ID"));
        return member;
    }
}
