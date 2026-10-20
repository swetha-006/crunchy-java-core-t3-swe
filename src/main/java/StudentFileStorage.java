
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class StudentFileStorage {

    private final Path filePath;

    public StudentFileStorage() {
        this(Path.of("data", "students.csv"));
    }

    public StudentFileStorage(Path filePath) {
        this.filePath = filePath;
    }

    public List<Student> loadStudents() throws StudentStorageException {
        List<Student> students = new ArrayList<>();

        // A missing file is normal on the first run.
        if (!Files.exists(filePath)) {
            return students;
        }

        try (BufferedReader reader =
                     Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String header = reader.readLine();

            if (header == null || !header.equals("name")) {
                throw new StudentStorageException(
                        "The CSV file has an invalid or missing header."
                );
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                try {
                    String name = parseName(line);
                    students.add(new Student(name));
                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Warning: Skipped invalid CSV line "
                                    + lineNumber + "."
                    );
                }
            }

            return students;

        } catch (IOException e) {
            throw new StudentStorageException(
                    "Could not read student records. Check the data file.",
                    e
            );
        }
    }

    public void saveStudents(List<Student> students)
            throws StudentStorageException {

        try {
            Path parent = filePath.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    filePath,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {

                writer.write("name");
                writer.newLine();

                for (Student student : students) {
                    String escapedName =
                            student.getName().replace("\"", "\"\"");
                    writer.write("\"" + escapedName + "\"");
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new StudentStorageException(
                    "Could not save student records. Check file permissions.",
                    e
            );
        }
    }

    private String parseName(String line) {
        if (line.length() < 2
                || line.charAt(0) != '"'
                || line.charAt(line.length() - 1) != '"') {
            throw new IllegalArgumentException("Invalid CSV format");
        }

        String content = line.substring(1, line.length() - 1);
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char current = content.charAt(i);

            if (current == '"') {
                if (i + 1 < content.length()
                        && content.charAt(i + 1) == '"') {
                    name.append('"');
                    i++;
                } else {
                    throw new IllegalArgumentException("Invalid quote");
                }
            } else {
                name.append(current);
            }
        }

        String result = name.toString().trim();

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Empty student name");
        }

        return result;
    }
}
