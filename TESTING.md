# Task 3 — Testing Report

## Environment

- Java 21
- Maven
- CSV file storage using Java I/O and NIO
- Application: Student Register CLI

## Hostile Input Tests

| No. | Test input or condition | Expected behavior | Result |
|---:|---|---|---|
| 1 | Letters (`abc`) at the menu | Reject input and re-prompt | Passed |
| 2 | Blank menu input | Reject input and re-prompt | Passed |
| 3 | Invalid menu number (`9`) | Reject input and re-prompt | Passed |
| 4 | Blank student name | Show validation message and re-prompt | Passed |
| 5 | Numeric-only student name (`123`) | Reject name and re-prompt | Verify after validation update |
| 6 | Restart application after saving a record | Restore saved records from CSV | Passed |
| 7 | Malformed CSV row | Skip invalid row and continue loading valid records | Passed |
| 8 | Missing CSV file on first run | Start with an empty record list | Passed |
| 9 | CSV file deleted while application is running | Handle subsequent save/load failure gracefully | Pending |
| 10 | CSV file cannot be written | Display a readable storage error without a stack trace | Pending |

## Observations

- Student records persist across application restarts.
- Menu input validation rejects invalid choices without terminating the application.
- Blank names are rejected with a readable message.
- Malformed CSV rows are skipped while valid rows are loaded.
- File permission and mid-session deletion scenarios require additional verification.

## Conclusion

The Student Register CLI now supports CSV-based persistence and defensive input validation. Further file-error testing is required to verify behavior under all storage failure conditions.
