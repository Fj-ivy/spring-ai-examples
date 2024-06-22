package org.ivy.func;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.function.Function;

public class MockWeatherService implements Function<MockWeatherService.Request, MockWeatherService.Response> {

    /**
     * Weather Function request.
     */
    @JsonInclude(Include.NON_NULL)
    @JsonClassDescription("Weather API request")
    public record Request(@JsonProperty(required = true, value = "location") @JsonPropertyDescription("The city and state e.g. 北京") String location,
                          @JsonProperty(required = true, value = "unit") @JsonPropertyDescription("Temperature unit") Unit unit) {
    }

    /**
     * Temperature units.
     */
    public enum Unit {
        C, F
    }

    /**
     * Weather Function response.
     */
    public record Response(double temp, Unit unit) {
    }

    @Override
    public Response apply(Request request) {
        System.out.println("function called :" + request);
        double temperature = 0;
        if (request.location().contains("北京")) {
            temperature = 15;
        } else if (request.location().contains("天津")) {
            temperature = 10;
        } else if (request.location().contains("南京")) {
            temperature = 30;
        }
        return new Response(temperature, Unit.C);
    }

}