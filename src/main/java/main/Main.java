package main;

import config.DsDevConfig;
import config.DsRealConfig;
import config.MemberConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(MemberConfig.class, DsDevConfig.class, DsRealConfig.class);
        context.refresh();
    }
}
