package nl.tsai.airosearch.csv;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {


    @SuppressWarnings("ConstantConditions")
    @Test
    void createDouble_whenNullString_returnNullValue() {
        String numberAsString = null;

        Double result = NumberUtils.createDouble(numberAsString);

        assertThat(result, is(nullValue()));
    }

    @Test
    void createDouble_whenEmptyString_returnNullValue() {
        String numberAsString = "";

        Double result = NumberUtils.createDouble(numberAsString);

        assertThat(result, is(nullValue()));
    }

    @Test
    void createDouble_whenNaN_throwNumberFormatException() {
        String numberAsString = "NotANumber";

        assertThrows(NumberFormatException.class, () -> NumberUtils.createDouble(numberAsString));
    }

    @Test
    void createDouble_whenValidDouble_returnActualDouble() {
        String numberAsString = "1.123";

        Double result = NumberUtils.createDouble(numberAsString);

        assertThat(result, is(1.123D));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void createFloat_whenNullString_returnNullValue() {
        String numberAsString = null;

        Float result = NumberUtils.createFloat(numberAsString);

        assertThat(result, is(nullValue()));
    }

    @Test
    void createFloat_whenEmptyString_returnNullValue() {
        String numberAsString = "";

        Float result = NumberUtils.createFloat(numberAsString);

        assertThat(result, is(nullValue()));
    }

    @Test
    void createFloat_whenNaN_throwNumberFormatException() {
        String numberAsString = "NotANumber";

        assertThrows(NumberFormatException.class, () -> NumberUtils.createFloat(numberAsString));
    }

    @Test
    void createFloat_whenValidDouble_returnActualDouble() {
        String numberAsString = "1.123";

        Float result = NumberUtils.createFloat(numberAsString);

        assertThat(result, is(1.123F));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void createInteger_whenNullString_returnNullValue() {
        String numberAsString = null;

        Integer result = NumberUtils.createInteger(numberAsString);

        assertThat(result, is(nullValue()));
    }

    @Test
    void createInteger_whenEmptyString_returnNullValue() {
        String numberAsString = "";

        Integer result = NumberUtils.createInteger(numberAsString);

        assertThat(result, is(nullValue()));
    }

    @Test
    void createInteger_whenNaN_throwNumberFormatException() {
        String numberAsString = "NotANumber";

        assertThrows(NumberFormatException.class, () -> NumberUtils.createInteger(numberAsString));
    }

    @Test
    void createInteger_whenValidDouble_returnActualDouble() {
        String numberAsString = "123";

        Integer result = NumberUtils.createInteger(numberAsString);

        assertThat(result, is(123));
    }
}