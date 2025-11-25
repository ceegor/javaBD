package org.example.web.servlets.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Student;
import org.example.service.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentListServlet extends HttpServlet {
    private final StudentService studentService = StudentServiceImpl.getInstance();
    private final GroupService groupService = GroupServiceImpl.getInstance();
    private final DepartmentService departmentService = DepartmentServiceImpl.getInstance();
    private final FacultyService facultyService = FacultyServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String lastName = trim(req.getParameter("lastName"));

        Integer groupId      = parseIntOrNull(req.getParameter("groupId"));
        Integer departmentId = parseIntOrNull(req.getParameter("departmentId"));
        Integer facultyId    = parseIntOrNull(req.getParameter("facultyId"));

        String sort = trim(req.getParameter("sort"));
        if (sort == null || sort.isBlank()) sort = "student_id";

        boolean asc = !"false".equalsIgnoreCase(req.getParameter("asc"));

        int size = parseIntOrDefault(req.getParameter("size"), 20);
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int offset = (page - 1) * size;

        List<Student> students =
                studentService.findPaged(lastName, groupId, departmentId, facultyId,
                        size, offset, sort, asc);

        int total =
                studentService.count(lastName, groupId, departmentId, facultyId);

        int pages = (int) Math.ceil(total / (double) size);

        req.setAttribute("groups",      groupService.getAll());
        req.setAttribute("departments", departmentService.getAll());
        req.setAttribute("faculties",   facultyService.getAll());

        req.setAttribute("students", students);

        req.setAttribute("lastName", lastName);
        req.setAttribute("groupId", groupId);
        req.setAttribute("departmentId", departmentId);
        req.setAttribute("facultyId", facultyId);

        req.setAttribute("sort", sort);
        req.setAttribute("asc", asc);
        req.setAttribute("size", size);
        req.setAttribute("page", page);
        req.setAttribute("pages", pages);
        req.setAttribute("total", total);

        req.getRequestDispatcher("/WEB-INF/jsp/student/list.jsp")
                .forward(req, resp);
    }

    private static Integer parseIntOrNull(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int parseIntOrDefault(String s, int def) {
        try {
            return (s == null || s.isBlank()) ? def : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
}
