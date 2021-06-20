package com.codetogether.openstudio.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

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
