package org.example.repository;

import org.example.database.Database;
import org.example.entities.Department;
import org.example.entities.Group;
import org.example.entities.Student;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {
    private static GroupRepositoryImpl instance;

    private GroupRepositoryImpl() {}

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Group> getGroupsByDepartment(int departmentId) {
        String sql = baseSelect() + " WHERE department_id=? ORDER BY group_id";
        List<Group> groups = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) groups.add(map(rs));}
        } catch (SQLException e) { throw new RuntimeException(e);}
        return groups;
    }

    @Override
    public Group getGroupByName(String name) {
        String sql = baseSelect() + " WHERE name=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Group> getGroupsByYear(short year) {
        String sql = baseSelect() + " WHERE year=? ORDER BY group_id";
        List<Group> groups = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setShort(1, year);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) groups.add(map(rs));}
        } catch (SQLException e) { throw new RuntimeException(e);}
        return groups;
    }

    @Override
    public void add(Group group) {
        String sql = """
                INSERT INTO student_group(name, year, department_id)
                VALUES (?, ?, ?)
                RETURNING group_id
                """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, group.getName());
            ps.setShort(2, group.getYear());
            ps.setInt(3, group.getDepartmentId());
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) group.setId(rs.getInt(1)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Group> getAll() {
        String sql = baseSelect() + " ORDER BY group_id";
        List<Group> groups = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) groups.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return groups;
    }

    @Override
    public Group getById(int id) {
        String sql = baseSelect() + " WHERE group_id=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void removeAll() {
        try (Connection c = Database.open(); var st = c.createStatement()) {
            st.executeUpdate("DELETE FROM student_group");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(int id, Group group) {
        String sql = """
            UPDATE student_group
               SET name=?, year=?, department_id=?
             WHERE group_id=?
        """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, group.getName());
            ps.setShort(2, group.getYear());
            ps.setInt(3, group.getDepartmentId());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteGroupById(int id) {
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement("DELETE FROM student_group WHERE group_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Group> findPaged(String nameLike, Integer departmentId, Short year,
                                 int limit, int offset, String sortBy, boolean asc) {
        String order = switch (sortBy == null ? "" : sortBy) {
            case "name" -> "g.name";
            case "year" -> "g.year";
            case "department" -> "g.department_id";
            default -> "g.group_id";
        };
        String dir = asc ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
        SELECT g.group_id, g.name, g.year, g.department_id
          FROM student_group g
         WHERE 1=1
    """);
        List<Object> args = new ArrayList<>();
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND g.name ILIKE ? ");
            args.add("%" + nameLike + "%");
        }
        if (departmentId != null) {
            sql.append(" AND g.department_id = ? ");
            args.add(departmentId);
        }
        if (year != null) {
            sql.append(" AND g.year = ? ");
            args.add(year);
        }
        sql.append(" ORDER BY ").append(order).append(" ").append(dir)
                .append(" LIMIT ? OFFSET ?");
        args.add(limit); args.add(offset);

        List<Group> out = new ArrayList<>();
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
    public int count(String nameLike, Integer departmentId, Short year) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM student_group g WHERE 1=1");
        List<Object> args = new ArrayList<>();
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND g.name ILIKE ? ");
            args.add("%" + nameLike + "%");
        }
        if (departmentId != null) { sql.append(" AND g.department_id = ? "); args.add(departmentId); }
        if (year != null) { sql.append(" AND g.year = ? "); args.add(year); }
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < args.size(); i++) ps.setObject(i + 1, args.get(i));
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int countByDepartmentId(int departmentId) {
        String sql = "SELECT COUNT(*) FROM student_group WHERE department_id = ?";
        try (Connection c = Database.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static String baseSelect() {
        return """
            SELECT group_id, name, year, department_id
              FROM student_group
        """;
    }

    private static Group map(ResultSet rs) throws SQLException {
        Group g = new Group();
        g.setId(rs.getInt("group_id"));
        g.setName(rs.getString("name"));
        g.setYear(rs.getShort("year"));
        g.setDepartmentId(rs.getInt("department_id"));
        return g;
    }
}
