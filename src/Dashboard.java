import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Dashboard {
    JFrame dashboardFrame;
    JPanel navigationPanel;
    JButton navButton1, navButton2, navButton3, navButton4, navButton5;
    JLabel heading, imageLabel;

    public Dashboard() {
        dashboardFrame = new JFrame();
        dashboardFrame.setSize(1920, 1080);
        dashboardFrame.setDefaultCloseOperation(dashboardFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setLayout(null);
        dashboardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        dashboardFrame.setUndecorated(true);

        navigationPanel = new JPanel();
        navigationPanel.setBounds(300, 110, 930, 50);
        navigationPanel.setBorder(new LineBorder(Color.gray, 2));
        navigationPanel.setLayout(null);
        dashboardFrame.add(navigationPanel);

        heading = new JLabel("Grocery Store Management System");
        heading.setBounds(300, 180, 930, 40);
        heading.setFont(new Font("monospaced", Font.BOLD, 25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.GRAY, 2));
        dashboardFrame.add(heading);

        imageLabel = new JLabel(new ImageIcon("images/groceystore.png"));
        imageLabel.setBounds(300, 230, 930, 460);
        imageLabel.setBorder(new LineBorder(Color.gray, 2));
        imageLabel.setLayout(null);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dashboardFrame.add(imageLabel);

        navButton1 = new JButton("Manage Products");
        navButton1.setBounds(5, 5, 180, 40);
        navButton1.setFont(new Font("monospaced", Font.BOLD, 16));
        navButton1.setFocusable(false);
        navButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navButton1.setBorder(new LineBorder(Color.BLUE,2));
        navigationPanel.add(navButton1);

        navButton1.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(dashboardFrame, "Enter Password (Only Admin Can Access)");
            if (!password.isEmpty()) {
                if (password.equals("Admin")) {
                    new ManageProducts();
                    dashboardFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(dashboardFrame, "Incorrect Admin Password Try Again");
                }
            } else {
                JOptionPane.showMessageDialog(dashboardFrame, "Password Field Is Empty Fill It First");
            }
        });

        navButton2 = new JButton("View Products");
        navButton2.setBounds(745, 5, 180, 40);
        navButton2.setFont(new Font("monospaced", Font.BOLD, 16));
        navButton2.setFocusable(false);
        navButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navButton2.setBorder(new LineBorder(Color.BLUE,2));
        navigationPanel.add(navButton2);

        navButton2.addActionListener(e -> {
            new ViewProducts();
            dashboardFrame.dispose();
        });

        navButton3 = new JButton("Buy Products");
        navButton3.setBounds(560, 5, 180, 40);
        navButton3.setFont(new Font("monospaced", Font.BOLD, 16));
        navButton3.setFocusable(false);
        navButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navButton3.setBorder(new LineBorder(Color.BLUE,2));
        navigationPanel.add(navButton3);

        navButton3.addActionListener(e -> {
            new PurchaseProducts();
            dashboardFrame.dispose();
        });

        navButton4 = new JButton("Sales Report");
        navButton4.setBounds(190, 5, 180, 40);
        navButton4.setFont(new Font("monospaced", Font.BOLD, 16));
        navButton4.setFocusable(false);
        navButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navButton4.setBorder(new LineBorder(Color.BLUE,2));
        navigationPanel.add(navButton4);

        navButton4.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(dashboardFrame, "Enter Password (Only Admin Can Access)");
            if (!password.isEmpty()) {
                if (password.equals("Admin")) {
                    new SalesReport();
                    dashboardFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(dashboardFrame, "Incorrect Admin Password Try Again");
                }
            } else {
                JOptionPane.showMessageDialog(dashboardFrame, "Password Field Is Empty Fill It First");
            }
        });

        navButton5 = new JButton("Exit");
        navButton5.setBounds(375, 5, 180, 40);
        navButton5.setFont(new Font("monospaced", Font.BOLD, 16));
        navButton5.setBorder(new LineBorder(Color.BLUE,2));
        navButton5.setFocusable(false);

        navButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(navButton5);

        navButton5.addActionListener(e -> {
            dashboardFrame.dispose();
        });

        dashboardFrame.setVisible(true);
    }
}
