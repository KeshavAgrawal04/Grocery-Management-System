import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalesReport {
    JFrame salesFrame;
    JFrame viewFrame;
    JLabel heading,totalAmount,gst,profit;
    JPanel tablePanel,navigationPanel,navigationPanel1;
    JButton back;
    JScrollPane scrollPane;
    JTable productsTable;
    JDBC jdbc;
    PreparedStatement preparedStatement;
    ResultSet resultSet;


    public SalesReport() {
        salesFrame = new JFrame();
        salesFrame.setSize(1920,1080);
        salesFrame.setDefaultCloseOperation(viewFrame.EXIT_ON_CLOSE);
        salesFrame.setLocationRelativeTo(null);
        salesFrame.setLayout(null);
        salesFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        salesFrame.setUndecorated(true);

        heading = new JLabel("Sales Report");
        heading.setBounds(300,110,930,40);
        heading.setFont(new Font("monospaced",Font.BOLD,25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.GRAY,2));
        salesFrame.add(heading);

        tablePanel = new JPanel();
        tablePanel.setBounds(300,160,930,410);
        tablePanel.setBorder(new LineBorder(Color.gray,2));
        tablePanel.setLayout(null);
        salesFrame.add(tablePanel);

        navigationPanel = new JPanel();
        navigationPanel.setBounds(300,630,930,50);
        navigationPanel.setBorder(new LineBorder(Color.gray,2));
        navigationPanel.setLayout(null);
        salesFrame.add(navigationPanel);

        navigationPanel1 = new JPanel();
        navigationPanel1.setBounds(300,575,930,50);
        navigationPanel1.setBorder(new LineBorder(Color.gray,2));
        navigationPanel1.setLayout(null);
        salesFrame.add(navigationPanel1);
        Long amount = 0L;
        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Sum(Amount) FROM Sales;");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            amount = resultSet.getLong(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        totalAmount = new JLabel("Total Amount : " + amount);
        totalAmount.setBounds(5,5,200,40);
        totalAmount.setFont(new Font("monospaced",Font.BOLD,16));
        totalAmount.setHorizontalAlignment(SwingConstants.CENTER);
        totalAmount.setBorder(new LineBorder(Color.GRAY,2));
        navigationPanel1.add(totalAmount);

        gst = new JLabel("GST (10%) : " + (amount - ((amount * 30) / 100)) / 10);
        gst.setBounds(725,5,200,40);
        gst.setFont(new Font("monospaced",Font.BOLD,16));
        gst.setHorizontalAlignment(SwingConstants.CENTER);
        gst.setBorder(new LineBorder(Color.GRAY,2));
        navigationPanel1.add(gst);

        profit = new JLabel("Profit (30%) : " + (amount * 30) / 100);
        profit.setBounds(365,5,200,40);
        profit.setFont(new Font("monospaced",Font.BOLD,16));
        profit.setHorizontalAlignment(SwingConstants.CENTER);
        profit.setBorder(new LineBorder(Color.GRAY,2));
        navigationPanel1.add(profit);

        back = new JButton("Back");
        back.setBounds(375,5,180,40);
        back.setFont(new Font("monospaced",Font.BOLD,25));
        back.setFocusable(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(back);

        back.addActionListener(e -> {
            new Dashboard();
            salesFrame.dispose();
        });

        showTable();

        salesFrame.setVisible(true);
    }

    public void showTable() {
        String tableColumns [] = {"SALES ID", "CUSTOMER ID", "CUSTOMER NAME", "CUSTOMER MOBILE No", "DATE", "TOTAL AMOUNT"};
        String tableData [][] = new String[0][0];
        int count = 0;
        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) From Sales;");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            System.out.println(count);

            tableData = new String[count][6];

            preparedStatement = jdbc.connection.prepareStatement("SELECT Id,CustomerId,CustomerName,CustomerMobile,Date,Amount from Sales;");
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
        } catch(Exception e) {
            e.printStackTrace();
        }

        productsTable = new JTable(tableData,tableColumns);
        productsTable.setFont(new Font("monospaced",Font.BOLD,18));
        productsTable.setRowHeight(35);
        scrollPane = new JScrollPane(productsTable);
        scrollPane.setBounds(5,5,920,400);
        tablePanel.add(scrollPane);

        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(productsTable.getValueAt(productsTable.getSelectedRow(),0));
                salesFrame.dispose();
                new CustomersSalesReport(productsTable);
            }
        });

        tablePanel.setVisible(true);
    }
}
