package org.example.repository;

import org.example.database.Database;
import org.example.entities.Student;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private static StudentRepositoryImpl instance;

    private StudentRepositoryImpl() {}

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Student> getStudentsByAdmissionYear(int year) {
        String sql = baseSelect() + " WHERE admission_year=? ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        try (Connection c  = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) students.add(map(rs)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return students;
    }

    @Override
    public List<Student> getStudentsByGroup(int groupId) {
        String sql = baseSelect() + " WHERE group_id=? ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        try (Connection c  = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) students.add(map(rs)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return students;
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        String sql = baseSelect() + " WHERE last_name=? ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        try (Connection c  = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, lastName);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) students.add(map(rs)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return students;
    }

    @Override
    public void add(Student student) {
        String sql = """
                INSERT INTO student(first_name, last_name, patronymic, date_of_birth, gender, 
                admission_year, student_code, email, phone, group_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING student_id
                """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getPatronymic());
            ps.setObject(4, student.getDateOfBirth());
            ps.setString(5, student.getGender());
            ps.setInt(6, student.getAdmissionYear());
            ps.setString(7, student.getStudentCode());
            ps.setString(8, student.getEmail());
            ps.setString(9, student.getPhone());
            ps.setInt(10, student.getGroupId());
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) student.setId(rs.getInt(1)); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Student> getAll() {
        String sql = baseSelect() + " ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) students.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return students;
    }

    @Override
    public Student getById(int id) {
        String sql = baseSelect() + " WHERE student_id=?";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void removeAll() {
        try (Connection c = Database.open(); var st = c.createStatement()) {
            st.executeUpdate("DELETE FROM student");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(int id, Student student) {
        String sql = """
            UPDATE student
               SET first_name=?, last_name=?, patronymic=?, date_of_birth=?, gender=?,
                   admission_year=?, student_code=?, email=?, phone=?, group_id=?
             WHERE student_id=?
        """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getPatronymic());
            ps.setObject(4, student.getDateOfBirth());
            ps.setString(5, student.getGender());
            ps.setInt(6, student.getAdmissionYear());
            ps.setString(7, student.getStudentCode());
            ps.setString(8, student.getEmail());
            ps.setString(9, student.getPhone());
            ps.setInt(10, student.getGroupId());
            ps.setInt(11, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteStudentById(int id) {
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement("DELETE FROM student WHERE student_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Student> findPaged(String lastNameLike, Integer groupId, Integer departmentId, Integer facultyId,
                                   int limit, int offset, String sortBy, boolean asc) {
        // Сортировка whitelisted
        String order = switch (sortBy == null ? "" : sortBy) {
            case "last_name" -> "s.last_name";
            case "first_name" -> "s.first_name";
            case "admission_year" -> "s.admission_year";
            case "group" -> "s.group_id";
            default -> "s.student_id";
        };
        String dir = asc ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
        SELECT s.student_id, s.first_name, s.last_name, s.patronymic,
               s.date_of_birth, s.gender, s.admission_year, s.student_code,
               s.email, s.phone, s.group_id
          FROM student s
          JOIN student_group g ON g.group_id = s.group_id
          JOIN department d    ON d.department_id = g.department_id
          JOIN faculty f       ON f.faculty_id = d.faculty_id
         WHERE 1=1
    """);
        List<Object> args = new ArrayList<>();

        if (lastNameLike != null && !lastNameLike.isBlank()) {
            sql.append(" AND s.last_name ILIKE ? ");
            args.add("%" + lastNameLike + "%");
        }
        if (groupId != null) {
            sql.append(" AND s.group_id = ? ");
            args.add(groupId);
        }
        if (departmentId != null) {
            sql.append(" AND d.department_id = ? ");
            args.add(departmentId);
        }
        if (facultyId != null) {
            sql.append(" AND f.faculty_id = ? ");
            args.add(facultyId);
        }

        sql.append(" ORDER BY ").append(order).append(" ").append(dir)
                .append(" LIMIT ? OFFSET ?");
        args.add(limit); args.add(offset);

        List<Student> res = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < args.size(); i++) ps.setObject(i + 1, args.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    res.add(map(rs));
            }
            return res;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int count(String lastNameLike, Integer groupId, Integer departmentId, Integer facultyId) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*)
          FROM student s
          JOIN student_group g ON g.group_id = s.group_id
          JOIN department d    ON d.department_id = g.department_id
          JOIN faculty f       ON f.faculty_id = d.faculty_id
         WHERE 1=1
    """);
        List<Object> args = new ArrayList<>();
        if (lastNameLike != null && !lastNameLike.isBlank()) {
            sql.append(" AND s.last_name ILIKE ? ");
            args.add("%" + lastNameLike + "%");
        }
        if (groupId != null)      { sql.append(" AND s.group_id = ? "); args.add(groupId); }
        if (departmentId != null) { sql.append(" AND d.department_id = ? "); args.add(departmentId); }
        if (facultyId != null)    { sql.append(" AND f.faculty_id = ? "); args.add(facultyId); }

        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < args.size(); i++) ps.setObject(i + 1, args.get(i));
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Student getByStudentCode(String code) {
        String sql = """
        SELECT student_id, first_name, last_name, patronymic, date_of_birth, gender,
               admission_year, student_code, email, phone, group_id
          FROM student
         WHERE student_code = ?
         LIMIT 1
    """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM student WHERE LOWER(email) = LOWER(?) LIMIT 1";
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Student> getByDepartment(int departmentId, int limit, int offset) {
        String sql = """
        SELECT s.student_id, s.first_name, s.last_name, s.patronymic,
               s.date_of_birth, s.gender, s.admission_year, s.student_code,
               s.email, s.phone, s.group_id
          FROM student s
          JOIN student_group g ON g.group_id = s.group_id
         WHERE g.department_id = ?
         ORDER BY s.student_id
         LIMIT ? OFFSET ?
    """;
        List<Student> out = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    out.add(map(rs));
            }
            return out;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int countByDepartment(int departmentId) {
        String sql = """
        SELECT COUNT(*)
          FROM student s
          JOIN student_group g ON g.group_id = s.group_id
         WHERE g.department_id = ?
    """;
        try (Connection c = Database.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }


    private static String baseSelect() {
        return """
            SELECT student_id, first_name, last_name, patronymic, date_of_birth, gender,
                   admission_year, student_code, email, phone, group_id
              FROM student
        """;
    }

    private static Student map(ResultSet rs) throws SQLException {
        Student s = Student.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .patronymic(rs.getString("patronymic"))
                .dateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class))
                .gender(rs.getString("gender"))
                .admissionYear(rs.getInt("admission_year"))
                .studentCode(rs.getString("student_code"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .groupId(rs.getInt("group_id"))
                .build();

        s.setId(rs.getInt("student_id"));
        return s;
    }
}
