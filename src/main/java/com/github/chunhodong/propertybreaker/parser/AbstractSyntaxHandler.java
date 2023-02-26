package com.github.chunhodong.propertybreaker.parser;

import java.util.Map;

public class AbstractSyntaxHandler implements ParserHandler{

    private ParserHandler parserHandler;

    public AbstractSyntaxHandler(ParserHandler parserHandler){
        this.parserHandler = parserHandler;
    }

    @Override
    public Map<String, String> handle(Map<String, Object> src, Map<String, String> desc) {
        return this.parserHandler == null ? desc : this.parserHandler.handle(src,desc);
    }
}
