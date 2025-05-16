package com.hello.advanced.programs;

//Student Result Processing System
//        Requirements:
//        Read a student list, process grades concurrently (Multithreading).
//        Use Stream API for filtering and sorting results.
//        Secure sensitive student data (like email) usiSerialize and deserialize the processed data to/from a file.ng encryption.
//        Serialize and deserialize the processed data to/from a file.
//        Use Factory + Strategy Patterns to generate different types of reports (CSV/PDF).
//        Use Optional and Functional Interfaces where applicable.
//        Tools: Java 17, Collections, Concurrency, Stream API, File I/O, Security API

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class P8_StudentResultProcessingSystem {

    public static void main(String[] args) throws Exception {

        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);
        loop:
        while (true) {
            System.out.println(
                    "Add student into system 1.\n" +
                            "Sort students 2.\n" +
                            "filter students 3.\n" +
                            "print students 4.\n" +
                            "generate report 5.\n" +
                            "Exit press 0"
            );

            System.out.print("Enter num:");
            int num = scanner.nextInt();
            scanner.nextLine();

            switch (num) {
                case 1 -> {
                    ExecutorService executorService = Executors.newFixedThreadPool(2);

                    List<Stud> students = new ArrayList<>();

                    while (true) {
                        System.out.println("Enter student name (or type 'exit' to stop):");
                        String name = scanner.nextLine();
                        if (name.equalsIgnoreCase("exit")) {
                            break;
                        }

                        System.out.println("Enter student ID:");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.println("Enter student email:");
                        String email = scanner.nextLine();

                        System.out.println("Enter student age:");
                        int age = Integer.parseInt(scanner.nextLine());

                        System.out.println("Enter number of subjects:");
                        int subjectCount = Integer.parseInt(scanner.nextLine());
                        int[] marks = new int[subjectCount];

                        for (int i = 0; i < subjectCount; i++) {
                            System.out.println("Enter marks for subject " + (i + 1) + ":");
                            marks[i] = Integer.parseInt(scanner.nextLine());
                        }

                        students.add(new Stud(id, name, email, age, marks));
                    }
                    System.out.println("\n--- All Students ---");
                    for (Stud s : students) {
                        System.out.println(s);
                    }
                    students.forEach(
                            stud -> executorService.submit(
                                    () -> {
                                        StudentResultProcessingSystem.processStudentAndAssignGrade(stud);
                                        studentManagementSystem.add(stud);
                                    }
                            )
                    );
                }
                case 2 -> {
                    System.out.println("Enter sort by (id, name, email, age, grade):");
                    String sortBy = scanner.nextLine().toLowerCase();

                    List<Stud> sortedList;

                    switch (sortBy) {
                        case "id" -> sortedList = studentManagementSystem.getStudentList()
                                .stream()
                                .sorted(StudentResultProcessingSystem.studIdComparator())
                                .collect(Collectors.toList());
                        case "name" -> sortedList = studentManagementSystem.getStudentList()
                                .stream()
                                .sorted(StudentResultProcessingSystem.studNameComparator())
                                .collect(Collectors.toList());
                        case "email" -> sortedList = studentManagementSystem.getStudentList()
                                .stream()
                                .sorted(StudentResultProcessingSystem.studEmailComparator())
                                .collect(Collectors.toList());
                        case "age" -> sortedList = studentManagementSystem.getStudentList()
                                .stream()
                                .sorted(StudentResultProcessingSystem.studAgeComparator())
                                .collect(Collectors.toList());
                        case "grade" -> sortedList = studentManagementSystem.getStudentList()
                                .stream()
                                .sorted(StudentResultProcessingSystem.studGradeComparator())
                                .collect(Collectors.toList());
                        default -> {
                            System.out.println("Not a valid field.");
                            return;
                        }
                    }
                    studentManagementSystem.printAllStudent(sortedList);
                }
                case 3 -> {
                    System.out.println("Enter filter by (id, name, email, age, grade):");
                    String filterBy = scanner.nextLine().toLowerCase();

                    switch (filterBy) {
                        case "id" -> {
                            System.out.print("Enter ID: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            studentManagementSystem.printAllStudent(
                                    StudentResultProcessingSystem.filterById(studentManagementSystem.getStudentList(), id)
                            );
                        }
                        case "name" -> {
                            System.out.print("Enter Name: ");
                            String name = scanner.nextLine();
                            studentManagementSystem.printAllStudent(
                                    StudentResultProcessingSystem.filterByName(studentManagementSystem.getStudentList(), name)
                            );
                        }
                        case "email" -> {
                            System.out.print("Enter Email: ");
                            String email = scanner.nextLine();
                            studentManagementSystem.printAllStudent(
                                    StudentResultProcessingSystem.filterByEmail(studentManagementSystem.getStudentList(), email)
                            );
                        }
                        case "age" -> {
                            System.out.print("Enter Age: ");
                            int age = Integer.parseInt(scanner.nextLine());
                            studentManagementSystem.printAllStudent(
                                    StudentResultProcessingSystem.filterByAge(studentManagementSystem.getStudentList(), age)
                            );
                        }
                        case "grade" -> {
                            System.out.print("Enter Grade (A, B, C, D, F): ");
                            String gradeInput = scanner.nextLine().toUpperCase();
                            try {
                                Grade grade = Grade.valueOf(gradeInput);
                                studentManagementSystem.printAllStudent(
                                        StudentResultProcessingSystem.filterByGrade(studentManagementSystem.getStudentList(), grade)
                                );
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid grade entered.");
                            }
                        }
                        default -> System.out.println("Not a valid field.");
                    }
                }
                case 4 -> studentManagementSystem.printAllStudent(studentManagementSystem.getStudentList());
                case 5 -> {
                    try {
                        System.out.println("Enter Report Type (ByMarks/ByAttendance):");
                        Report.ReportType reportType = Report.ReportType.valueOf(scanner.nextLine());
                        try {
                            System.out.println("Enter Report Strategy (PDF/CSV):");
                            Report.ReportStrategy reportStrategy = Report.ReportStrategy.valueOf(scanner.nextLine());
                            Report report = ReportConfigGenerationFactory.generateReport(reportType, reportStrategy);
                            System.out.println("Enter Path:");
                            report.generate(scanner.nextLine());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid report type.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid report by.");
                    }
                }
                case 0 -> {
                    break loop;
                }
                default -> System.out.println("Invalid input.");
            }
            System.out.println();
        }
    }
}

