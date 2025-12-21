package org.example.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class CsrfFilter implements Filter {

    public static final String CSRF_TOKEN_ATTR = "CSRF_TOKEN";

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);
        String token = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute(CSRF_TOKEN_ATTR, token);
        }

        String method = req.getMethod();
        boolean unsafe = "POST".equalsIgnoreCase(method)
                || "PUT".equalsIgnoreCase(method)
                || "DELETE".equalsIgnoreCase(method);

        if (unsafe) {
            String requestToken = req.getParameter("_csrf");
            if (requestToken == null || !requestToken.equals(token)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "CSRF token invalid or missing");
                return;
            }
        }

        chain.doFilter(req, resp);
    }
}
