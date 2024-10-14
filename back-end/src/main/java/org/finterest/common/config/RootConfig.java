package org.finterest.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:/application.properties"})
@MapperScan(basePackages  = {
        //B팀
        "org.finterest.archive.mapper",
        "org.finterest.quiz.mapper",
        "org.finterest.user.mapper",
        "org.finterest.point.mapper",
        "org.finterest.notice.mapper",
        "org.finterest.adminChart.mapper",
        "org.finterest.ranking.mapper",

        //A팀
        "org.finterest.invest.stock.overall.mapper",
        "org.finterest.invest.conversion.mapper",
        "org.finterest.invest.board.mapper",
        "org.finterest.invest.comment.mapper",
        "org.finterest.invest.stock.simulator.mapper",
        "org.finterest.invest.scenario.mapper"

})
@ComponentScan(basePackages = {
        "org.finterest.security.config",
        "org.finterest.common.config",

        "org.finterest.user",
        "org.finterest.archive",
        "org.finterest.quiz",
        "org.finterest.point",
        "org.finterest.notice",
        "org.finterest.adminChart",
        "org.finterest.ranking",


        "org.finterest.invest.stock.overall.service",
        "org.finterest.invest.stock.simulator.service",
        "org.finterest.invest.board.service",
        "org.finterest.invest.conversion.service",
        "org.finterest.invest.comment.service",
        "org.finterest.invest.scenario.service"

})
@Slf4j
@EnableTransactionManagement
public class RootConfig {
    @Value("${jdbc.driver}") String driver;
    @Value("${jdbc.url}") String url;
    @Value("${jdbc.username}") String username;
    @Value("${jdbc.password}") String password;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfigLocation(
                applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());
        return manager;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate1(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
