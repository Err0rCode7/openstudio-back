package com.codetogether.openstudio.properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CadetPropertyTest {
    @Autowired
    private CadetProperty cadetProperty;

    @Test
    @DisplayName("ConfigurationPropery test")
    void 프로펄티만들기테스트() {
        Integer cadetCount = cadetProperty.getCadetCount();
        assertThat(cadetCount).isEqualTo(900);
    }
}