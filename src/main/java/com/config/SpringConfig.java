package com.config;
import com.controller.logic.Dictionary;
import com.controller.logic.FileOperation;
import com.model.DictionaryStorage;
import com.model.DictionaryType;
import com.view.Console;
import liquibase.integration.spring.SpringLiquibase;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
@Import({
        org.springdoc.webmvc.ui.SwaggerConfig.class,
        org.springdoc.core.SwaggerUiConfigProperties.class, org.springdoc.core.SwaggerUiOAuthProperties.class,
        org.springdoc.webmvc.core.SpringDocWebMvcConfiguration.class,
        org.springdoc.webmvc.core.MultipleOpenApiSupportConfiguration.class,
        org.springdoc.core.SpringDocConfiguration.class, org.springdoc.core.SpringDocConfigProperties.class,
        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class
})

@Configuration
@EnableWebMvc
@ComponentScan("com")
@PropertySource("classpath:config.properties")
@PropertySource("application.properties")
public class SpringConfig implements WebMvcConfigurer {
    @Value("#{${valuesMap}}")
    private Map<Integer, String> dictionaryTypeMap;
    private final ApplicationContext applicationContext;
    @Value("${divider}")
    private String lineDelimiter;

    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    @Value("${driver}")
    private String driver;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Bean
    public Map<Integer, DictionaryType> getDictionaryTypeMap() {
        Map<Integer, DictionaryType> dictionaryMap = new HashMap<>();
        for (Map.Entry<Integer, String> pair : dictionaryTypeMap.entrySet()) {
            String[] lineSplit  = pair.getValue().split(lineDelimiter);
            dictionaryMap.put(pair.getKey(), new DictionaryType(lineSplit[0], lineSplit[1], lineSplit[2]));
        }
        return dictionaryMap;
    }

    @Bean
    public Map<Integer, DictionaryStorage> getDictionaryStorageMap(){
        Map<Integer, DictionaryStorage> dictionaryStorageMap = new HashMap<>();
        for (Map.Entry<Integer, DictionaryType> pair : getDictionaryTypeMap().entrySet()){
            dictionaryStorageMap.put(pair.getKey(), new DictionaryStorage(pair.getValue().getDictionaryPath()));
        }
        return dictionaryStorageMap;
    }

    @Bean
    @Lazy
    public Map<Integer, FileOperation> getFileDictionary() {
        Map<Integer, FileOperation> fileOperationMap = new HashMap<>();
        for (Map.Entry<Integer, DictionaryType> pair : getDictionaryTypeMap().entrySet()) {
            fileOperationMap.put(pair.getKey(), new FileOperation(pair.getValue()));
        }
        return fileOperationMap;
    }

    @Bean
    @Lazy
    public Map<Integer, Dictionary> getMapDictionary() {
        Map<Integer, Dictionary> fileOperationMap = new HashMap<>();
        for (Map.Entry<Integer, DictionaryType> pair : getDictionaryTypeMap().entrySet()) {
            fileOperationMap.put(pair.getKey(), new Dictionary(pair.getValue()));
        }
        return fileOperationMap;
    }

    @Bean
    public Console getConsole() {
        return new Console();
    }
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:database/changelog/liquibase-changelog.xml");
        return liquibase;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }


}
