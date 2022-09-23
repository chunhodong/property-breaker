package com.github.chunhodong.propertybreaker.parser;

import java.util.Map;

public interface ParserHandler {
    Map<String,String> handle(Map<String,Object> src,Map<String,String> desc);
}
