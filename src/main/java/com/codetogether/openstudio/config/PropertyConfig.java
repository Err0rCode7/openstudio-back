package com.codetogether.openstudio.config;

import com.codetogether.openstudio.properties.CadetProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {CadetProperty.class})
public class PropertyConfig {
}
