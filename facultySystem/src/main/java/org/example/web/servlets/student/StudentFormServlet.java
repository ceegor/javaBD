package org.example.web.servlets.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Student;
import org.example.service.GroupService;
import org.example.service.GroupServiceImpl;
import org.example.service.StudentService;
import org.example.service.StudentServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/student/edit")
public class StudentFormServlet extends HttpServlet {
    private final StudentService studentService = StudentServiceImpl.getInstance();
    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        Student student = null;

        if (idParam != null && !idParam.isBlank()) {
            try {
                int id = Integer.parseInt(idParam);
                student = studentService.getById(id);
            } catch (NumberFormatException ignored) { }
        }

        req.setAttribute("student", student);
        req.setAttribute("groups", groupService.getAll());

        req.getRequestDispatcher("/WEB-INF/jsp/student/form.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String idParam        = req.getParameter("id");
        String firstName      = req.getParameter("firstName");
        String lastName       = req.getParameter("lastName");
        String patronymic     = req.getParameter("patronymic");
        String dobStr         = req.getParameter("dateOfBirth");
        String gender         = req.getParameter("gender");
        String admissionYearStr = req.getParameter("admissionYear");
        String studentCode    = req.getParameter("studentCode");
        String email          = req.getParameter("email");
        String phone          = req.getParameter("phone");
        String groupIdStr     = req.getParameter("groupId");

        Map<String,String> errors = new LinkedHashMap<>();

        if (firstName == null || firstName.isBlank())
            errors.put("firstName", "Имя обязательно");

        if (lastName == null || lastName.isBlank())
            errors.put("lastName", "Фамилия обязательна");

        Integer admissionYear = null;
        if (admissionYearStr == null || admissionYearStr.isBlank()) {
            errors.put("admissionYear", "Год поступления обязателен");
        } else {
            try {
                admissionYear = Integer.parseInt(admissionYearStr);
            } catch (NumberFormatException e) {
                errors.put("admissionYear", "Некорректный год поступления");
            }
        }

        Integer groupId = null;
        if (groupIdStr == null || groupIdStr.isBlank()) {
            errors.put("groupId", "Нужно выбрать группу");
        } else {
            try {
                groupId = Integer.parseInt(groupIdStr);
                if (groupId <= 0) errors.put("groupId", "Некорректный id группы");
            } catch (NumberFormatException e) {
                errors.put("groupId", "Некорректный id группы");
            }
        }

        LocalDate dob = null;
        if (dobStr != null && !dobStr.isBlank()) {
            try {
                dob = LocalDate.parse(dobStr);
            } catch (DateTimeParseException e) {
                errors.put("dateOfBirth", "Некорректная дата");
            }
        }

        if (studentCode == null || studentCode.isBlank()) {
            errors.put("studentCode", "Код студента обязателен");
        } else if (idParam == null) {
            if (studentService.getByStudentCode(studentCode) != null) {
                errors.put("studentCode", "Студент с таким кодом уже существует");
            }
        }

        if (email != null && !email.isBlank() && idParam == null) {
            if (!studentService.isEmailUnique(email)) {
                errors.put("email", "Почта уже используется другим студентом");
            }
        }
        Student.Builder builder = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patronymic(patronymic)
                .dateOfBirth(dob)
                .gender(gender)
                .studentCode(studentCode)
                .email(email)
                .phone(phone);

        if (admissionYear != null) builder.admissionYear(admissionYear);
        if (groupId != null)       builder.groupId(groupId);

        Student student;
        try {
            student = builder.build();
        } catch (IllegalArgumentException e) {
            errors.put("common", e.getMessage());
            student = null;
        }

        Integer id = null;
        if (idParam != null && !idParam.isBlank()) {
            try { id = Integer.parseInt(idParam); } catch (NumberFormatException ignored) {}
        }

        if (!errors.isEmpty() || student == null) {
            req.setAttribute("errors", errors);
            req.setAttribute("student", student);
            req.setAttribute("groups", groupService.getAll());
            req.getRequestDispatcher("/WEB-INF/jsp/student/form.jsp")
                    .forward(req, resp);
            return;
        }

        if (id == null) {
            studentService.add(student);
        } else {
            studentService.update(id, student);
        }

        resp.sendRedirect(req.getContextPath() + "/students");
    }
}
