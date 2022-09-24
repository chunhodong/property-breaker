package com.github.chunhodong.propertybreaker.parser;

import com.github.chunhodong.propertybreaker.enums.ParserConstant;
import java.util.Map;

public class HibernateSyntaxHandler extends AbstractSyntaxHandler{


    public HibernateSyntaxHandler(ParserHandler parserHandler) {
        super(parserHandler);
    }

    @Override
    public Map<String, String> handle(Map<String, Object> src, Map<String, String> desc) {

        Object value = src.get(ParserConstant.HIBERNATE.getProperty());

        if(value == null)return super.handle(src,desc);

        if(value.toString().equals(Boolean.TRUE.toString())){
            desc.put("spring.jpa.hibernate.ddl-auto","create");
        }
        return super.handle(src, desc);

    }


}
