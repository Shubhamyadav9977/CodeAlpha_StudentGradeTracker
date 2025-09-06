import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextField nameField, gradeField;
    private JTextArea reportArea;

    public StudentGradeTrackerGUI() {
        setTitle("📊 Student Grade Tracker");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center screen

        // Main panel with padding
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Student Grade Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 120));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Student Name
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Grade
        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(gradeLabel, gbc);

        gradeField = new JTextField(5);
        gbc.gridx = 1;
        panel.add(gradeField, gbc);

        // Buttons
        JButton addButton = new JButton("➕ Add Student");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(60, 179, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(addButton, gbc);

        JButton reportButton = new JButton("📑 Show Report");
        reportButton.setFont(new Font("Arial", Font.BOLD, 14));
        reportButton.setBackground(new Color(70, 130, 180));
        reportButton.setForeground(Color.WHITE);
        reportButton.setFocusPainted(false);

        gbc.gridx = 1;
        panel.add(reportButton, gbc);

        // Report Area
        reportArea = new JTextArea(12, 35);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reportArea.setEditable(false);
        reportArea.setBackground(new Color(230, 240, 250));
        reportArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 150), 2));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(new JScrollPane(reportArea), gbc);

        add(panel);

        // Button actions
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            try {
                double grade = Double.parseDouble(gradeField.getText().trim());
                students.add(new Student(name, grade));
                reportArea.append("✔ Added: " + name + " - " + grade + "\n");
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "⚠ Please enter a valid grade!");
            }
        });

        reportButton.addActionListener(e -> {
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(null, "⚠ No students added yet!");
                return;
            }

            double sum = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
            String topStudent = "", lowStudent = "";

            StringBuilder report = new StringBuilder("===== Student Grade Report =====\n\n");

            for (Student s : students) {
                report.append(s.name).append(" : ").append(s.grade).append("\n");
                sum += s.grade;
                if (s.grade > highest) {
                    highest = s.grade;
                    topStudent = s.name;
                }
                if (s.grade < lowest) {
                    lowest = s.grade;
                    lowStudent = s.name;
                }
            }

            double average = sum / students.size();

            report.append("\n--------------------------------\n");
            report.append("📊 Average Score: ").append(average).append("\n");
            report.append("🏆 Highest Score: ").append(highest).append(" (" + topStudent + ")\n");
            report.append("⬇ Lowest Score: ").append(lowest).append(" (" + lowStudent + ")\n");

            reportArea.setText(report.toString());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}
