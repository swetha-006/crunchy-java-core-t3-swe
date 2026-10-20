
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> listStudents();
                case "3" -> searchStudent();
                case "4" -> deleteStudent();
                case "5" -> showSummary();
                case "0" -> {
                    running = false;
                    System.out.println("Exiting Student Register. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== STUDENT REGISTER =====");
        System.out.println("1. Add Record");
        System.out.println("2. List Records");
        System.out.println("3. Search Record");
        System.out.println("4. Delete Record");
        System.out.println("5. Summary");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        if (studentService.addStudent(name)) {
            System.out.println("Record added successfully.");
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    private static void listStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName());
        }
    }

    private static void searchStudent() {
        System.out.print("Enter student name to search: ");
        String name = scanner.nextLine();

        Student student = studentService.searchStudent(name);

        if (student != null) {
            System.out.println("Record found: " + student);
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter student name to delete: ");
        String name = scanner.nextLine();

        if (studentService.deleteStudent(name)) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void showSummary() {
        System.out.println("Total records: " + studentService.getStudentCount());
    }
}
