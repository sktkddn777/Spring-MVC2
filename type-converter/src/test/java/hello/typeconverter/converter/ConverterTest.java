package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ConverterTest {

    @Test
    void stringToInteger() {
        // given
        StringToIntegerConverter converter = new StringToIntegerConverter();

        // when
        Integer convertNum = converter.convert("10");

        // then
        assertThat(convertNum).isEqualTo(10);
    }

    @Test
    void integerToString() {
        // given
        IntegerToStringConverter converter = new IntegerToStringConverter();

        // when
        String convertNum = converter.convert(10);

        // then
        assertThat(convertNum).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1", 8080);
        String result = converter.convert(source);
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

    @Test
    void ipPortToString() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";
        IpPort result = converter.convert(source);
        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080)); // EqualsAndHashCode 때문에 참조값이 달라도 값만 같으면 같은걸루
    }
}