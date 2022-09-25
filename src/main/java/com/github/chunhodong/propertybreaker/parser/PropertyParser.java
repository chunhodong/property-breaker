package com.github.chunhodong.propertybreaker.parser;

import com.github.chunhodong.propertybreaker.enums.ParserConstant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyParser {

    public static Map<String, String> parsePropertyMap(Map<String,Object> map, ParserHandler parserHandler){

        Map<String,Object> filteredMap = filterPropertyMap(map);
        if(filteredMap.isEmpty())return Collections.EMPTY_MAP;

        return parserHandler.handle(filteredMap,new HashMap<>());

    }

    private static Map<String,Object> filterPropertyMap(Map<String,Object> map){
        return map.entrySet()
                .stream()
                .filter(e -> e.getKey().indexOf(ParserConstant.PREFIX.getValue()) != -1)
                .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
    }



}
