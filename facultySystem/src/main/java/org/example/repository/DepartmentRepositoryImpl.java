package org.example.repository;

import org.example.database.Database;
import org.example.entities.Department;
import org.example.entities.Faculty;
import org.example.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static DepartmentRepositoryImpl instance;

    private DepartmentRepositoryImpl() {}

    public static DepartmentRepository getInstance() {
        if (instance == null) {
            instance = new DepartmentRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Department> getDepartmentsByFaculty(int facultyId) {
        String sql = "SELECT department_id, name, faculty_id FROM department WHERE faculty_id=? ORDER BY department_id";
        var list = new ArrayList<Department>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public Department getDepartmentByName(String name) {
        String sql = "SELECT department_id, name, faculty_id FROM department WHERE name=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void add(Department d) {
        String sql = """
            INSERT INTO department(name, faculty_id)
            VALUES(?, ?) RETURNING department_id
        """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setInt(2, d.getFacultyId());
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) d.setId(rs.getInt(1)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Department> getAll() {
        String sql = "SELECT department_id, name, faculty_id FROM department ORDER BY department_id";
        ArrayList<Department> list = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public Department getById(int id) {
        String sql = "SELECT department_id, name, faculty_id FROM department WHERE department_id=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void removeAll() {
        try (Connection c = Database.open(); var st = c.createStatement()) {
            st.executeUpdate("DELETE FROM department");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(int id, Department d) {
        String sql = "UPDATE department SET name=?, faculty_id=? WHERE department_id=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setInt(2, d.getFacultyId());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteDepartmentById(int id) {
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement("DELETE FROM department WHERE department_id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Department> findPaged(String nameLike, Integer facultyId, int limit, int offset,
                                      String sortBy, boolean asc) {
        String order = switch (sortBy == null ? "" : sortBy) {
            case "name" -> "d.name";
            case "faculty" -> "d.faculty_id";
            default -> "d.department_id";
        };
        String dir = asc ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
        SELECT d.department_id, d.name, d.faculty_id
          FROM department d
         WHERE 1=1
    """);
        List<Object> args = new ArrayList<>();
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND d.name ILIKE ? ");
            args.add("%" + nameLike + "%");
        }
        if (facultyId != null) {
            sql.append(" AND d.faculty_id = ? ");
            args.add(facultyId);
        }
        sql.append(" ORDER BY ").append(order).append(" ").append(dir)
                .append(" LIMIT ? OFFSET ?");
        args.add(limit); args.add(offset);

        List<Department> out = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < args.size(); i++) ps.setObject(i + 1, args.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    out.add(map(rs));
            }
            return out;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int count(String nameLike, Integer facultyId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM department d WHERE 1=1");
        List<Object> args = new ArrayList<>();
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND d.name ILIKE ? ");
            args.add("%" + nameLike + "%");
        }
        if (facultyId != null) {
            sql.append(" AND d.faculty_id = ? ");
            args.add(facultyId);
        }
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < args.size(); i++) ps.setObject(i + 1, args.get(i));
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }


    private static Department map(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getInt("department_id"));
        d.setName(rs.getString("name"));
        d.setFacultyId(rs.getInt("faculty_id"));
        return d;
    }
}
