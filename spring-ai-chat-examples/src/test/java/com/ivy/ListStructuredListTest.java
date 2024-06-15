package com.ivy;

import org.junit.jupiter.api.Test;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.List;

public class ListStructuredListTest {

    @Test
    public void testListStructuredList() {

        ListOutputConverter converter = new ListOutputConverter(new DefaultConversionService());
        List<String> strings = converter.convert("one,two,three");

        System.out.println(strings);
    }
}
