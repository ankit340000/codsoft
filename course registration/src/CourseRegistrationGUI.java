import javax.swing.*;
import java.awt.*;
import java.util.*;

class Course {
    String code, title;
    int capacity;
    ArrayList<String> enrolledStudents = new ArrayList<>();

    public Course(String code, String title, int capacity) {
        this.code = code;
        this.title = title;
        this.capacity = capacity;
    }

    public boolean enrollStudent(String studentID) {
        if (enrolledStudents.size() < capacity && !enrolledStudents.contains(studentID)) {
            enrolledStudents.add(studentID);
            return true;
        }
        return false;
    }

    public void removeStudent(String studentID) {
        enrolledStudents.remove(studentID);
    }

    public String toString() {
        return code + " - " + title + " (" + (capacity - enrolledStudents.size()) + " slots left)";
    }
}

class Student {
    String id, name;
    ArrayList<String> registeredCourses = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void registerCourse(String courseCode) {
        if (!registeredCourses.contains(courseCode)) {
            registeredCourses.add(courseCode);
        }
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }

    public String toString() {
        return id + " - " + name + " | Registered: " + registeredCourses;
    }
}

public class CourseRegistrationGUI {
    static HashMap<String, Course> courseMap = new HashMap<>();
    static HashMap<String, Student> studentMap = new HashMap<>();
    static JTextArea output = new JTextArea(15, 40);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Course Registration System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout());

        // Buttons
        JButton addCourse = new JButton("Add Course");
        JButton addStudent = new JButton("Add Student");
        JButton register = new JButton("Register Course");
        JButton drop = new JButton("Drop Course");
        JButton viewCourses = new JButton("View Courses");
        JButton viewStudents = new JButton("View Students");

        panel.add(addCourse);
        panel.add(addStudent);
        panel.add(register);
        panel.add(drop);
        panel.add(viewCourses);
        panel.add(viewStudents);
        panel.add(new JScrollPane(output));

        // Actions
        addCourse.addActionListener(e -> {
            String code = JOptionPane.showInputDialog("Course Code:");
            String title = JOptionPane.showInputDialog("Course Title:");
            int capacity = Integer.parseInt(JOptionPane.showInputDialog("Capacity:"));
            courseMap.put(code, new Course(code, title, capacity));
            output.append("Course added: " + code + "\n");
        });

        addStudent.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Student ID:");
            String name = JOptionPane.showInputDialog("Student Name:");
            studentMap.put(id, new Student(id, name));
            output.append("Student added: " + id + "\n");
        });

        register.addActionListener(e -> {
            String sid = JOptionPane.showInputDialog("Student ID:");
            String cid = JOptionPane.showInputDialog("Course Code:");
            if (studentMap.containsKey(sid) && courseMap.containsKey(cid)) {
                boolean success = courseMap.get(cid).enrollStudent(sid);
                if (success) {
                    studentMap.get(sid).registerCourse(cid);
                    output.append("Student " + sid + " registered in " + cid + "\n");
                } else {
                    output.append("Registration failed (Course full or already registered).\n");
                }
            } else {
                output.append("Invalid student or course ID.\n");
            }
        });

        drop.addActionListener(e -> {
            String sid = JOptionPane.showInputDialog("Student ID:");
            String cid = JOptionPane.showInputDialog("Course Code:");
            if (studentMap.containsKey(sid) && courseMap.containsKey(cid)) {
                courseMap.get(cid).removeStudent(sid);
                studentMap.get(sid).dropCourse(cid);
                output.append("Student " + sid + " dropped from " + cid + "\n");
            } else {
                output.append("Invalid student or course ID.\n");
            }
        });

        viewCourses.addActionListener(e -> {
            output.append("\nAvailable Courses:\n");
            for (Course c : courseMap.values()) {
                output.append(c + "\n");
            }
        });

        viewStudents.addActionListener(e -> {
            output.append("\nRegistered Students:\n");
            for (Student s : studentMap.values()) {
                output.append(s + "\n");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
