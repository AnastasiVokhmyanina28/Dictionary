package com.config;
import com.model.DictionaryStorage;
import com.model.DictionaryType;
import com.view.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebMvc
@ComponentScan("com")
@PropertySource("classpath:config.properties")

public class SpringConfig implements WebMvcConfigurer {
    @Value("#{${valuesMap}}")
    private Map<Integer, String> dictionaryTypeMap;
    private int dictionaryTypeOne = 1;
    private int dictionaryTypeTwo = 2;

    private final ApplicationContext applicationContext;
    @Value("${divider}")
    private String lineDelimiter;

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

    @Bean("file")
    public DictionaryType getFileDictionary() {
        return getDictionaryTypeMap().get(dictionaryTypeTwo);
    }

    @Bean("map")
    public DictionaryType getMapDictionary() {
        return getDictionaryTypeMap().get(dictionaryTypeOne);
    }

    @Bean
    public Console getConsole() {
        return new Console();
    }
}
