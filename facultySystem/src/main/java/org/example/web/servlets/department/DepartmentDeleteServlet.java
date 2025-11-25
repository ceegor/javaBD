package org.example.web.servlets.department;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

import java.io.IOException;

@WebServlet("/department/delete")
public class DepartmentDeleteServlet extends HttpServlet {
    private final DepartmentService departmentService = DepartmentServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        try {
            if (idStr != null && !idStr.isBlank()) {
                int id = Integer.parseInt(idStr);
                departmentService.delete(id);
            }
        } catch (NumberFormatException ignored) {

        }
        resp.sendRedirect(req.getContextPath() + "/departments");
    }
}
