package org.example.web.servlets.faculty;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.io.IOException;

@WebServlet("/faculty/delete")
public class FacultyDeleteServlet extends HttpServlet {
    private final FacultyService facultyService = FacultyServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            int id = Integer.parseInt(idStr);
            facultyService.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/faculties");
    }
}
