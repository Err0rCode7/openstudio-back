package com.codetogether.openstudio.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "openstudio")
public class CadetProperty {

    private final Integer cadetCount;
    private final String surveyForm;

    public CadetProperty(Integer cadetCount, String surveyForm) {
        this.cadetCount = cadetCount;
        this.surveyForm = surveyForm;
    }
}
