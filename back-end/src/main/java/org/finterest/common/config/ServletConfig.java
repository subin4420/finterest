package org.finterest.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@ComponentScan(basePackages = {
        "org.finterest.controller",
        "org.finterest.exception",

        //B_Team
        "org.finterest.quiz.controller",
        "org.finterest.archive.controller",
        "org.finterest.user.controller",
        "org.finterest.point.controller",
        "org.finterest.notice.controller",
        "org.finterest.adminChart.controller",
        "org.finterest.ranking.controller",
        //A_Team
        "org.finterest.invest.board.controller",
        "org.finterest.invest.stock.overall.controller",
        "org.finterest.invest.stock.simulator.controller",
        "org.finterest.invest.conversion.controller",
        "org.finterest.invest.comment.controller",
        "org.finterest.invest.scenario.controller",
        "org.finterest.invest.portfolio.controller"


})
public class ServletConfig  implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/resources/index.html");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/resources/assets/");
    }


    //	Servlet 3.0 파일 업로드 사용시
    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }

}
