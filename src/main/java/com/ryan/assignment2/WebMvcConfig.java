package com.ryan.assignment2;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()
    {
        CookieLocaleResolver resolver= new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.UK);
        resolver.setCookieMaxAge(60*60*24);
        return resolver;
    }

    @Bean(name = "messageSource")
    public MessageSource getMessageResource()
    {
        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();

        messageResource.setBasename("classpath:locales/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");

        registry.addInterceptor(localeInterceptor).addPathPatterns("/**");
    }

}