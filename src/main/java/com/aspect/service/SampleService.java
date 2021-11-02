package com.aspect.service;

import com.aspect.annotation.FindValueByPropertyName;
import com.aspect.model.SampleObject;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @FindValueByPropertyName(propertyName = "value")
    public void methodWithSimpleParam(String value) {}

    @FindValueByPropertyName(propertyName = "otherValue")
    public void methodWithMultipleParam(String value, String otherValue) {}

    @FindValueByPropertyName(propertyName = "sampleObject.name")
    public void methodWithObjectParam(String otherValue, SampleObject sampleObject) {}

    @FindValueByPropertyName(propertyName = "sampleObject.intValue")
    public void methodWithObjectParam(SampleObject sampleObject) {}
}
