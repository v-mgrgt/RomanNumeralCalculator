import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralConverterTest {
    private static RomanNumeralConverter romanNumeralConverter;

    @BeforeAll
    static void initialize() {
        romanNumeralConverter = new RomanNumeralConverter();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ArabicRoman.csv", delimiter = ';')
    void isTrueRomanNumber(int mock, String actual) {
        assertTrue(romanNumeralConverter.isCorrectRomanNumber(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {"IIII", "IIV", "IIX", "XIIII", "VXL", "IC", "CDM", "CLLX", "IMM"})
    void isFalseRomanNumber(String actual) {
        assertFalse(romanNumeralConverter.isCorrectRomanNumber(actual));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ArabicRoman.csv", delimiter = ';')
    void convertRomanNumberToArabicNumber(int expected, String actual) {
        assertEquals(expected, romanNumeralConverter.convertToArabicNumber(actual));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/RomanArabic.csv", delimiter = ';')
    void convertArabicNumberToRomanNumber(String expected, int actual) {
        assertEquals(expected, romanNumeralConverter.convertFromArabicNumber(actual));
    }
}
