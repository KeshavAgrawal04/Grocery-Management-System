import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomersSalesReport {
    JFrame customersSalesFrame;
    JFrame viewFrame;
    JLabel heading, customerNameL, customerMobileNoL, customerIdL, customerAmountL;
    JPanel tablePanel, navigationPanel, navigationPanel1;
    JButton back;
    JScrollPane scrollPane;
    JTable productsTable;
    JDBC jdbc;
    String customersName[];
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    int customerId = 0;
    int salesId = 0;

    public CustomersSalesReport(JTable jTable) {
        jdbc = new JDBC();

        salesId = Integer.parseInt((String) (jTable.getValueAt(jTable.getSelectedRow(), 0)));
        // System.out.println("ID : " + salesId);
        customerId = Integer.parseInt((String) (jTable.getValueAt(jTable.getSelectedRow(), 1)));
        jTable.setRowMargin(5);
        customersSalesFrame = new JFrame();
        customersSalesFrame.setSize(1920, 1080);
        customersSalesFrame.setDefaultCloseOperation(viewFrame.EXIT_ON_CLOSE);
        customersSalesFrame.setLocationRelativeTo(null);
        customersSalesFrame.setLayout(null);
        customersSalesFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        customersSalesFrame.setUndecorated(true);

        heading = new JLabel("Customers Sales Report");
        heading.setBounds(300, 110, 930, 40);
        heading.setFont(new Font("monospaced", Font.BOLD, 25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.GRAY, 2));
        customersSalesFrame.add(heading);

        tablePanel = new JPanel();
        tablePanel.setBounds(300, 260, 930, 360);
        tablePanel.setBorder(new LineBorder(Color.gray, 2));
        tablePanel.setLayout(null);
        customersSalesFrame.add(tablePanel);

        navigationPanel = new JPanel();
        navigationPanel.setBounds(300, 630, 930, 50);
        navigationPanel.setBorder(new LineBorder(Color.gray, 2));
        navigationPanel.setLayout(null);
        customersSalesFrame.add(navigationPanel);

        navigationPanel1 = new JPanel();
        navigationPanel1.setBounds(300, 160, 930, 90);
        navigationPanel1.setBorder(new LineBorder(Color.gray, 2));
        navigationPanel1.setLayout(null);
        customersSalesFrame.add(navigationPanel1);

        customerNameL = new JLabel("Customer Name : " + (jTable.getValueAt(jTable.getSelectedRow(), 2)));
        customerNameL.setBounds(50, 5, 250, 40);
        customerNameL.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(customerNameL);

        customerIdL = new JLabel("Customer Id   : " + (jTable.getValueAt(jTable.getSelectedRow(), 1)));
        customerIdL.setBounds(50, 45, 250, 40);
        customerIdL.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(customerIdL);

        customerMobileNoL = new JLabel("Customer Mobile No   : " + (jTable.getValueAt(jTable.getSelectedRow(), 3)));
        customerMobileNoL.setBounds(450, 5, 550, 40);
        customerMobileNoL.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(customerMobileNoL);

        customerAmountL = new JLabel("Total Bill Amount    : " + (jTable.getValueAt(jTable.getSelectedRow(), 5)));
        customerAmountL.setBounds(450, 45, 550, 40);
        customerAmountL.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(customerAmountL);

        back = new JButton("Back");
        back.setBounds(375, 5, 180, 40);
        back.setFont(new Font("monospaced", Font.BOLD, 25));
        back.setFocusable(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(back);

        back.addActionListener(e -> {
            new Dashboard();
            customersSalesFrame.dispose();
        });

        showTable();

        customersSalesFrame.setVisible(true);
    }

    public void showTable() {
        String tableColumns[] = { "PRODUCT ID", "PRODUCT NAME", "PRICE", "QUANTITY", "TOTAL AMOUNT", "DATE" };
        String tableData[][] = new String[0][0];
        int count = 0;
        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement(
                    "SELECT Count(*) From Orders WHERE CustomerId = " + customerId + " AND SalesId = " + salesId + ";");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            // System.out.println("Cust Id : " + customerId);
            // System.out.println(count);

            tableData = new String[count][6];

            preparedStatement = jdbc.connection.prepareStatement(
                    "SELECT ProductId,ProductName,ProductPrice,Quantity,Amount, Date from Orders WHERE CustomerId = "
                            + customerId + " AND SalesId = " + salesId + ";");
            ResultSet resultSet1 = preparedStatement.executeQuery();

            for (int i = 0; i < count; i++) {
                resultSet1.next();
                tableData[i][0] = resultSet1.getString(1);
                tableData[i][1] = resultSet1.getString(2);
                tableData[i][2] = resultSet1.getString(3);
                tableData[i][3] = resultSet1.getString(4);
                tableData[i][4] = resultSet1.getString(5);
                tableData[i][5] = resultSet1.getString(6);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productsTable = new JTable(tableData, tableColumns);
        productsTable.setFont(new Font("monospaced", Font.BOLD, 18));
        productsTable.setRowHeight(35);
        productsTable.setEnabled(false);
        scrollPane = new JScrollPane(productsTable);
        scrollPane.setBounds(5, 5, 920, 350);
        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);
    }

    public static void main(String[] args) {
        new CustomersSalesReport(new JTable());
    }
}
