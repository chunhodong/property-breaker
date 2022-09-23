package com.github.chunhodong.propertybreaker.parser;

import java.util.Map;

public class HibernateSyntaxHandler extends AbstractSyntaxHandler{


    public HibernateSyntaxHandler(ParserHandler parserHandler) {
        super(parserHandler);
    }

    @Override
    public Map<String, String> handle(Map<String, Object> src, Map<String, String> desc) {
        return super.handle(src, desc);
    }
}