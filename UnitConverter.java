import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class UnitConverter extends JFrame implements ActionListener {
    
    // Conversion categories
    private String[] categories = {"Length", "Weight", "Temperature", "Area", "Volume", "Digital Storage"};
    
    // Units for each category
    private String[][] units = {
        // Length
        {"Meters", "Kilometers", "Centimeters", "Millimeters", "Miles", "Yards", "Feet", "Inches"},
        // Weight
        {"Kilograms", "Grams", "Milligrams", "Pounds", "Ounces"},
        // Temperature
        {"Celsius", "Fahrenheit", "Kelvin"},
        // Area
        {"Square Meters", "Square Kilometers", "Square Miles", "Square Feet", "Acres", "Hectares"},
        // Volume
        {"Liters", "Milliliters", "Gallons", "Quarts", "Cubic Meters", "Cubic Feet"},
        // Digital Storage
        {"Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes"}
    };
    
    // UI Components
    private JComboBox<String> categoryCombo;
    private JComboBox<String> fromUnitCombo, toUnitCombo;
    private JTextField inputField, resultField;
    private JButton convertBtn, clearBtn, swapBtn;
    private JLabel fromLabel, toLabel, resultLabel;
    
    private DecimalFormat df = new DecimalFormat("#,###.##########");
    
    public UnitConverter() {
        setTitle("Universal Unit Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initializeComponents();
        layoutComponents();
        addEventListeners();
        
        setVisible(true);
    }
    
    private void initializeComponents() {
        // ComboBoxes
        categoryCombo = new JComboBox<>(categories);
        fromUnitCombo = new JComboBox<>(units[0]);
        toUnitCombo = new JComboBox<>(units[0]);
        toUnitCombo.setSelectedIndex(1);
        
        // Text fields
        inputField = new JTextField(15);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        resultField = new JTextField(15);
        resultField.setFont(new Font("Arial", Font.PLAIN, 16));
        resultField.setEditable(false);
        resultField.setBackground(new Color(240, 240, 240));
        
        // Buttons
        convertBtn = new JButton("Convert");
        convertBtn.setBackground(new Color(70, 130, 180));
        convertBtn.setForeground(Color.WHITE);
        convertBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        clearBtn = new JButton("Clear");
        clearBtn.setBackground(new Color(220, 20, 60));
        clearBtn.setForeground(Color.WHITE);
        
        swapBtn = new JButton("⇄ Swap");
        swapBtn.setBackground(new Color(50, 150, 50));
        swapBtn.setForeground(Color.WHITE);
        
        // Labels
        fromLabel = new JLabel("From:");
        toLabel = new JLabel("To:");
        resultLabel = new JLabel("Result:");
        
        // Set fonts for labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        fromLabel.setFont(labelFont);
        toLabel.setFont(labelFont);
        resultLabel.setFont(labelFont);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Category selection
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Category:"), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(categoryCombo, gbc);
        
        // From unit
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        mainPanel.add(fromLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 1;
        mainPanel.add(fromUnitCombo, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 1;
        mainPanel.add(inputField, gbc);
        
        // To unit
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        mainPanel.add(toLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        mainPanel.add(toUnitCombo, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 1;
        mainPanel.add(resultField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        mainPanel.add(convertBtn, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        mainPanel.add(clearBtn, gbc);
        
        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1;
        mainPanel.add(swapBtn, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Info panel at bottom
        JLabel infoLabel = new JLabel("🔁 Universal Unit Converter - Convert between different measurement units", JLabel.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        add(infoLabel, BorderLayout.SOUTH);
    }
    
    private void addEventListeners() {
        categoryCombo.addActionListener(e -> updateUnitComboBoxes());
        convertBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        swapBtn.addActionListener(this);
        
        // Convert on Enter key press
        inputField.addActionListener(e -> convertUnits());
    }
    
    private void updateUnitComboBoxes() {
        int categoryIndex = categoryCombo.getSelectedIndex();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(units[categoryIndex]);
        fromUnitCombo.setModel(model);
        toUnitCombo.setModel(new DefaultComboBoxModel<>(units[categoryIndex]));
        toUnitCombo.setSelectedIndex(1);
        clearFields();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertBtn) {
            convertUnits();
        } else if (e.getSource() == clearBtn) {
            clearFields();
        } else if (e.getSource() == swapBtn) {
            swapUnits();
        }
    }
    
    private void convertUnits() {
        try {
            String inputText = inputField.getText().trim();
            if (inputText.isEmpty()) {
                resultField.setText("Enter a value");
                return;
            }
            
            double inputValue = Double.parseDouble(inputText);
            int categoryIndex = categoryCombo.getSelectedIndex();
            String fromUnit = (String) fromUnitCombo.getSelectedItem();
            String toUnit = (String) toUnitCombo.getSelectedItem();
            
            double result = performConversion(inputValue, categoryIndex, fromUnit, toUnit);
            resultField.setText(df.format(result));
            
        } catch (NumberFormatException ex) {
            resultField.setText("Invalid number");
        } catch (Exception ex) {
            resultField.setText("Conversion error");
        }
    }
    
    private double performConversion(double value, int category, String fromUnit, String toUnit) {
        // Convert to base unit first, then to target unit
        double baseValue = toBaseUnit(value, category, fromUnit);
        return fromBaseUnit(baseValue, category, toUnit);
    }
    
    private double toBaseUnit(double value, int category, String unit) {
        switch (category) {
            case 0: // Length - base: meters
                switch (unit) {
                    case "Meters": return value;
                    case "Kilometers": return value * 1000;
                    case "Centimeters": return value / 100;
                    case "Millimeters": return value / 1000;
                    case "Miles": return value * 1609.34;
                    case "Yards": return value * 0.9144;
                    case "Feet": return value * 0.3048;
                    case "Inches": return value * 0.0254;
                    default: return value;
                }
                
            case 1: // Weight - base: kilograms
                switch (unit) {
                    case "Kilograms": return value;
                    case "Grams": return value / 1000;
                    case "Milligrams": return value / 1000000;
                    case "Pounds": return value * 0.453592;
                    case "Ounces": return value * 0.0283495;
                    default: return value;
                }
                
            case 2: // Temperature - special handling
                switch (unit) {
                    case "Celsius": return value;
                    case "Fahrenheit": return (value - 32) * 5/9;
                    case "Kelvin": return value - 273.15;
                    default: return value;
                }
                
            case 3: // Area - base: square meters
                switch (unit) {
                    case "Square Meters": return value;
                    case "Square Kilometers": return value * 1000000;
                    case "Square Miles": return value * 2589988.11;
                    case "Square Feet": return value * 0.092903;
                    case "Acres": return value * 4046.86;
                    case "Hectares": return value * 10000;
                    default: return value;
                }
                
            case 4: // Volume - base: liters
                switch (unit) {
                    case "Liters": return value;
                    case "Milliliters": return value / 1000;
                    case "Gallons": return value * 3.78541;
                    case "Quarts": return value * 0.946353;
                    case "Cubic Meters": return value * 1000;
                    case "Cubic Feet": return value * 28.3168;
                    default: return value;
                }
                
            case 5: // Digital Storage - base: bytes
                switch (unit) {
                    case "Bytes": return value;
                    case "Kilobytes": return value * 1024;
                    case "Megabytes": return value * 1024 * 1024;
                    case "Gigabytes": return value * 1024 * 1024 * 1024;
                    case "Terabytes": return value * 1024 * 1024 * 1024 * 1024;
                    default: return value;
                }
                
            default: return value;
        }
    }
    
    private double fromBaseUnit(double value, int category, String unit) {
        switch (category) {
            case 0: // Length
                switch (unit) {
                    case "Meters": return value;
                    case "Kilometers": return value / 1000;
                    case "Centimeters": return value * 100;
                    case "Millimeters": return value * 1000;
                    case "Miles": return value / 1609.34;
                    case "Yards": return value / 0.9144;
                    case "Feet": return value / 0.3048;
                    case "Inches": return value / 0.0254;
                    default: return value;
                }
                
            case 1: // Weight
                switch (unit) {
                    case "Kilograms": return value;
                    case "Grams": return value * 1000;
                    case "Milligrams": return value * 1000000;
                    case "Pounds": return value / 0.453592;
                    case "Ounces": return value / 0.0283495;
                    default: return value;
                }
                
            case 2: // Temperature
                switch (unit) {
                    case "Celsius": return value;
                    case "Fahrenheit": return (value * 9/5) + 32;
                    case "Kelvin": return value + 273.15;
                    default: return value;
                }
                
            case 3: // Area
                switch (unit) {
                    case "Square Meters": return value;
                    case "Square Kilometers": return value / 1000000;
                    case "Square Miles": return value / 2589988.11;
                    case "Square Feet": return value / 0.092903;
                    case "Acres": return value / 4046.86;
                    case "Hectares": return value / 10000;
                    default: return value;
                }
                
            case 4: // Volume
                switch (unit) {
                    case "Liters": return value;
                    case "Milliliters": return value * 1000;
                    case "Gallons": return value / 3.78541;
                    case "Quarts": return value / 0.946353;
                    case "Cubic Meters": return value / 1000;
                    case "Cubic Feet": return value / 28.3168;
                    default: return value;
                }
                
            case 5: // Digital Storage
                switch (unit) {
                    case "Bytes": return value;
                    case "Kilobytes": return value / 1024;
                    case "Megabytes": return value / (1024 * 1024);
                    case "Gigabytes": return value / (1024 * 1024 * 1024);
                    case "Terabytes": return value / (1024 * 1024 * 1024 * 1024);
                    default: return value;
                }
                
            default: return value;
        }
    }
    
    private void clearFields() {
        inputField.setText("");
        resultField.setText("");
        inputField.requestFocus();
    }
    
    private void swapUnits() {
        String fromUnit = (String) fromUnitCombo.getSelectedItem();
        String toUnit = (String) toUnitCombo.getSelectedItem();
        
        fromUnitCombo.setSelectedItem(toUnit);
        toUnitCombo.setSelectedItem(fromUnit);
        
        // Also swap the values if they exist
        if (!resultField.getText().isEmpty() && !resultField.getText().equals("Enter a value")) {
            String temp = inputField.getText();
            inputField.setText(resultField.getText());
            resultField.setText(temp);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new UnitConverter());
    }
}