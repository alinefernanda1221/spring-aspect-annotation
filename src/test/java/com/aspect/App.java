package com.aspect;

import com.aspect.model.SampleObject;
import com.aspect.service.SampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(AspectApplication.class, args);

        SampleService sampleService = context.getBean(SampleService.class);

        //simple calls to evaluate results :)
        sampleService.methodWithSimpleParam("value");

        sampleService.methodWithMultipleParam("", "otherValueParam");

        SampleObject sampleObject = new SampleObject("sample object name", 10);

        sampleService.methodWithObjectParam("", sampleObject);

        sampleService.methodWithObjectParam(sampleObject);
    }
}
