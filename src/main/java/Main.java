
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static StudentService studentService;

    public static void main(String[] args) {
        try {
            studentService =
                    new StudentService(new StudentFileStorage());
        } catch (StudentStorageException e) {
            System.out.println("Startup error: " + e.getMessage());
            scanner.close();
            return;
        }

        System.out.println("Student records loaded: "
                + studentService.getStudentCount());

        boolean running = true;

        while (running) {
            displayMenu();
            String choice = readMenuChoice();

            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> listStudents();
                    case "3" -> searchStudent();
                    case "4" -> deleteStudent();
                    case "5" -> showSummary();
                    case "0" -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println(
                            "Invalid choice. Enter a number from 0 to 5."
                    );
                }
            } catch (StudentStorageException e) {
                System.out.println("Storage error: " + e.getMessage());
                System.out.println(
                        "Your requested change could not be saved."
                );
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
    }

    private static String readMenuChoice() {
        while (true) {
            System.out.print("Enter your choice (0-5): ");
            String choice = scanner.nextLine().trim();

            if (choice.matches("[0-5]")) {
                return choice;
            }

            System.out.println(
                    "Invalid input. Please enter a number from 0 to 5."
            );
        }
    }

   
private static String readNonBlankName(String prompt) {
    while (true) {
        System.out.print(prompt);
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be blank. Please try again.");
        } else if (!name.matches(".*\\p{L}.*")) {
            System.out.println(
                    "Name must contain at least one letter. Please try again."
            );
        } else {
            return name;
        }
    }
}


    private static void addStudent()
            throws StudentStorageException {
        String name = readNonBlankName("Enter student name: ");

        if (studentService.addStudent(name)) {
            System.out.println("Record added and saved successfully.");
        } else {
            System.out.println("Invalid name. Please try again.");
        }
    }

    private static void listStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + students.get(i).getName()
            );
        }
    }

    private static void searchStudent() {
        String name = readNonBlankName(
                "Enter student name to search: "
        );

        Student student = studentService.searchStudent(name);

        if (student != null) {
            System.out.println("Record found: " + student.getName());
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void deleteStudent()
            throws StudentStorageException {
        String name = readNonBlankName(
                "Enter student name to delete: "
        );

        if (studentService.deleteStudent(name)) {
            System.out.println("Record deleted and saved successfully.");
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void showSummary() {
        System.out.println(
                "Total records: " + studentService.getStudentCount()
        );
    }
}