class StudentManagementSystem {
    private final List<Stud> studentList;
    private final File file;
    public SecretKey secretKey;

    public StudentManagementSystem() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        this.secretKey = keyGenerator.generateKey();
        this.secretKey = new SecretKeySpec("0123456789abcdef".getBytes(StandardCharsets.UTF_8), "AES");
        this.studentList = new ArrayList<>();
        this.file = new File("stud.ser");
        loadDataFromFile();
    }

    private void loadDataFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Stud stud = (Stud) objectInputStream.readObject();
                    studentList.add(stud);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (EOFException e) {
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("All data loaded.");
        System.out.println("All data:" + studentList);
    }

    public boolean add(Stud stud) {
        try {
            stud.setEmail(this.encryptString(stud.getEmail()));
            persist(stud);
            if (studentList.add(stud)) {
                System.out.println("employee persist successfully");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void persist(Stud stud) throws Exception {
        boolean append = file.exists() && file.length() > 0;
        try (ObjectOutputStream objectOutputStream = append ? new AppendingObjectOutputStream(new FileOutputStream(file, true)) : new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(stud);
            objectOutputStream.flush();
        }
    }

    public List<Stud> getStudentList() {
//        return studentList;
        return studentList.stream().map(stud -> {
            try {
                Stud student = new Stud();
                student.setId(stud.getId());
                student.setName(stud.getName());
                student.setEmail(this.decryptString(stud.getEmail()));
                student.setAge(stud.getAge());
                student.setMarks(Arrays.copyOf(stud.getMarks(), stud.getMarks().length));
                student.setGrade(stud.getGrade());
                return student;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void printAllStudent(List<Stud> studentList) {
        System.out.println("List:");
        studentList.forEach(stud ->
                System.out.println("Stud{" +
                        "id=" + stud.getId() +
                        ", name='" + stud.getName() + '\'' +
                        ", email='" + stud.getEmail() + '\'' +
                        ", age=" + stud.getAge() +
                        ", marks=" + Arrays.toString(stud.getMarks()) +
                        ", grade=" + stud.getGrade() +
                        '}')
        );
    }

    private String encryptString(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = cipher.doFinal(strBytes);
        String encodedString = Base64.getEncoder().encodeToString(encryptedBytes);
        return encodedString;
    }

    private String decryptString(String encodedString) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}

class StudentResultProcessingSystem {
    public static void processStudentAndAssignGrade(Stud stud) {
        int[] marksArray = stud.getMarks();
        int total = 0;
        for (int mark : marksArray) {
            total += mark;
        }
        int average = marksArray.length > 0 ? total / marksArray.length : 0;
        Grade grade;
        if (average >= 90) {
            grade = Grade.A;
        } else if (average >= 80) {
            grade = Grade.B;
        } else if (average >= 70) {
            grade = Grade.C;
        } else if (average >= 60) {
            grade = Grade.D;
        } else {
            grade = Grade.F;
        }
        stud.setGrade(grade);
    }

    public static Comparator<Stud> studIdComparator() {
        return Comparator.comparingInt(Stud::getId);
    }

    public static Comparator<Stud> studNameComparator() {
        return Comparator.comparing(Stud::getName);
    }

    public static Comparator<Stud> studEmailComparator() {
        return Comparator.comparing(Stud::getEmail);
    }

    public static Comparator<Stud> studAgeComparator() {
        return Comparator.comparingInt(Stud::getId);
    }

    public static Comparator<Stud> studGradeComparator() {
        return Comparator.comparing(Stud::getGrade);
    }

    public static List<Stud> filterById(List<Stud> studList, int id) {
        return studList.stream().filter(stud -> stud.getId() == id).collect(Collectors.toList());
    }

    public static List<Stud> filterByName(List<Stud> studList, String name) {
        return studList.stream().filter(stud -> stud.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public static List<Stud> filterByEmail(List<Stud> studList, String email) {
        return studList.stream().filter(stud -> stud.getEmail().equals(email)).collect(Collectors.toList());
    }

    public static List<Stud> filterByAge(List<Stud> studList, int age) {
        return studList.stream().filter(stud -> stud.getAge() == age).collect(Collectors.toList());
    }

    public static List<Stud> filterByGrade(List<Stud> studList, Grade grade) {
        return studList.stream().filter(stud -> stud.getGrade().equals(grade)).collect(Collectors.toList());
    }
}

class Stud implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private int age;
    private int[] marks;
    private Grade grade;

    public Stud() {
    }

    public Stud(int id, String name, String email, int age, int[] marks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Stud{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", marks=" + Arrays.toString(marks) +
                ", grade=" + grade +
                '}';
    }
}

enum Grade {
    A, B, C, D, F
}