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
        String key = parseGeneralSyntaxField(src,getGeneralPropertyKey());
        String value = parseGeneralSyntaxField(src,getGeneralPropertyValue());
        if(key != null && value != null)
            desc.put(key,value);
        return super.handle(src, desc);
    }

    private String parseGeneralSyntaxField(Map<String, Object> srcMap,String keyword){
        List<String> keys =  srcMap.keySet()
                .stream()
                .filter(s -> s.equals(keyword))
                .collect(Collectors.toList());
        if(keys.size() == 0) return null;
        if(keys.size() > 1)throw new IllegalArgumentException("only one ".concat(keyword).concat(" is allowed"));
        return srcMap.get(keys.get(0)).toString();

    }
    private String getGeneralPropertyKey(){
        return ParserConstant.PREFIX.getValue().concat(".").concat("general-property-deactive").concat(".key");
    }
    private String getGeneralPropertyValue(){
        return ParserConstant.PREFIX.getValue().concat(".").concat("general-property-deactive").concat(".value");
    }

}
