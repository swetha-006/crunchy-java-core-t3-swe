
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    // Add a student
    public boolean addStudent(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        students.add(new Student(name.trim()));
        return true;
    }

    // Return all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // Search for a student by name
    public Student searchStudent(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name.trim())) {
                return student;
            }
        }

        return null;
    }

    // Delete a student by name
    public boolean deleteStudent(String name) {
        Student student = searchStudent(name);

        if (student == null) {
            return false;
        }

        students.remove(student);
        return true;
    }

    // Return the total number of students
    public int getStudentCount() {
        return students.size();
    }
}
