package org.example.entities;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Student {

    private static final AtomicInteger STUDENT_INSTANCE_COUNTER = new AtomicInteger(0);


    private final int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final int admissionYear;
    private long studentCode;
    private String email;
    private String phone;
    private Group group;

    public static class StudentBuilder {
        private final String firstName;
        private final String lastName;
        private final LocalDate dateOfBirth;
        private final String gender;
        private final int admissionYear;
        private final long studentCode;
        private final Group group;
        private String patronymic = "no patronymic";
        private String email = "no email";
        private String phone = "no phone";

        public StudentBuilder(String firstName, String lastName, LocalDate dateOfBirth, String gender, int admissionYear, long studentCode, Group group) {
            this.admissionYear = admissionYear;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.studentCode = studentCode;
            this.group = group;
        }

        public StudentBuilder patronymic(String val) {
            patronymic = val;
            return this;
        }

        public StudentBuilder email(String val) {
            email = val;
            return this;
        }

        public StudentBuilder phone(String val) {
            phone = val;
            return this;
        }

        public Student build() { return new Student(this); }
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(Long studentCode) {
        this.studentCode = studentCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    private Student(StudentBuilder builder) {
        id = STUDENT_INSTANCE_COUNTER.incrementAndGet();
        firstName = builder.firstName;
        lastName = builder.lastName;
        dateOfBirth = builder.dateOfBirth;
        gender = builder.gender;
        admissionYear = builder.admissionYear;
        studentCode = builder.studentCode;
        email = builder.email;
        phone = builder.phone;
        group = builder.group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", admission_year=" + admissionYear +
                ", email='" + email + '\'' +
                ", first_name='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", group=" + group +
                ", last_name='" + lastName + '\'' +
                ", student_code=" + studentCode +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
