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

@WebServlet("/departments")
public class DepartmentListServlet extends HttpServlet {
    private final DepartmentService departmentService = DepartmentServiceImpl.getInstance();
    private final FacultyService facultyService = FacultyServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = trim(req.getParameter("q"));
        Integer facultyId = parseIntOrNull(req.getParameter("facultyId"));

        String sort = trim(req.getParameter("sort"));
        if (sort == null || sort.isBlank()) sort = "id";

        boolean asc = !"false".equalsIgnoreCase(req.getParameter("asc"));

        int size = parseIntOrDefault(req.getParameter("size"), 10);
        if (size <= 0) size = 10;
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        if (page < 1) page = 1;
        int offset = (page - 1) * size;

        List<Department> departments = departmentService.findPaged(q, facultyId, size, offset, sort, asc);

        int total = departmentService.count(q, facultyId);
        int pages = (int) Math.ceil(total / (double) size);

        List<Faculty> faculties = facultyService.getAll();

        req.setAttribute("departments", departments);
        req.setAttribute("faculties", faculties);

        req.setAttribute("q", q);
        req.setAttribute("facultyId", facultyId);
        req.setAttribute("sort", sort);
        req.setAttribute("asc", asc);
        req.setAttribute("size", size);
        req.setAttribute("page", page);
        req.setAttribute("pages", pages);
        req.setAttribute("total", total);

        req.getRequestDispatcher("WEB-INF/jsp/department/list.jsp").forward(req, resp);
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static int parseIntOrDefault(String s, int def) {
        try {
            return (s == null || s.isBlank()) ? def : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static Integer parseIntOrNull(String s) {
        try {
            if (s == null || s.isBlank()) return null;
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
