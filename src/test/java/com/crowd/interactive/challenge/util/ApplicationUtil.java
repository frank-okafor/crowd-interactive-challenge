package com.crowd.interactive.challenge.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.crowd.interactive.challenge.utils.ApplicationUtils;

class ApplicationUtil {

    @ParameterizedTest
    @CsvSource({ "frank.frank@gmail.com,true", "frank@gmail,false" })
    void testEmailValidator(String parameter, boolean expected) {
	boolean result = ApplicationUtils.isEmailValid(parameter);
	assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ "1234567899,true", "12345678765,false" })
    void testAccountNumberValidator(String parameter, boolean expected) {
	boolean result = ApplicationUtils.isAccountNumberValid(parameter);
	assertThat(result).isEqualTo(expected);
    }

}
