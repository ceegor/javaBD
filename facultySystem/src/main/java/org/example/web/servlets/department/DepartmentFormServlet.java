package org.example.web.servlets.department;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Department;
import org.example.entities.Faculty;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet({"/department/new", "/department/edit"})
public class DepartmentFormServlet extends HttpServlet {
    private final DepartmentService departmentService = DepartmentServiceImpl.getInstance();
    private final FacultyService facultyService = FacultyServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            int id = Integer.parseInt(idStr);
            Department d = departmentService.getById(id);
            req.setAttribute("department", d);
        }

        List<Faculty> faculties = facultyService.getAll();
        req.setAttribute("faculties", faculties);

        req.getRequestDispatcher("/WEB-INF/jsp/department/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String facultyStr = req.getParameter("facultyId");

        Integer facultyId = parseIntOrNull(facultyStr);

        if (name == null || name.isBlank() || facultyId == null || facultyId <= 0) {
            req.setAttribute("error", "Название и факультет обязательны");
            Department d = new Department();
            d.setName(name);
            if (facultyId != null) d.setFacultyId(facultyId);
            int id = parseIntOrZero(idStr);
            if (id > 0) d.setId(id);
            req.setAttribute("department", d);

            req.setAttribute("faculties", facultyService.getAll());

            req.getRequestDispatcher("/WEB-INF/jsp/department/form.jsp").forward(req, resp);
            return;
        }
        Department d = new Department();
        d.setName(name);
        d.setFacultyId(facultyId);
        int id = parseIntOrZero(idStr);
        if (id > 0) {
            departmentService.update(id, d);
        } else {
            departmentService.add(d);
        }

        resp.sendRedirect(req.getContextPath() + "/departments");

    }
    private int parseIntOrZero(String s) {
        try {
            return (s == null || s.isBlank()) ? 0 : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Integer parseIntOrNull(String s) {
        try {
            if (s == null || s.isBlank()) return null;
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
