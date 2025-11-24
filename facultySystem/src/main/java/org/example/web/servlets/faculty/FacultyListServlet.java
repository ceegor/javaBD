package org.example.web.servlets.faculty;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/faculties")
public class FacultyListServlet extends HttpServlet {
    private final FacultyService service = FacultyServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = trim(req.getParameter("q"));
        String sort = trim(req.getParameter("sort"));
        boolean asc = !"false".equalsIgnoreCase(req.getParameter("asc"));

        int size = parseInt(req.getParameter("size"), 10);
        int page = parseInt(req.getParameter("page"), 1);
        if (page < 1) page = 1;
        if (size <= 0) size = 10;
        int offset = (page - 1) * size;

        List<Faculty> data = service.findPaged(q, size, offset, sort, asc);
        int total = service.count(q);
        int pages = (int) Math.ceil(total / (double) size);

        req.setAttribute("faculties", data);
        req.setAttribute("q", q);
        req.setAttribute("sort", sort == null ? "id" : sort);
        req.setAttribute("asc", asc);
        req.setAttribute("size", size);
        req.setAttribute("page", page);
        req.setAttribute("pages", pages);
        req.setAttribute("total", total);

        req.getRequestDispatcher("/WEB-INF/jsp/faculty/list.jsp").forward(req, resp);

    }

    private static int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
}
