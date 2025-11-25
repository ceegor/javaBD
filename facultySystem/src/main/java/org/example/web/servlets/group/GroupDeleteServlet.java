package org.example.web.servlets.group;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.GroupService;
import org.example.service.GroupServiceImpl;

import java.io.IOException;

@WebServlet("/group/delete")
public class GroupDeleteServlet extends HttpServlet {
    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        try {
            if (idStr != null && !idStr.isBlank()) {
                int id = Integer.parseInt(idStr);
                groupService.delete(id);
            }
        } catch (NumberFormatException ignored) {}
        resp.sendRedirect(req.getContextPath() + "/groups");
    }

}
