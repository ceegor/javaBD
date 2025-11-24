package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


@Getter
@Setter
public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String gender;
    private int admissionYear;
    private String studentCode;
    private String email;
    private String phone;
    private int groupId;

    private Student() {}

    public static Builder builder() { return new Builder(); }
    public static final class Builder {
        private final Student s = new Student();
        public Builder firstName(String v){ s.firstName = v; return this; }
        public Builder lastName(String v){ s.lastName = v; return this; }
        public Builder patronymic(String v){ s.patronymic = v; return this; }
        public Builder dateOfBirth(LocalDate v){ s.dateOfBirth = v; return this; }
        public Builder gender(String v){ s.gender = v; return this; }
        public Builder admissionYear(int v){ s.admissionYear = v; return this; }
        public Builder studentCode(String v){ s.studentCode = v; return this; }
        public Builder email(String v){ s.email = v; return this; }
        public Builder phone(String v){ s.phone = v; return this; }
        public Builder groupId(int v){ s.groupId = v; return this; }
        public Student build() {
            Objects.requireNonNull(s.firstName, "firstName");
            Objects.requireNonNull(s.lastName, "lastName");
            if (s.groupId <= 0) throw new IllegalArgumentException("groupId must be > 0");
            return s;
        }
    }

    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPatronymic() { return patronymic; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public int getAdmissionYear() { return admissionYear; }
    public String getStudentCode() { return studentCode; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getGroupId() { return groupId; }


//    private Integer id;
//    private String firstName;
//    private String lastName;
//    private String patronymic;
//    private LocalDate dateOfBirth;
//    private String gender;
//    private int admissionYear;
//    private long studentCode;
//    private String email;
//    private String phone;
//    private int groupId;
//
//    public static class StudentBuilder {
//        private final String firstName;
//        private final String lastName;
//        private final LocalDate dateOfBirth;
//        private final String gender;
//        private final int admissionYear;
//        private final long studentCode;
//        private final int groupId;
//        private String patronymic = "no patronymic";
//        private String email = "no email";
//        private String phone = "no phone";
//
//        public StudentBuilder(String firstName, String lastName, LocalDate dateOfBirth, String gender, int admissionYear, long studentCode, int groupId) {
//            this.admissionYear = admissionYear;
//            this.firstName = firstName;
//            this.lastName = lastName;
//            this.dateOfBirth = dateOfBirth;
//            this.gender = gender;
//            this.studentCode = studentCode;
//            this.groupId = groupId;
//        }
//
//        public StudentBuilder patronymic(String val) {
//            patronymic = val;
//            return this;
//        }
//
//        public StudentBuilder email(String val) {
//            email = val;
//            return this;
//        }
//
//        public StudentBuilder phone(String val) {
//            phone = val;
//            return this;
//        }
//
//        public Student build() { return new Student(this); }
//    }
//
//    private Student(StudentBuilder builder) {
//        firstName = builder.firstName;
//        lastName = builder.lastName;
//        dateOfBirth = builder.dateOfBirth;
//        gender = builder.gender;
//        admissionYear = builder.admissionYear;
//        studentCode = builder.studentCode;
//        email = builder.email;
//        phone = builder.phone;
//        groupId = builder.groupId;
//        patronymic = builder.patronymic;
//    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", admission_year=" + admissionYear +
                ", email='" + email + '\'' +
                ", first_name='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", group=" + groupId +
                ", last_name='" + lastName + '\'' +
                ", student_code=" + studentCode +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return s.id == id && s.firstName.equals(firstName) && s.lastName.equals(lastName) && s.dateOfBirth.equals(dateOfBirth);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        return result;
    }
}
