import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PurchaseProducts {
    JFrame purchaseFrame;
    JLabel heading, subHeading, customerNameL, customerMobileL, subHeading2, productNameL, qtyL, price, priceF;
    JTextField customerNameF, customerMobileF, qtyF;
    JPanel customerPanel, tablePanel, navigationPanel, purchasePanel;
    JComboBox productName;
    JButton startBilling, clear, addToCart, clear1, save, back;
    JScrollPane scrollPane;
    JTable productsTable;
    JDBC jdbc;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    Statement statement;
    String products[];
    Customers customer;
    int salesId;
    int customerId;

    public PurchaseProducts() {
        customer = new Customers();

        purchaseFrame = new JFrame();
        purchaseFrame.setSize(1920, 1080);
        purchaseFrame.setDefaultCloseOperation(purchaseFrame.EXIT_ON_CLOSE);
        purchaseFrame.setLocationRelativeTo(null);
        purchaseFrame.setLayout(null);
        purchaseFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        purchaseFrame.setUndecorated(true);

        heading = new JLabel("Purchase Products");
        heading.setBounds(300, 110, 930, 40);
        heading.setFont(new Font("monospaced", Font.BOLD, 25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new LineBorder(Color.GRAY, 2));
        purchaseFrame.add(heading);

        customerPanel = new JPanel();
        customerPanel.setBounds(300, 160, 470, 200);
        customerPanel.setBorder(new LineBorder(Color.gray, 2));
        customerPanel.setLayout(null);
        purchaseFrame.add(customerPanel);

        tablePanel = new JPanel();
        tablePanel.setBounds(300, 370, 930, 250);
        tablePanel.setBorder(new LineBorder(Color.gray, 2));
        tablePanel.setLayout(null);
        purchaseFrame.add(tablePanel);

        navigationPanel = new JPanel();
        navigationPanel.setBounds(300, 630, 930, 50);
        navigationPanel.setBorder(new LineBorder(Color.gray, 2));
        navigationPanel.setLayout(null);
        purchaseFrame.add(navigationPanel);

        subHeading = new JLabel("Customer Details");
        subHeading.setBounds(10, 10, 470, 40);
        subHeading.setFont(new Font("monospaced", Font.BOLD, 22));
        customerPanel.add(subHeading);

        customerNameL = new JLabel("Customer Name");
        customerNameL.setBounds(10, 60, 200, 40);
        customerNameL.setFont(new Font("monospaced", Font.BOLD, 18));
        customerPanel.add(customerNameL);

        customerNameF = new JTextField();
        customerNameF.setBounds(240, 70, 220, 30);
        customerNameF.setFont(new Font("monospaced", Font.BOLD, 18));
        customerPanel.add(customerNameF);

        customerMobileL = new JLabel("Customer Mobile No");
        customerMobileL.setBounds(10, 100, 930, 40);
        customerMobileL.setFont(new Font("monospaced", Font.BOLD, 18));
        customerPanel.add(customerMobileL);

        customerMobileF = new JTextField();
        customerMobileF.setBounds(240, 110, 220, 30);
        customerMobileF.setFont(new Font("monospaced", Font.BOLD, 18));
        customerPanel.add(customerMobileF);

        clear = new JButton("Clear");
        clear.setBounds(10, 150, 180, 40);
        clear.setFont(new Font("monospaced", Font.BOLD, 16));
        clear.setFocusable(false);
        clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        customerPanel.add(clear);

        clear.addActionListener(e -> {
            customerNameF.setText("");
            customerMobileF.setText("");
        });

        startBilling = new JButton("Start Billing");
        startBilling.setBounds(240, 150, 220, 40);
        startBilling.setFont(new Font("monospaced", Font.BOLD, 16));
        startBilling.setFocusable(false);
        startBilling.setCursor(new Cursor(Cursor.HAND_CURSOR));
        customerPanel.add(startBilling);

        startBilling.addActionListener(e -> {
            if (customerNameF.getText().isEmpty() && customerMobileF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(purchaseFrame, "Enter Customer Details");
            } else {
                try {
                    preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) FROM Customers;");
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    System.out.println(resultSet.getInt(1));
                    if(resultSet.getInt(1) == 0) {
                        jdbc.setConnection();
                        preparedStatement = jdbc.connection.prepareStatement("INSERT INTO Customers (Name, MobileNumber) VALUES(?, ?)");

                        preparedStatement.setString(1, customerNameF.getText());
                        preparedStatement.setLong(2, Long.parseLong(customerMobileF.getText()));
                        preparedStatement.executeUpdate();
                        System.out.println("Data Inserted");

                        preparedStatement = jdbc.connection.prepareStatement("SELECT Id FROM Customers;");
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        customer.id = resultSet.getInt(1);

                        System.out.println("Customer Id [New] : " + customerId);
                        customerNameF.setEditable(false);
                        customerMobileF.setEditable(false);
                        startBilling.setEnabled(false);
                        clear.setEnabled(false);
                        productName.setEnabled(true);
                        qtyF.setEnabled(true);
                        clear1.setEnabled(true);
                        addToCart.setEnabled(true);
                    } else {
                        preparedStatement = jdbc.connection.prepareStatement("SELECT Id, Name, MobileNumber FROM Customers;");
                        resultSet = preparedStatement.executeQuery();
                        while(resultSet.next()) {
                            if(resultSet.getString(2).equals(customerNameF.getText()) && resultSet.getLong(3) == Long.parseLong(customerMobileF.getText())) {
                                JOptionPane.showMessageDialog(purchaseFrame,"Customer Name Already Customer Id : " + resultSet.getInt(1));
                                customer.id = resultSet.getInt(1);
                                customerNameF.setEditable(false);
                                customerMobileF.setEditable(false);
                                startBilling.setEnabled(false);
                                clear.setEnabled(false);
                                productName.setEnabled(true);
                                qtyF.setEnabled(true);
                                clear1.setEnabled(true);
                                addToCart.setEnabled(true);
                            } else {
                                customerNameF.setEditable(false);
                                customerMobileF.setEditable(false);
                                startBilling.setEnabled(false);
                                clear.setEnabled(false);
                                productName.setEnabled(true);
                                qtyF.setEnabled(true);
                                clear1.setEnabled(true);
                                addToCart.setEnabled(true);

                                try {
                                    jdbc.setConnection();
                                    preparedStatement = jdbc.connection.prepareStatement("INSERT INTO Customers (Name, MobileNumber) VALUES(?, ?)");

                                    preparedStatement.setString(1, customerNameF.getText());
                                    preparedStatement.setLong(2, Long.parseLong(customerMobileF.getText()));
                                    preparedStatement.executeUpdate();
                                    System.out.println("Data Inserted");

                                    preparedStatement = jdbc.connection.prepareStatement("SELECT Id FROM Customers WHERE Id = (SELECT max(Id) FROM Customers);");
                                    resultSet = preparedStatement.executeQuery();
                                    resultSet.next();
                                    customer.id = resultSet.getInt(1);
                                    System.out.println("Customer Id [Old] : " + customerId);
                                } catch (Exception e1) {
                                    System.out.println("Error While Inserting Data");
                                }
                            }
                        }
                    }
                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        purchasePanel = new JPanel();
        purchasePanel.setBounds(780, 160, 450, 200);
        purchasePanel.setBorder(new LineBorder(Color.gray, 2));
        purchasePanel.setLayout(null);
        purchaseFrame.add(purchasePanel);

        subHeading2 = new JLabel("Select Products");
        subHeading2.setBounds(10, 10, 470, 40);
        subHeading2.setFont(new Font("monospaced", Font.BOLD, 22));
        purchasePanel.add(subHeading2);

        productNameL = new JLabel("Product");
        productNameL.setBounds(10, 60, 80, 40);
        productNameL.setFont(new Font("monospaced", Font.BOLD, 18));
        purchasePanel.add(productNameL);

        int count = 0;
        int i = 1;

        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) From Products;");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

            products = new String[count + 1];
            resultSet = null;

            statement = jdbc.connection.createStatement();
            resultSet = statement.executeQuery("SELECT Name FROM Products;");
            System.out.println("Working");
            while (resultSet.next()) {
                products[0] = "Select";
                products[i] = resultSet.getString("Name");
                i++;
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }

        productName = new JComboBox(products);
        productName.setBounds(100, 70, 180, 30);
        productName.setFont(new Font("monospaced", Font.BOLD, 18));
        purchasePanel.add(productName);
        productName.setEnabled(false);

        qtyL = new JLabel("Qty");
        qtyL.setBounds(300, 60, 50, 40);
        qtyL.setFont(new Font("monospaced", Font.BOLD, 18));
        purchasePanel.add(qtyL);

        qtyF = new JTextField();
        qtyF.setBounds(360, 70, 80, 30);
        qtyF.setFont(new Font("monospaced", Font.BOLD, 18));
        purchasePanel.add(qtyF);
        qtyF.setEnabled(false);

        price = new JLabel("Price");
        price.setBounds(10, 100, 100, 40);
        price.setFont(new Font("monospaced", Font.BOLD, 18));
        purchasePanel.add(price);

        priceF = new JLabel();
        priceF.setBounds(100, 110, 180, 30);
        priceF.setFont(new Font("monospaced", Font.BOLD, 18));
        priceF.setBorder(new LineBorder(Color.gray));
        purchasePanel.add(priceF);

        clear1 = new JButton("Clear");
        clear1.setBounds(10, 150, 180, 40);
        clear1.setFont(new Font("monospaced", Font.BOLD, 16));
        clear1.setFocusable(false);
        clear1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        purchasePanel.add(clear1);
        clear1.setEnabled(false);

        clear1.addActionListener(e -> {
            qtyF.setText("");
            priceF.setText("");
            productName.setSelectedIndex(0);
        });

        addToCart = new JButton("Add To Cart");
        addToCart.setBounds(220, 150, 220, 40);
        addToCart.setFont(new Font("monospaced", Font.BOLD, 16));
        addToCart.setFocusable(false);
        addToCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        purchasePanel.add(addToCart);
        addToCart.setEnabled(false);

        addToCart.addActionListener(e -> {
            if (productName.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(purchaseFrame, "Fill Details Properly");
                priceF.setText("");
                qtyF.setText("");
            } else {
                if (qtyF.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(purchaseFrame, "Fill Details Properly");
                } else {
                    int productId = 0;
                    try {
                        jdbc.setConnection();
                        String customerName = customerNameF.getText();
                        long quantity = 0L;
                        String productNameS = productName.getSelectedItem().toString();
                        resultSet = statement.executeQuery("SELECT * FROM Products WHERE Name = '" + productNameS + "';");
                        while (resultSet.next()) {
                            quantity = (resultSet.getLong("Quantity"));
                            productId = resultSet.getInt("Id");
                        }
                        if (Long.parseLong(qtyF.getText()) > quantity) {
                            JOptionPane.showMessageDialog(purchaseFrame, "Out Of Quantity Available Quantity : " + quantity);
                        } else {
//                            resultSet = statement.executeQuery("SELECT Id FROM Customers WHERE Name = '" + customerName + "';");
//                            while (resultSet.next()) {
//                                customerId = resultSet.getInt("Id");
//                            }
                            long millis = System.currentTimeMillis();
                            java.sql.Date date = new java.sql.Date(millis);
                            System.out.println(date);

                            long qty = Long.parseLong(qtyF.getText());
                            long price = Long.parseLong(priceF.getText());

                            System.out.println(qtyF.getText());
                            long temp = qty * price;
                            Long temp1 = (long) temp;
                            System.out.println("Working");

                            preparedStatement = jdbc.connection.prepareStatement("INSERT INTO temporders (CustomerId, ProductId, Quantity, ProductPrice, Date, Amount, ProductName) VALUES(?, ?, ?, ?, ?, ?, ?);");
                            preparedStatement.setInt(1, customer.id);
                            preparedStatement.setInt(2, productId);
                            preparedStatement.setLong(3, Long.parseLong(qtyF.getText()));
                            preparedStatement.setLong(4, Long.parseLong(priceF.getText()));
                            preparedStatement.setDate(5, date);
                            preparedStatement.setLong(6, temp1);
                            preparedStatement.setString(7, productName.getSelectedItem().toString());
                            preparedStatement.executeUpdate();
                            System.out.println("Working");

//                            preparedStatement = jdbc.connection.prepareStatement("SELECT Id FROM Sales WHERE id = (SELECT max(Id) FROM Sales);");


                            showTable();
                        }

                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            }
        });

        productName.addActionListener(e -> {
            if (productName.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(purchaseFrame, "Select An Product First");
            } else {
                int productId = 0;
                String productNameS = productName.getSelectedItem().toString();
                System.out.println(productNameS);

                jdbc.setConnection();
                try {
                    resultSet = statement.executeQuery("SELECT * FROM Products WHERE Name = '" + productNameS + "';");
                    priceF.setText("");
                    while (resultSet.next()) {
                        priceF.setText(String.valueOf(resultSet.getLong("Price")));
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        save = new JButton("Save");
        save.setBounds(445, 5, 180, 40);
        save.setFont(new Font("monospaced", Font.BOLD, 25));
        save.setFocusable(false);
        save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(save);

        save.addActionListener(e -> {
            showTable();
            if (customerNameF.getText().isEmpty() && customerMobileF.getText().isEmpty() && (productsTable.getRowCount() <= 0)) {
                JOptionPane.showMessageDialog(purchaseFrame, "Please Fill All Details And Purchase At Least 1 Product");
            } else {
                int rowCount = productsTable.getRowCount();
                System.out.println("Row Count " + rowCount);
                try {
                    jdbc.setConnection();
                    for (int j = 0; j < rowCount; j++) {
                        long productId = Long.parseLong((String) productsTable.getValueAt(j, 0));
                        long productQuantity = Long.parseLong((String) productsTable.getValueAt(j, 3));

                        preparedStatement = jdbc.connection.prepareStatement("UPDATE Products SET Quantity = Quantity - " + productQuantity + " WHERE Id = " + productId);
                        preparedStatement.executeUpdate();
                        System.out.println("Done");
                        long millis = System.currentTimeMillis();
                        java.sql.Date date = new java.sql.Date(millis);
                        System.out.println(date);

                        preparedStatement = jdbc.connection.prepareStatement("INSERT INTO Sales (CustomerId, CustomerName, CustomerMobile , Date, Amount) VALUES(?, ?, ?, ?, ?);");
                        preparedStatement.setInt(1, customer.id);
                        preparedStatement.setString(2, customerNameF.getText());
                        preparedStatement.setLong(3, Long.parseLong(customerMobileF.getText()));
                        preparedStatement.setDate(4, date);
                    }
                    resultSet = jdbc.connection.prepareStatement("select sum(Amount) from temporders where CustomerId = " + customer.id + ";").executeQuery();
                    resultSet.next();

                    Long totalAmount = resultSet.getLong(1);
                    System.out.println(totalAmount);
                    preparedStatement.setLong(5, totalAmount);
                    preparedStatement.executeUpdate();
                    System.out.println("Sales Done");

                    preparedStatement = jdbc.connection.prepareStatement("SELECT Id FROM Sales WHERE Id = (SELECT max(Id) FROM Sales);");
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    salesId = resultSet.getInt(1);

                    preparedStatement = jdbc.connection.prepareStatement("UPDATE temporders SET SalesId = " + salesId + ";");
                    preparedStatement.executeUpdate();
                    System.out.println("Sales Id : " + salesId);

                    preparedStatement = jdbc.connection.prepareStatement("INSERT orders SELECT * FROM temporders");
                    preparedStatement.executeUpdate();

                    preparedStatement = jdbc.connection.prepareStatement("TRUNCATE temporders");
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(purchaseFrame, "Thanks For Purchase");

                    new Dashboard();
                    purchaseFrame.dispose();
                } catch (Exception e3) {
                    System.out.println("Sales Exception ");
                    e3.printStackTrace();
                }
            }
        });

        back = new JButton("Back");
        back.setBounds(225, 5, 180, 40);
        back.setFont(new Font("monospaced", Font.BOLD, 25));
        back.setFocusable(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigationPanel.add(back);
        showTable();

        back.addActionListener(e -> {
            purchaseFrame.dispose();
            new Dashboard();
        });
        purchaseFrame.setVisible(true);
    }

    public void showTable() {
        String tableColumns[] = {"PRODUCT ID", "PRODUCT NAME", "PRICE", "QUANTITY", "TOTAL AMOUNT"};
        String tableData[][] = new String[0][0];
        int count = 0;
        try {
            jdbc = new JDBC();
            jdbc.setConnection();
            preparedStatement = jdbc.connection.prepareStatement("SELECT Count(*) From temporders WHERE CustomerId = " + customer.id + ";");
            System.out.println( + customerId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            System.out.println(count);
            tableData = new String[count][5];

            resultSet = statement.executeQuery("SELECT ProductId, ProductName, ProductPrice, Quantity, Amount from temporders WHERE CustomerId = " + customer.id + ";");

            for (int i = 0; i < count; i++) {
                resultSet.next();
                tableData[i][0] = resultSet.getString(1);
                tableData[i][1] = resultSet.getString(2);
                tableData[i][2] = resultSet.getString(3);
                tableData[i][3] = resultSet.getString(4);
                tableData[i][4] = resultSet.getString(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productsTable = new JTable(tableData, tableColumns);
        productsTable.setFont(new Font("monospaced", Font.BOLD, 18));
        productsTable.setRowHeight(35);
        productsTable.setEnabled(false);
        scrollPane = new JScrollPane(productsTable);
        scrollPane.setBounds(5, 5, 920, 240);
        tablePanel.add(scrollPane);

        tablePanel.setVisible(true);
    }
}
