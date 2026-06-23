package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Calculator extends JFrame implements ActionListener {

    static final Color BG_DARK = new Color(0x1C1C1E);
    static final Color DISPLAY_TEXT = new Color(245,245,245);
    static final Color EXPR_TEXT = new Color(0x8E8E93);

    static final Color FUNC_BG = new Color(0xA5A5A5);
    static final Color FUNC_FG = Color.BLACK;

    static final Color DIGIT_BG = new Color(0x333333);
    static final Color DIGIT_FG = Color.WHITE;

    static final Color OP_BG = new Color(0xFF9F0A);
    static final Color OP_FG = Color.WHITE;

    static final Color MEM_BG = new Color(0x2C2C2E);
    static final Color MEM_FG = new Color(0xD1D1D6);

    static final Color CONV_BG = new Color(0x3A3A3C);

    JLabel expressionLabel = new JLabel(" ");
    JLabel display = new JLabel("0");

    double firstOperand = 0;
    double memoryValue = 0;
    String operator = "";
    boolean newInput = true;

    public Calculator() {
        setTitle("My Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_DARK);

        add(buildDisplayPanel(), BorderLayout.NORTH);
        add(buildKeypadPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel buildDisplayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 20, 15, 20));

        expressionLabel.setForeground(EXPR_TEXT);
        expressionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        expressionLabel.setHorizontalAlignment(JLabel.RIGHT);

        display.setForeground(DISPLAY_TEXT);
        display.setFont(new Font("SansSerif", Font.BOLD, 60));
        display.setHorizontalAlignment(JLabel.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 20));

        JPanel exprRow = new JPanel(new BorderLayout());
        exprRow.setBackground(BG_DARK);
        exprRow.add(expressionLabel, BorderLayout.EAST);

        JPanel displayRow = new JPanel(new BorderLayout());
        displayRow.setBackground(BG_DARK);
        displayRow.add(display, BorderLayout.EAST);

        panel.add(exprRow);
        panel.add(displayRow);

        return panel;
    }

    private JPanel buildKeypadPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setBackground(BG_DARK);
        outer.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

        JPanel memRow = new JPanel(new GridLayout(1, 4, 8, 0));
        memRow.setBackground(BG_DARK);

        memRow.add(roundedButton("MC", MEM_BG, MEM_FG, 15));
        memRow.add(roundedButton("MR", MEM_BG, MEM_FG, 15));
        memRow.add(roundedButton("M+", MEM_BG, MEM_FG, 15));
        memRow.add(roundedButton("M-", MEM_BG, MEM_FG, 15));

        memRow.setPreferredSize(new Dimension(10, 35));

        JPanel grid = new JPanel(new GridLayout(6, 4, 10, 10));
        grid.setBackground(BG_DARK);

        addKey(grid, "AC", FUNC_BG, FUNC_FG);
        addKey(grid, "+/−", FUNC_BG, FUNC_FG);
        addKey(grid, "%", FUNC_BG, FUNC_FG);
        addKey(grid, "÷", OP_BG, OP_FG);

        addKey(grid, "7", DIGIT_BG, DIGIT_FG);
        addKey(grid, "8", DIGIT_BG, DIGIT_FG);
        addKey(grid, "9", DIGIT_BG, DIGIT_FG);
        addKey(grid, "×", OP_BG, OP_FG);

        addKey(grid, "4", DIGIT_BG, DIGIT_FG);
        addKey(grid, "5", DIGIT_BG, DIGIT_FG);
        addKey(grid, "6", DIGIT_BG, DIGIT_FG);
        addKey(grid, "−", OP_BG, OP_FG);

        addKey(grid, "1", DIGIT_BG, DIGIT_FG);
        addKey(grid, "2", DIGIT_BG, DIGIT_FG);
        addKey(grid, "3", DIGIT_BG, DIGIT_FG);
        addKey(grid, "+", OP_BG, OP_FG);

        addKey(grid, "√", DIGIT_BG, DIGIT_FG);
        addKey(grid, "0", DIGIT_BG, DIGIT_FG);
        addKey(grid, ".", DIGIT_BG, DIGIT_FG);
        addKey(grid, "=", OP_BG, OP_FG);

        JButton powerBtn = roundedButton("xʸ", OP_BG, OP_FG, 18);
        JButton convBtn = roundedButton("Conv", CONV_BG, Color.WHITE, 16);

        grid.add(spanTwo(powerBtn));
        grid.add(spanTwo(convBtn));

        outer.add(memRow, BorderLayout.NORTH);
        outer.add(grid, BorderLayout.CENTER);

        return outer;
    }

    private JPanel spanTwo(JButton btn) {
        JPanel p = new JPanel(new GridLayout(1,1));
        p.setBackground(BG_DARK);
        p.add(btn);
        return p;
    }

    private void addKey(JPanel panel, String text, Color bg, Color fg) {
        panel.add(roundedButton(text, bg, fg, 20));
    }

    private JButton roundedButton(String label, Color bg, Color fg, int fontSize) {
        JButton btn = new JButton(label) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color hover = (Color) getClientProperty("hoverColor");
                Color fill;

                if (getModel().isPressed()) {
                    fill = bg.darker();
                } else if (hover != null) {
                    fill = hover;
                } else {
                    fill = bg;
                }

                g2.setColor(fill);
                int arc = Math.min(getWidth(), getHeight());
                g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),arc,arc));
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.putClientProperty("hoverColor",
                        new Color(
                                Math.min(bg.getRed()+25,255),
                                Math.min(bg.getGreen()+25,255),
                                Math.min(bg.getBlue()+25,255)));
                btn.repaint();
            }

            public void mouseExited(MouseEvent e) {
                btn.putClientProperty("hoverColor", null);
                btn.repaint();
            }
        });

        btn.addActionListener(this);
        return btn;
    }

    private String formatNumber(double value) {
        if (value == Math.floor(value))
            return String.valueOf((long)value);
        return String.valueOf(value);
    }

    private double currentDisplayValue() {
        try {
            return Double.parseDouble(display.getText());
        } catch(Exception e) {
            return 0;
        }
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd.matches("[0-9.]")) {
            if(newInput) {
                display.setText(cmd.equals(".") ? "0." : cmd);
                newInput = false;
            } else {
                if(cmd.equals(".") && display.getText().contains(".")) return;
                display.setText(display.getText() + cmd);
            }
            return;
        }

        switch(cmd) {
            case "AC" -> {
                display.setText("0");
                expressionLabel.setText(" ");
                operator = "";
                firstOperand = 0;
                newInput = true;
            }

            case "+/−" -> display.setText(formatNumber(-currentDisplayValue()));

            case "%" -> {
                display.setText(formatNumber(currentDisplayValue()/100));
                newInput = true;
            }

            case "√" -> {
                display.setText(formatNumber(Math.sqrt(currentDisplayValue())));
                newInput = true;
            }

            case "MC" -> memoryValue = 0;
            case "MR" -> display.setText(formatNumber(memoryValue));
            case "M+" -> memoryValue += currentDisplayValue();
            case "M-" -> memoryValue -= currentDisplayValue();

            case "Conv" -> JOptionPane.showMessageDialog(this,
                    "Converter Window Placeholder");

            case "+","−","×","÷","xʸ" -> {
                firstOperand = currentDisplayValue();
                operator = cmd;
                expressionLabel.setText(formatNumber(firstOperand) + " " + cmd);
                newInput = true;
            }

            case "=" -> {
                double second = currentDisplayValue();
                double result = switch(operator) {
                    case "+" -> firstOperand + second;
                    case "−" -> firstOperand - second;
                    case "×" -> firstOperand * second;
                    case "÷" -> firstOperand / second;
                    case "xʸ" -> Math.pow(firstOperand, second);
                    default -> second;
                };

                display.setText(formatNumber(result));
                expressionLabel.setText("");
                operator = "";
                newInput = true;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
