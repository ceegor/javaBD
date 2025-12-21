package org.example.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;

@WebFilter("/*")
public class InputSanitizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletRequest wrapped = new SanitizedRequest(req);
        chain.doFilter(wrapped, response);
    }

    private static class SanitizedRequest extends HttpServletRequestWrapper {

        public SanitizedRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return sanitize(value);
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) return null;
            String[] out = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                out[i] = sanitize(values[i]);
            }
            return out;
        }

        private String sanitize(String value) {
            if (value == null) return null;

            // Убираем <script>...</script> (простая защита от XSS)
            value = value.replaceAll("(?i)<script.*?>.*?</script>", "");

            // Доп. «подчистка»
            return value.trim();
        }
    }
}

