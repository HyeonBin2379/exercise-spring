package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("real")
public class DsRealConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();

        /* JDBC 드라이버 클래스로 MySQL 드라이버 클래스를 사용 */
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        /* JDBC URL을 지정하고, MySQL의 DB와 연동할 때 사용할 캐릭터셋을 utf8로 지정 */
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");

        /* DB 연동 시 사용할 사용자 계정과 암호 지정 */
        ds.setUsername("spring5");
        ds.setPassword("spring5");

        /* 커넥션 풀의 초기 커넥션 개수 & 대여 가능한 최대 커넥션 개수 설정 */
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        ds.setMaxIdle(10);

        /* 유휴 상태 커넥션 검사 옵션 설정 */
        ds.setTestWhileIdle(true);                      // 유휴 상태인 커넥션 검사
        ds.setMinEvictableIdleTimeMillis(1000*60*3);    // 최소 유휴 시간: 3분
        ds.setTimeBetweenEvictionRunsMillis(1000*10);   // 유휴 커넥션 검사 주기: 10초
        return ds;
    }
}
