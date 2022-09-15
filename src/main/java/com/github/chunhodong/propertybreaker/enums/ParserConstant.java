package com.github.chunhodong.propertybreaker.enums;

public enum ParserConstant {
    PREFIX("chunhodong.property-breaker"),
    HIBERNATE("hibernate-ddlauto-deactive"),
    GENERAL("general-property-deactive");

    private String value;
    ParserConstant(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public String getProperty(){
        return PREFIX.value.concat(".").concat(value);

    }
}
