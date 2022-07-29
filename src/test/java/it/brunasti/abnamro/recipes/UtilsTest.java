package it.brunasti.abnamro.recipes;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

public class UtilsTest {

    @Test
    public void extractTokenTest() {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(AUTHORIZATION, "Bearer TOKEN");
        String res = Utils.extractToken(httpServletRequest);
        assert(res.equals("TOKEN"));
    }

    @Test
    public void arrayDumpTest() {
        String[] array = new String[3];
        array[0] = "A";
        array[1] = "B";
        array[2] = "C";

        String res = Utils.arrayDump(array);
        assert(res.equals("[<A>,<B>,<C>]"));
    }

}
