package com.github.chunhodong.propertybreaker.listener;

import com.github.chunhodong.propertybreaker.parser.PropertyParser;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Collections;
import java.util.Map;

public class EnvironmentPrepareListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        if(environment.getPropertySources() == null)return;


        Map<String,Object> propertyMap = getPropertySource(environment);

        if(propertyMap.isEmpty())return;

        Map<String, String> detectedPropertyMap = PropertyParser.parsePropertyMap(propertyMap);

        if(detectedPropertyMap.isEmpty())return;

        detectedPropertyMap.entrySet().stream().forEach(entry -> {
            Object propertyValue = propertyMap.get(entry.getKey());
            if(propertyValue != null){
                if(entry.getValue().equals(propertyValue.toString()))
                    throw new IllegalArgumentException("not allowed property value");
            }
        });
    }

    private Map getPropertySource(ConfigurableEnvironment environment){
        MutablePropertySources mutablePropertySources = environment.getPropertySources();

        return environment.getPropertySources()
                .stream()
                .filter(propertySource -> propertySource instanceof OriginTrackedMapPropertySource)
                .findFirst()
                .map(propertySource -> (Map)propertySource.getSource())
                .orElse(Collections.EMPTY_MAP);


    }


}
