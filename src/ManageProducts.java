import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageProducts {
    JFrame manageProductFrame;
    JLabel heading, subHeading, productNameL, productCostL, productBrandL, productQtyL, subHeading1;
    JLabel productNameL1, productQtyL1;
    JTextField productCostF1, productQtyF1;
    JTextField productNameF, productCostF, productBrandF, productQtyF;
    JPanel productPanel, navigationPanel, navigationPanel1, tablePanel;
    JButton addProduct, clear, back, clear1, editProduct;
    Product product;
    JComboBox productNameF1;
    JDBC jdbc;
    JTable productsTable;
    JScrollPane scrollPane;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    String[] productName;

    public ManageProducts() {
        manageProductFrame = new JFrame();
        manageProductFrame.setSize(1920, 1080);
        manageProductFrame.setDefaultCloseOperation(manageProductFrame.EXIT_ON_CLOSE);
        manageProductFrame.setLocationRelativeTo(null);
        manageProductFrame.setLayout(null);
        manageProductFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        manageProductFrame.setUndecorated(true);

        heading = new JLabel("Manage Products");
        heading.setBounds(300, 110, 930, 40);
        heading.setFont(new Font("monospaced", Font.BOLD, 25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.GRAY, 2));
        manageProductFrame.add(heading);

        productPanel = new JPanel();
        productPanel.setBounds(300, 160, 460, 280);
        productPanel.setBorder(new LineBorder(Color.gray, 2));
        productPanel.setLayout(null);
        manageProductFrame.add(productPanel);

        navigationPanel1 = new JPanel();
        navigationPanel1.setBounds(770, 160, 460, 280);
        navigationPanel1.setBorder(new LineBorder(Color.gray, 2));
        navigationPanel1.setLayout(null);
        manageProductFrame.add(navigationPanel1);

        subHeading1 = new JLabel("Edit Product");
        subHeading1.setBounds(10, 10, 420, 40);
        subHeading1.setHorizontalAlignment(SwingConstants.CENTER);
        subHeading1.setFont(new Font("monospaced", Font.BOLD, 22));
        navigationPanel1.add(subHeading1);

        productNameL1 = new JLabel("Product Name");
        productNameL1.setBounds(10, 60, 200, 40);
        productNameL1.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(productNameL1);

        int count = 0;
        int i = 1;

        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) From Products;");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

            productName = new String[count + 1];
            resultSet = null;

            statement = jdbc.connection.createStatement();
            resultSet = statement.executeQuery("SELECT Name FROM Products;");
            System.out.println("Working");
            while (resultSet.next()) {
                productName[0] = "Select";
                productName[i] = resultSet.getString("Name");
                i++;
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }

        productNameF1 = new JComboBox(productName);
        productNameF1.setBounds(230, 70, 220, 30);
        productNameF1.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(productNameF1);

        productQtyL1 = new JLabel("Product Qty");
        productQtyL1.setBounds(10, 100, 200, 40);
        productQtyL1.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(productQtyL1);

        productQtyF1 = new JTextField();
        productQtyF1.setBounds(230, 110, 220, 30);
        productQtyF1.setFont(new Font("monospaced", Font.BOLD, 18));
        navigationPanel1.add(productQtyF1);

        clear1 = new JButton("Clear");
        clear1.setBounds(10, 230, 180, 40);
        clear1.setFont(new Font("monospaced", Font.BOLD, 16));
        clear1.setFocusable(false);
        clear1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel1.add(clear1);

        clear1.addActionListener(e -> {
            productNameF1.setSelectedIndex(0);
            productCostF1.setText("");
            productQtyF1.setText("");
        });

        editProduct = new JButton("Edit Product");
        editProduct.setBounds(230, 230, 220, 40);
        editProduct.setFont(new Font("monospaced", Font.BOLD, 16));
        editProduct.setFocusable(false);
        editProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel1.add(editProduct);

        editProduct.addActionListener(e -> {
            int count1 = 0;
            long productQuantity = 0L;
            String productNameS = productNameF1.getSelectedItem().toString();

            if(productNameF1.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(manageProductFrame,"Select An Product");
            } else {
                try {
                    jdbc = new JDBC();
                    jdbc.setConnection();
                    System.out.println(productName);
                    if (productQtyF1.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(manageProductFrame,"Quantity Field Is Empty Fill It First");
                    } else {
                        productQuantity = Long.parseLong(productQtyF1.getText());
                        try {
                            preparedStatement = jdbc.connection.prepareStatement("UPDATE Products SET Quantity = Quantity + " + productQuantity + " WHERE Name = '" + productNameS + "';");
                            preparedStatement.executeUpdate();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    JOptionPane.showMessageDialog(manageProductFrame,"Product Data Edited");
                    manageProductFrame.dispose();
                    new ManageProducts();
                    showTable();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
        });

        subHeading = new JLabel("Add Product");
        subHeading.setBounds(10, 10, 480, 40);
        subHeading.setHorizontalAlignment(SwingConstants.CENTER);
        subHeading.setFont(new Font("monospaced", Font.BOLD, 22));
        productPanel.add(subHeading);

        productNameL = new JLabel("Product Name");
        productNameL.setBounds(10, 60, 200, 40);
        productNameL.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productNameL);

        productNameF = new JTextField();
        productNameF.setBounds(230, 70, 220, 30);
        productNameF.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productNameF);

        productCostL = new JLabel("Product Cost");
        productCostL.setBounds(10, 100, 200, 40);
        productCostL.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productCostL);

        productCostF = new JTextField();
        productCostF.setBounds(230, 110, 220, 30);
        productCostF.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productCostF);

        productBrandL = new JLabel("Product Brand");
        productBrandL.setBounds(10, 140, 200, 40);
        productBrandL.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productBrandL);

        productBrandF = new JTextField();
        productBrandF.setBounds(230, 150, 220, 30);
        productBrandF.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productBrandF);

        productQtyL = new JLabel("Product Qty");
        productQtyL.setBounds(10, 180, 200, 40);
        productQtyL.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productQtyL);

        productQtyF = new JTextField();
        productQtyF.setBounds(230, 190, 220, 30);
        productQtyF.setFont(new Font("monospaced", Font.BOLD, 18));
        productPanel.add(productQtyF);

        clear = new JButton("Clear");
        clear.setBounds(10, 230, 180, 40);
        clear.setFont(new Font("monospaced", Font.BOLD, 16));
        clear.setFocusable(false);
        clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productPanel.add(clear);

        clear.addActionListener(e -> {
            productNameF.setText("");
            productCostF.setText("");
            productBrandF.setText("");
            productQtyF.setText("");
        });

        addProduct = new JButton("Add Product");
        addProduct.setBounds(230, 230, 220, 40);
        addProduct.setFont(new Font("monospaced", Font.BOLD, 16));
        addProduct.setFocusable(false);
        addProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productPanel.add(addProduct);

        tablePanel = new JPanel();
        tablePanel.setBounds(300, 450, 930, 200);
        tablePanel.setBorder(new LineBorder(Color.gray, 2));
        tablePanel.setLayout(null);
        manageProductFrame.add(tablePanel);

        showTable();

        addProduct.addActionListener(e -> {
            if (productNameF.getText().isEmpty() && productCostF.getText().isEmpty() && productQtyF.getText().isEmpty() && productBrandF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(manageProductFrame, "Fields Are Empty Fill Them First");
            } else {
                product = new Product();
                product.productName = productNameF.getText();
                product.brandName = productBrandF.getText();
                product.cost = Long.parseLong(productCostF.getText());
                long temp = product.cost / 10;
                product.cost = product.cost + temp;
                product.price = (product.cost / 3) + product.cost;
                product.quantity = Integer.parseInt(productQtyF.getText());

                try {
                    jdbc = new JDBC();
                    jdbc.setConnection();
                    preparedStatement = jdbc.connection.prepareStatement("INSERT INTO Products (Name, BrandName, Cost, Price, Quantity) values(?, ?, ?, ?, ?);");

                    preparedStatement.setString(1, product.productName);
                    preparedStatement.setString(2, product.brandName);
                    preparedStatement.setLong(3, product.cost);
                    preparedStatement.setLong(4, product.price);
                    preparedStatement.setLong(5, product.quantity);

                    preparedStatement.executeUpdate();
                    System.out.println("Data Inserted");
                    new ManageProducts();
                    manageProductFrame.dispose();
                    showTable();
                } catch (Exception e1) {
                    System.out.println("Error In Data Insertion");
                }
            }
        });

        navigationPanel = new JPanel();
        navigationPanel.setBounds(300, 660, 930, 50);
        navigationPanel.setBorder(new LineBorder(Color.gray, 2));
        navigationPanel.setLayout(null);
        manageProductFrame.add(navigationPanel);

        back = new JButton("Back");
        back.setBounds(375, 5, 180, 40);
        back.setFont(new Font("monospaced", Font.BOLD, 25));
        back.setFocusable(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(back);

        back.addActionListener(e -> {
            new Dashboard();
            manageProductFrame.dispose();
        });

        manageProductFrame.setVisible(true);
    }

    public void showTable() {
        String tableColumns[] = {"PRODUCT ID", "PRODUCT NAME", "COST", "PRICE", "COMPANY NAME", "QUANTITY"};
        String tableData[][] = new String[0][0];
        int count = 0;
        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) From Products;");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            System.out.println(count);

            tableData = new String[count][6];

            preparedStatement = jdbc.connection.prepareStatement("SELECT * from Products;");
            resultSet = preparedStatement.executeQuery();

            for (int i = 0; i < count; i++) {
                resultSet.next();
                tableData[i][0] = resultSet.getString(1);
                tableData[i][1] = resultSet.getString(2);
                tableData[i][2] = resultSet.getString(4);
                tableData[i][3] = resultSet.getString(5);
                tableData[i][4] = resultSet.getString(3);
                tableData[i][5] = resultSet.getString(6);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productsTable = new JTable(tableData, tableColumns);
        productsTable.setFont(new Font("monospaced", Font.BOLD, 18));
        productsTable.setRowHeight(35);
        scrollPane = new JScrollPane(productsTable);
        scrollPane.setBounds(5, 5, 920, 190);
        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);

        productsTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(productsTable, "Do You Want To Delete Row ?", "Deleting Row", JOptionPane.YES_NO_OPTION);
                int index = productsTable.getSelectedRow();
                if (index == -1) {
                    index = 0;
                }

                if (result == JOptionPane.YES_OPTION) {
                    Integer id = Integer.parseInt((String) productsTable.getValueAt(index, 0));
                    try {
                        preparedStatement = jdbc.connection.prepareStatement("DELETE FROM Products WHERE Id = " + id + ";");
                        preparedStatement.executeUpdate();
                        new ManageProducts().showTable();
                        manageProductFrame.dispose();
                        index = 0;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
