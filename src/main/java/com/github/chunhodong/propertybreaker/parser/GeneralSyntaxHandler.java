package com.github.chunhodong.propertybreaker.parser;

import com.github.chunhodong.propertybreaker.enums.ParserConstant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneralSyntaxHandler extends AbstractSyntaxHandler{


    public GeneralSyntaxHandler(ParserHandler parserHandler) {
        super(parserHandler);
    }

    @Override
    public Map<String, String> handle(Map<String, Object> src, Map<String, String> desc) {
        Map<String,Object> generalPropertyMap = src.entrySet()
                .stream()
                .filter(entry -> entry.getKey().indexOf(ParserConstant.GENERAL.getProperty()) != -1)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        if(generalPropertyMap.isEmpty())return super.handle(src,desc);;

        String key = parseGeneralSyntaxField(src,".key");
        String value = parseGeneralSyntaxField(src,".value");
        desc.put(key,value);
        return super.handle(src, desc);
    }

    private static String parseGeneralSyntaxField(Map<String, Object> srcMap,String keyword){
        List<String> keys =  srcMap.keySet()
                .stream()
                .filter(s -> s.equals(ParserConstant.GENERAL.getProperty().concat(keyword)))
                .collect(Collectors.toList());
        if(keys.size() != 1)throw new IllegalArgumentException("only one ".concat(keyword).concat(" is allowed"));
        return srcMap.get(keys.get(0)).toString();



    }
}
