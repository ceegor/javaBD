package org.example.web.servlets.statistics;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Student;
import org.example.service.StudentService;
import org.example.service.StudentServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
    private final StudentService studentService = StudentServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int age = parseIntOrDefault(req.getParameter("age"), 18);

        req.setAttribute("byFaculty", studentService.getStudentCountByFaculty());
        req.setAttribute("byDepartment", studentService.getStudentCountByDepartment());
        req.setAttribute("byGroup", studentService.getStudentCountByGroup());
        req.setAttribute("byYear", studentService.getStudentCountByAdmissionYear());

        List<Student> olderStudents   = studentService.findStudentsOlderThan(age);
        List<Student>    youngerStudents = studentService.findStudentsYoungerThan(age);

        req.setAttribute("age", age);
        req.setAttribute("olderStudents", olderStudents);
        req.setAttribute("youngerStudents", youngerStudents);

        req.setAttribute("olderCount", olderStudents.size());
        req.setAttribute("youngerCount", youngerStudents.size());

        req.getRequestDispatcher("/WEB-INF/jsp/statistics/statistics.jsp").forward(req, resp);
    }

    private int parseIntOrDefault(String s, int def) {
        try {
            return (s == null || s.isBlank()) ? def : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
