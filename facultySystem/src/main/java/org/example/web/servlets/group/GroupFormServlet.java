package org.example.web.servlets.group;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Department;
import org.example.entities.Group;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;
import org.example.service.GroupService;
import org.example.service.GroupServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet({"/group/new", "/group/edit"})
public class GroupFormServlet extends HttpServlet {
    private final GroupService groupService = GroupServiceImpl.getInstance();
    private final DepartmentService departmentService = DepartmentServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            int id = Integer.parseInt(idStr);
            Group g = groupService.getById(id);
            req.setAttribute("group", g);
        }

        List<Department> departments = departmentService.getAll();
        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/WEB-INF/jsp/group/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String yearStr = req.getParameter("year");
        String departmentStr = req.getParameter("departmentId");

        Short year = parseShortOrNull(yearStr);
        Integer departmentId = parseIntOrNull(departmentStr);

        if (name == null || name.isBlank() || year == null || departmentId == null || departmentId <= 0) {
            req.setAttribute("error", "Название, курс и кафедра обязательны");
            Group g = new Group();
            g.setName(name);
            if (year != null) g.setYear(year);
            if (departmentId != null) g.setDepartmentId(departmentId);
            int id = parseIntOrZero(idStr);
            if (id > 0) g.setId(id);
            req.setAttribute("group", g);

            req.setAttribute("departments", departmentService.getAll());
            req.getRequestDispatcher("/WEB-INF/jsp/group/form.jsp").forward(req, resp);
            return;
        }

        Group g = new Group();
        g.setName(name);
        g.setYear(year);
        g.setDepartmentId(departmentId);

        int id = parseIntOrZero(idStr);
        if (id > 0) {
            groupService.update(id, g);
        } else {
            groupService.add(g);
        }

        resp.sendRedirect(req.getContextPath() + "/groups");
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

    private Short parseShortOrNull(String s) {
        try {
            if (s == null || s.isBlank()) return null;
            return Short.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
