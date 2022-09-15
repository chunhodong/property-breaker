package com.github.chunhodong.propertybreaker.parser;

import com.github.chunhodong.propertybreaker.enums.ParserConstant;
import org.springframework.boot.origin.OriginTrackedValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyParser {

    public static Map<String, String> parsePropertyMap(Map<String,Object> map){


        Map<String,Object> filteredMap = map.entrySet()
                .stream()
                .filter(e -> e.getKey().indexOf(ParserConstant.PREFIX.getValue()) != -1)
                .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

        if(filteredMap.isEmpty())return Collections.EMPTY_MAP;

        Map<String, String> result = new HashMap<>();
        parseHibernateSyntax(filteredMap,result);
        parseGeneralSyntax(filteredMap,result);

        return result;
    }

    private static void parseHibernateSyntax( Map<String,Object> srcMap,Map<String, String> descMap){
        OriginTrackedValue originTrackedValue = OriginTrackedValue.of(srcMap.get(ParserConstant.HIBERNATE.getProperty()));

        if(originTrackedValue == null)return;
        if(originTrackedValue.getValue().toString() != Boolean.TRUE.toString())return;
        descMap.put("spring.jpa.hibernate.ddl-auto","create");
    }

    private static void parseGeneralSyntax( Map<String,Object> srcMap,Map<String, String> descMap){
        Map<String,Object> generalPropertyMap = srcMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().indexOf(ParserConstant.GENERAL.getProperty()) != -1)
                .collect(Collectors.toMap(entry -> entry.getKey(),entry -> entry.getValue()));
        if(generalPropertyMap.isEmpty())return;

        String key = parseGeneralSyntaxField(srcMap,".key");
        String value = parseGeneralSyntaxField(srcMap,".value");

        if(key == null || value == null)return;
        descMap.put(key,value);

    }

    private static String parseGeneralSyntaxField(Map<String, Object> srcMap,String keyword){
        List<String> keys =  srcMap.keySet()
                .stream()
                .filter(s -> s.equals(ParserConstant.GENERAL.getProperty().concat(keyword)))
                .collect(Collectors.toList());
        if(keys.size() > 1)throw new IllegalArgumentException("duplicate general key");
        return srcMap.get(keys.get(0)).toString();


    }


}
