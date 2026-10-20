
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students;
    private final StudentFileStorage storage;

    public StudentService(StudentFileStorage storage)
            throws StudentStorageException {
        this.storage = storage;
        this.students = new ArrayList<>(storage.loadStudents());
    }

    public boolean addStudent(String name)
            throws StudentStorageException {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        Student student = new Student(name.trim());
        students.add(student);

        try {
            storage.saveStudents(students);
            return true;
        } catch (StudentStorageException e) {
            students.remove(student);
            throw e;
        }
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

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

    public boolean deleteStudent(String name)
            throws StudentStorageException {
        Student student = searchStudent(name);

        if (student == null) {
            return false;
        }

        int originalIndex = students.indexOf(student);
        students.remove(student);

        try {
            storage.saveStudents(students);
            return true;
        } catch (StudentStorageException e) {
            students.add(originalIndex, student);
            throw e;
        }
    }

    public int getStudentCount() {
        return students.size();
    }
}
