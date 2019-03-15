package calculator_tdd;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringAdderTest {
    @Test
    public void 쉼표_덧셈() {
        // given
        String expression = "1,2,3,4";
        
        // when
        int result = StringAdder.calculate(expression);
        
        // then
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void 콜론_덧셈() {
        // given
        String expression = "1:2:4";

        // when
        int result = StringAdder.calculate(expression);

        // then
        assertThat(result).isEqualTo(7);
    }


    @Test
    public void 쉼표_콜론_복합_덧셈() {
        // given
        String expression = "1,2:3,4:5";

        // when
        int result = StringAdder.calculate(expression);

        // then
        assertThat(result).isEqualTo(15);
    }

    @Test
    public void 커스텀_구분자_덧셈() {
        // given
        String expression = "//;\n1;2;3";

        // when
        int result = StringAdder.calculate(expression);

        // then
        assertThat(result).isEqualTo(6);
    }
}
