package org.example.web.servlets.faculty;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.io.IOException;

@WebServlet({"/faculty/new", "/faculty/edit"})
public class FacultyFormServlet extends HttpServlet {
    private final FacultyService facultyService = FacultyServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            int id = Integer.parseInt(idStr);
            Faculty f = facultyService.getById(id);
            req.setAttribute("faculty", f);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/faculty/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String dean = req.getParameter("dean");

        if (name == null || name.isBlank()) {
            req.setAttribute("error", "Название факультета обязательно");
            Faculty f = new Faculty();
            f.setName(name);
            f.setDean(dean);
            int id = parseIntOrZero(idStr);
            if (id > 0) f.setId(id);
            req.setAttribute("faculty", f);
            req.getRequestDispatcher("WEB-INF/jsp/faculty/form.jsp").forward(req, resp);
            return;
        }

        Faculty f = new Faculty();
        f.setName(name.trim());
        f.setDean(dean == null ? null : dean.trim());
        int id = parseIntOrZero(idStr);
        if (id > 0) {
            facultyService.update(id, f);
        } else {
            facultyService.add(f);
        }

        resp.sendRedirect(req.getContextPath() + "/faculties");
    }

    private int parseIntOrZero(String s) {
        try {
            return (s == null || s.isBlank()) ? 0 : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
