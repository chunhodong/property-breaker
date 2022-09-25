package com.github.chunhodong.propertybreaker.listener;

import com.github.chunhodong.propertybreaker.parser.AbstractSyntaxHandler;
import com.github.chunhodong.propertybreaker.parser.GeneralSyntaxHandler;
import com.github.chunhodong.propertybreaker.parser.HibernateSyntaxHandler;
import com.github.chunhodong.propertybreaker.parser.PropertyParser;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Collections;
import java.util.Map;

public class EnvironmentPrepareListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        //check environment value
        MutablePropertySources environmentPropertySources = environment.getPropertySources();
        if(environmentPropertySources == null || environmentPropertySources.size() == 0)return;

        //check if user-defined values in .yml or .properties file
        Map<String,Object> definedPropertyMap = getDefinedPropertyMap(environment);
        if(definedPropertyMap.isEmpty())return;


        //get the property value to detect in the property-breaker.
        AbstractSyntaxHandler handler = new GeneralSyntaxHandler(new HibernateSyntaxHandler(null));
        Map<String, String> detectedPropertyMap = PropertyParser.parsePropertyMap(definedPropertyMap,handler);

        if(detectedPropertyMap.isEmpty())return;



        //find a property value in the .yml or .properties file and raises an exception if it matches
        validatePropertyValues(detectedPropertyMap,definedPropertyMap);


    }

    private Map getDefinedPropertyMap(ConfigurableEnvironment environment){

        return environment.getPropertySources()
                .stream()
                .filter(propertySource -> propertySource instanceof OriginTrackedMapPropertySource)
                .findFirst()
                .map(propertySource -> (Map)propertySource.getSource())
                .orElse(Collections.EMPTY_MAP);


    }

    private void validatePropertyValues( Map<String, String> detectedPropertyMap, Map<String,Object> envPropertyMap){
        detectedPropertyMap.entrySet().stream().forEach(entry -> {
            Object propertyValue = envPropertyMap.get(entry.getKey());
            if(propertyValue != null){
                if(entry.getValue().equals(propertyValue.toString()))
                    throw new IllegalArgumentException("not allowed property value [".concat(entry.getKey().concat(":").concat(propertyValue.toString()).concat("]")));
            }
        });

    }

}
