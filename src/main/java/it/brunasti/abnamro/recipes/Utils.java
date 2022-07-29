package it.brunasti.abnamro.recipes;

import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;

public class Utils {

    public static synchronized String extractToken(HttpServletRequest request) {
        final String param = ofNullable(request.getHeader(AUTHORIZATION)).orElse(request.getParameter("t"));
        return ofNullable(param).map(value -> removeStart(value, "Bearer"))
                .map(String::trim).orElseThrow(() -> new BadCredentialsException("No Token Found!"));
    }

    public static String arrayDump(String[] array) {
        StringBuilder res = new StringBuilder("--");
        String comma = "<";
        if (array != null) {
            res = new StringBuilder("[");
            for (String s : array) {
                res.append(comma).append(s);
                comma = ">,<";
            }
            res.append(">]");
        }
        return res.toString();
    }
}
