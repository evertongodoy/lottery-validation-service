package com.lottery.validation.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring6.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring6.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig implements WebFluxConfigurer {

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false); // Set to true in production
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCheckExistence(true);
        return resolver;
    }

    @Bean
    public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
        final SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
        final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        viewResolver.setResponseMaxChunkSizeBytes(8192);
        return viewResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafReactiveViewResolver());
    }
}
