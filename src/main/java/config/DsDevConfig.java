package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile("dev")
public class DsDevConfig {
    @Value("${dev.driver}")
    private String driver;
    @Value("${dev.url}")
    private String jdbcUrl;
    @Value("${dev.user}")
    private String user;
    @Value("${dev.password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer =
                new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("/db/dev.properties"));
        return configurer;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();

        /* JDBC 드라이버 클래스로 MySQL 드라이버 클래스를 사용 */
        ds.setDriverClassName(driver);

        /* JDBC URL을 지정하고, MySQL의 DB와 연동 시 사용할 캐릭터셋을 utf8로 지정 */
        ds.setUrl(jdbcUrl);

        /* DB 연동 시 사용할 사용자 계정과 암호 지정 */
        ds.setUsername(user);
        ds.setPassword(password);

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
