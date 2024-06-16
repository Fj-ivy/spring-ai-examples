package org.ivy.config;

import org.ivy.func.MockWeatherService;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public FunctionCallback weatherFunctionInfo() {

        return FunctionCallbackWrapper.builder(new MockWeatherService())
                .withName("WeatherInfo")
                .withDescription("获取城市的天气")
                .withResponseConverter((response) -> "" + response.temp() + response.unit())
                .build();
    }
}
