package spring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> memRowMapper = new MemberRowMapper();

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* jdbcTemplate을 이용한 개별 및 전체 조회 쿼리 */
    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?", // sql 조회 쿼리: 특정 email 주소를 갖는 사용자의 정보 찾기
                memRowMapper                                // 조회 쿼리 결과를 리스트로 저장
                , email);                                   // 데이터 조회 조건으로 사용할 속성값
        return results.isEmpty() ? null : results.get(0);
    }
    public List<Member> selectAll() {
        return jdbcTemplate.query("select * from MEMBER", memRowMapper);
    }
    public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(
                "select * from MEMBER where REGDATE between ? and ?"
                        + "order by REGDATE desc",
                memRowMapper,
                from, to);
    }
    public Member selectById(Long memId) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where id = ?",
                memRowMapper, memId);
        return results.isEmpty() ? null : results.get(0);
    }

    /* jdbcTemplate과 queryForObject를 활용한 전체 인원수 구하기 */
    public Integer count() {
        return jdbcTemplate.queryForObject("select count(*) from MEMBER", Integer.class);
    }

    /* jdbcTemplate을 이용한 변경 쿼리 */
    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail());
    }

    /* jdbcTemplate과 PreparedStatement를 이용한 삽입 쿼리 */
    public void insert(Member member) {
        /* keyHolder: 쿼리 실행 후 생성된 key값을 저장 */
        KeyHolder keyholder = new GeneratedKeyHolder();

        /* PreparedMemberCreatorForInsert : PreparedStatementCreator 인터페이스 구현체 */
        jdbcTemplate.update(
                con -> new PreparedMemberCreatorForInsert(member)
                        .createPreparedStatement(con),
                keyholder);
        Number keyValue = keyholder.getKey();
        member.setId(Objects.requireNonNull(keyValue).longValue());
    }

    /* jdbcTemplate을 이용한 삭제 쿼리(이후의 반복 실행을 위해 id=1인 행을 제외한 나머지 삭제) */
    public void deleteByEmail(String email) {
        jdbcTemplate.update("delete from MEMBER where EMAIL != ?", email);
    }
}
