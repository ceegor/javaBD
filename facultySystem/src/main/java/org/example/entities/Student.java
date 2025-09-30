package org.example.entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Student {

    private static final AtomicInteger STUDENT_INSTANCE_COUNTER = new AtomicInteger(0);


    private final int id;
    private String first_name;
    private String last_name;
    private String patronymic;
    private final String gender;
    private final int admission_year;
    private long student_code;
    private String email;
    private String phone;
    private Group group;

    public static class StudentBuilder {
        private final String first_name;
        private final String last_name;
        private final String gender;
        private final int admission_year;
        private final long student_code;
        private final Group group;
        private String patronymic = "no patronymic";
        private String email = "no email";
        private String phone = "no phone";

        public StudentBuilder(String first_name, String last_name, String gender, int admission_year, long student_code, Group group) {
            this.admission_year = admission_year;
            this.first_name = first_name;
            this.last_name = last_name;
            this.gender = gender;
            this.student_code = student_code;
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

    public int getAdmission_year() {
        return admission_year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public Long getStudent_code() {
        return student_code;
    }

    public void setStudent_code(Long student_code) {
        this.student_code = student_code;
    }

    private Student(StudentBuilder builder) {
        id = STUDENT_INSTANCE_COUNTER.incrementAndGet();
        first_name = builder.first_name;
        last_name = builder.last_name;
        gender = builder.gender;
        admission_year = builder.admission_year;
        student_code = builder.student_code;
        email = builder.email;
        phone = builder.phone;
        group = builder.group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", admission_year=" + admission_year +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", gender='" + gender + '\'' +
                ", group=" + group +
                ", last_name='" + last_name + '\'' +
                ", student_code=" + student_code +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
