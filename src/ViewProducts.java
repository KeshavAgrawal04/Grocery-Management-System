import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewProducts {
    JFrame viewFrame;
    JLabel heading;
    JPanel tablePanel,navigationPanel;
    JButton back;
    JScrollPane scrollPane;
    JTable productsTable;
    JDBC jdbc;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public ViewProducts() {
        viewFrame = new JFrame();
        viewFrame.setSize(1920,1080);
        viewFrame.setDefaultCloseOperation(viewFrame.EXIT_ON_CLOSE);
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setLayout(null);
        viewFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        heading = new JLabel("All Products From Store");
        heading.setBounds(300,110,930,40);
        heading.setFont(new Font("monospaced",Font.BOLD,25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.GRAY,2));
        viewFrame.add(heading);

        tablePanel = new JPanel();
        tablePanel.setBounds(300,160,930,460);
        tablePanel.setBorder(new LineBorder(Color.gray,2));
        tablePanel.setLayout(null);
        viewFrame.add(tablePanel);

        navigationPanel = new JPanel();
        navigationPanel.setBounds(300,630,930,50);
        navigationPanel.setBorder(new LineBorder(Color.gray,2));
        navigationPanel.setLayout(null);
        viewFrame.add(navigationPanel);

        back = new JButton("Back");
        back.setBounds(375,5,180,40);
        back.setFont(new Font("monospaced",Font.BOLD,25));
        back.setFocusable(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(back);

        back.addActionListener(e -> {
            new Dashboard();
            viewFrame.dispose();
        });

        showTable();

        viewFrame.setVisible(true);
    }

    public void showTable() {
        String tableColumns [] = {"PRODUCT ID", "PRODUCT NAME", "PRICE", "COMPANY NAME", "QUANTITY"};
        String tableData [][] = new String[0][0];
        int count = 0;
        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) From Products;");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            System.out.println(count);

            tableData = new String[count][5];

            preparedStatement = jdbc.connection.prepareStatement("SELECT Id, Name, Price, BrandName, Quantity from Products;");
            resultSet = preparedStatement.executeQuery();

            for (int i = 0; i < count; i++) {
                resultSet.next();
                tableData[i][0] = resultSet.getString(1);
                tableData[i][1] = resultSet.getString(2);
                tableData[i][2] = resultSet.getString(3);
                tableData[i][3] = resultSet.getString(4);
                tableData[i][4] = resultSet.getString(5);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        productsTable = new JTable(tableData,tableColumns);
        productsTable.setFont(new Font("monospaced",Font.BOLD,18));
        productsTable.setRowHeight(35);
        productsTable.setEnabled(false);
        scrollPane = new JScrollPane(productsTable);
        scrollPane.setBounds(5,5,920,450);
        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);
    }
}
