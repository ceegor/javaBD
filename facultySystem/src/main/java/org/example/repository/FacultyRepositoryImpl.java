package org.example.repository;

import org.example.database.Database;
import org.example.entities.Faculty;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FacultyRepositoryImpl implements FacultyRepository {
    private static FacultyRepositoryImpl instance;
    private final Map<Integer, Faculty> storage = new ConcurrentHashMap<>();

    private FacultyRepositoryImpl() {}

    public static FacultyRepository getInstance() {
        if (instance == null) {
            instance = new FacultyRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(Faculty faculty) {
        String sql = """
                INSERT INTO faculty(name,dean)
                VALUES (?,?) RETURNING faculty_id
                """;
        try (Connection c = Database.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, faculty.getName());
            ps.setString(2, faculty.getDean());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) faculty.setId(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Faculty getFacultyByDean(String dean) {
        String sql = "SELECT faculty_id, name, dean FROM faculty WHERE dean = ?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dean);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Faculty getFacultyByName(String name) {
        String sql = "SELECT faculty_id, name, dean FROM faculty WHERE name=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Faculty> getAll() {
        String sql = "SELECT faculty_id, name, dean FROM faculty ORDER BY faculty_id";
        List<Faculty> facultyList = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) facultyList.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return facultyList;
    }

    @Override
    public Faculty getById(int id) {
        String sql = "SELECT faculty_id, name, dean FROM faculty WHERE faculty_id=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void removeAll() {
        try (Connection c = Database.open(); var st = c.createStatement()) {
            st.executeQuery("DELETE FROM faculty");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(int id, Faculty faculty) {
        String sql = "UPDATE faculty SET name=?, dean=? WHERE faculty_id=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, faculty.getName());
            ps.setString(2, faculty.getDean());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteFacultyById(int id) {
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement("DELETE FROM faculty WHERE faculty_id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Faculty> findPaged(String nameLike, int limit, int offset, String sortBy, boolean asc) {
        String order = switch (sortBy == null ? "" : sortBy) {
            case "name" -> "name";
            case "dean" -> "dean";
            default -> "faculty_id";
        };
        String dir = asc ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
        SELECT faculty_id, name, dean
          FROM faculty
         WHERE 1=1
    """);
        List<Object> args = new ArrayList<>();
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND name ILIKE ? ");
            args.add("%" + nameLike + "%");
        }
        sql.append(" ORDER BY ").append(order).append(" ").append(dir)
                .append(" LIMIT ? OFFSET ?");

        args.add(limit);
        args.add(offset);

        List<Faculty> out = new ArrayList<>();
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
    public int count(String nameLike) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM faculty WHERE 1=1");
        List<Object> args = new ArrayList<>();
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND name ILIKE ?");
            args.add("%" + nameLike + "%");
        }
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < args.size(); i++) ps.setObject(i + 1, args.get(i));
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private static Faculty map(ResultSet rs) throws SQLException {
        Faculty f = new Faculty();
        f.setId(rs.getInt("faculty_id"));
        f.setName(rs.getString("name"));
        f.setDean(rs.getString("dean"));
        return f;
    }
}
