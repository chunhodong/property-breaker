package com.github.chunhodong.propertybreaker.parser;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PropertyParserTest {

    @Test
    void 필드조회_빈값입력하면_빈값리턴(){

        Map map = PropertyParser.parsePropertyMap(Collections.EMPTY_MAP,null);

        assertThat(map.isEmpty()).isEqualTo(true);
    }

    @Test
    void 필드조회_hiberndate_ddlauto_감지필드없으면_빈값리턴(){

        Map map = PropertyParser.parsePropertyMap(Map.of("spring.jpa.hibernate.ddl-auto","create"));

        assertThat(map.isEmpty()).isEqualTo(true);
    }

    @Test
    void 필드조회_hiberndate_ddlauto_감지필드false면_빈값리턴(){

        Map propertyMap = Map.of("chunhodong.property-breaker.hibernate-ddlauto-deactive","false");
        Map map = PropertyParser.parsePropertyMap(propertyMap);

        assertThat(map.isEmpty()).isEqualTo(true);
    }

    @Test
    void 필드조회_hiberndate_ddlauto_감지필드true면_프로퍼티리턴(){

        Map propertyMap = Map.of("chunhodong.property-breaker.hibernate-ddlauto-deactive","true");
        Map map = PropertyParser.parsePropertyMap(propertyMap);
        assertThat(map.get("spring.jpa.hibernate.ddl-auto")).isNotNull();
        assertThat(map.get("spring.jpa.hibernate.ddl-auto")).isEqualTo("create");

    }


}
