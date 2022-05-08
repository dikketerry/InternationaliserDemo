package io.eho.internationaliserdemo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();
        messageSource.setBasenames("language/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    /*
    ResourceBundleMessageSource is an implementation of MessageSource which
    accesses Java resource bundles using specified base names. When
    configuring MessageSource the path for storing the message files for
    supported languages is defined. An alternative is to configure the
    MessageSource in the application.properties file:
    spring.messages.basename=language/messages
     */

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        slr.setLocaleAttributeName("current.locale");
        slr.setTimeZoneAttributeName("current.timezone");
        return slr; // initially, this returned a LocaleResolver; not a
        // SessionLocaleResolver
    }
    /*
    localeResolver is an interface used for resolving the locale of a user
    from the incoming request. Spring provides 4 type of implementations:
    1 - FixedLocaleResolver - mostly for debugging. Resolves locale to a
    fixed language set in application.properties
    2 - AcceptHeaderLocaleResolver - resolves the locale using an accept HTTP
    header retrieved from an HTTP request
    3 - SessionLocaleResolver - stores locale selected by user in attribute
    of HTTPSession and resolves by reading that attribute
    4 - CookieLocaleResolver - stores locale selected by user in a cookie on
    the user machine and resolves by reading cookie
     */

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor =
                new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /*
    LocaleChangeInterceptor is an implementation of the HandlerInterceptor
    interface. The LocaleChangeInterceptor will handle the switch to a new
    locale based on the value of the 'language' parameter:
    http://localhost:8080/index?language=de >> switch to German
    http://localhost:8080/index?language=fr >> switch to French
    The bean is added to the InterceptorRegistry, which helps with keeping a
    list of mapped interceptors (TODO: investigate this more)
     */



}
