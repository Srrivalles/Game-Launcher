import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import org.json.*;
import java.io.*;
import java.nio.file.*;
import com.formdev.flatlaf.FlatDarkLaf;



public class GameLauncher extends JFrame {

    private static final String USER_DATA_FILE = "users.json";
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Image backgroundImage;

    public GameLauncher() {
        setTitle("Game Launcher");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/images/casa_assombrada_area_trabalho.png")).getImage());

        // Carrega a imagem de background
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/images/casa_assombrada.png")).getImage();
        } catch (Exception e) {
            System.out.println("Imagem de fundo não encontrada, usando fundo padrão");
            backgroundImage = null;
        }


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        mainPanel.add(createWelcomePanel(), "welcome");
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegisterPanel(), "register");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel("BEM-VINDO AO LAUNCHER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn = createStyledButton("LOGIN");
        loginBtn.setMaximumSize(new Dimension(300, 40)); // largura fixa
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        JButton registerBtn = createStyledButton("CADASTRO");
        registerBtn.setMaximumSize(new Dimension(300, 40)); // largura fixa
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        panel.add(Box.createVerticalGlue()); // empurra para o centro
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(registerBtn);
        panel.add(Box.createVerticalGlue()); // empurra para baixo

        return panel;
    }


    private JPanel createLoginPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(60, 200, 60, 200)); // margem para centralização

        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Usuário ou Email", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Senha", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);

        JButton loginBtn = createStyledButton("Entrar");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(200, 40));
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                // abrir launcher principal
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = createStyledButton("Voltar ao Menu");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setMaximumSize(new Dimension(200, 40));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        JButton forgotBtn = new JButton("Esqueci a senha");
        styleLinkButton(forgotBtn);
        forgotBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento"));

        JButton helpBtn = new JButton("Ajuda");
        styleLinkButton(helpBtn);
        helpBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Contate o suporte: suporte@jogo.com"));

        JPanel bottomLinks = new JPanel();
        bottomLinks.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomLinks.setOpaque(false);
        bottomLinks.add(forgotBtn);
        bottomLinks.add(helpBtn);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bottomLinks);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backBtn);
        panel.add(Box.createVerticalGlue());

        return panel;
    }



    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panel.setOpaque(false);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setBackground(Color.DARK_GRAY);
        tabbedPane.setOpaque(true);

        // Campos compartilhados
        JTextField nicknameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        JTextField fullNameField = new JTextField();
        JTextField birthDateField = new JTextField();
        JComboBox<String> countryCombo = new JComboBox<>(new String[]{"Brasil", "EUA", "Portugal", "Outro"});

        // Aba 1 - Informações básicas
        JPanel basicInfoPanel = createBasicInfoPanel(
                tabbedPane, nicknameField, emailField, passwordField, confirmPasswordField
        );

        // Aba 2 - Informações adicionais
        JPanel additionalInfoPanel = new JPanel();
        additionalInfoPanel.setLayout(new BoxLayout(additionalInfoPanel, BoxLayout.Y_AXIS));
        additionalInfoPanel.setOpaque(false);
        additionalInfoPanel.setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));

        fullNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        fullNameField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Nome Completo", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        fullNameField.setBackground(new Color(50, 50, 50));
        fullNameField.setForeground(Color.WHITE);

        birthDateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        birthDateField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Data de Nascimento", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        birthDateField.setBackground(new Color(50, 50, 50));
        birthDateField.setForeground(Color.WHITE);

        countryCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        countryCombo.setBackground(new Color(70, 70, 70));
        countryCombo.setForeground(Color.WHITE);

        JButton registerBtn = createStyledButton("Finalizar Cadastro");
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(200, 40));
        registerBtn.addActionListener(e -> {
            if (validateAdditionalInfo(fullNameField, birthDateField)) {
                saveUserData(
                        nicknameField.getText(),
                        emailField.getText(),
                        new String(passwordField.getPassword()),
                        fullNameField.getText(),
                        birthDateField.getText(),
                        (String) countryCombo.getSelectedItem()
                );
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                cardLayout.show(mainPanel, "welcome");
            }
        });

        JButton backBtn = createStyledButton("Voltar");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setMaximumSize(new Dimension(200, 40));
        backBtn.addActionListener(e -> tabbedPane.setSelectedIndex(0));

        additionalInfoPanel.add(fullNameField);
        additionalInfoPanel.add(Box.createVerticalStrut(10));
        additionalInfoPanel.add(birthDateField);
        additionalInfoPanel.add(Box.createVerticalStrut(10));
        additionalInfoPanel.add(countryCombo);
        additionalInfoPanel.add(Box.createVerticalStrut(20));
        additionalInfoPanel.add(registerBtn);
        additionalInfoPanel.add(Box.createVerticalStrut(10));
        additionalInfoPanel.add(backBtn);

        // Adiciona as abas ao tabbedPane
        tabbedPane.addTab("Informações Básicas", basicInfoPanel);
        tabbedPane.addTab("Informações Adicionais", additionalInfoPanel);

        // Botão para voltar ao menu principal
        JButton returnBtn = createStyledButton("Voltar ao Menu");
        returnBtn.setPreferredSize(new Dimension(400, 45));
        returnBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(returnBtn, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel createBasicInfoPanel(JTabbedPane tabbedPane,
                                        JTextField nicknameField,
                                        JTextField emailField,
                                        JPasswordField passwordField,
                                        JPasswordField confirmPasswordField) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));

        nicknameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nicknameField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Nickname", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        nicknameField.setBackground(new Color(50, 50, 50));
        nicknameField.setForeground(Color.WHITE);

        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Email", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        emailField.setBackground(new Color(50, 50, 50));
        emailField.setForeground(Color.WHITE);

        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Senha", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);

        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70,130,180)), "Confirmar Senha", 0, 0, new Font("Arial", Font.PLAIN, 12), Color.WHITE));
        confirmPasswordField.setBackground(new Color(50, 50, 50));
        confirmPasswordField.setForeground(Color.WHITE);

        JButton nextBtn = createStyledButton("Próximo");
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextBtn.setMaximumSize(new Dimension(200, 40));
        nextBtn.addActionListener(e -> {
            if (validateBasicInfo(nicknameField, emailField, passwordField, confirmPasswordField)) {
                tabbedPane.setSelectedIndex(1);
            }
        });

        panel.add(nicknameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nextBtn);

        return panel;
    }



    private boolean validateBasicInfo(JTextField nickname, JTextField email,
                                      JPasswordField password, JPasswordField confirmPassword) {
        if (nickname.getText().isEmpty() || email.getText().isEmpty() ||
                password.getPassword().length == 0 || confirmPassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new String(password.getPassword()).equals(new String(confirmPassword.getPassword()))) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (new String(password.getPassword()).length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.getText().contains("@") || !email.getText().contains(".")) {
            JOptionPane.showMessageDialog(this, "Email inválido", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateAdditionalInfo(JTextField fullName, JTextField birthDate) {
        if (fullName.getText().isEmpty() || birthDate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void saveUserData(String nickname, String email, String password,
                              String fullName, String birthDate, String country) {
        JSONObject user = new JSONObject();
        user.put("nickname", nickname);
        user.put("email", email);
        user.put("password", password); // Na prática, você deve usar hash para senhas
        user.put("fullName", fullName);
        user.put("birthDate", birthDate);
        user.put("country", country);

        JSONArray users = new JSONArray();
        File file = new File(USER_DATA_FILE);

        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                if (!content.isEmpty()) {
                    users = new JSONArray(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        users.put(user);

        try (FileWriter writer = new FileWriter(USER_DATA_FILE)) {
            writer.write(users.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(String username, String password) {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) return false;

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.isEmpty()) return false;

            JSONArray users = new JSONArray(content);
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if ((user.getString("nickname").equals(username) ||
                        user.getString("email").equals(username)) &&
                        user.getString("password").equals(password)) { // Comparação insegura - apenas para exemplo
                    return true;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setBackground(new Color(255, 255, 255, 200));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setBackground(new Color(255, 255, 255, 200));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleLinkButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            GameLauncher launcher = new GameLauncher();
            launcher.setVisible(true);
        });
    }
}