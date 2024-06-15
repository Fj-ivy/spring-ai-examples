package com.ivy;

import org.junit.jupiter.api.Test;
import org.springframework.ai.converter.BeanOutputConverter;

public class BeanOutputConverterTest {

    @Test
    public void testFormat() {
        BeanOutputConverter<User> converter = new BeanOutputConverter<>(User.class);
        String format = converter.getFormat();
        System.out.println(format);
    }


    public static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
