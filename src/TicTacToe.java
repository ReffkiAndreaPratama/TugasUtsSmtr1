import  javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public TicTacToe() {
        setTitle("Login Game Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        //memberi warna pada panel
        panel.setBackground(Color.black);

        JLabel usernameLabel = new  JLabel("nama pengguna:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("kata sandi:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.black);
        loginButton.setForeground(Color.white);
        usernameLabel.setForeground(Color.white);
        passwordLabel.setForeground(Color.white);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("") && password.equals("")) {
                    // pengguna berhasil login, mulai game
                    TicTacToeGame game = new TicTacToeGame();
                    game.setVisible(true);
                    dispose(); // tutup jendela login
                } else {
                    JOptionPane.showMessageDialog(null, "Username atau Password tidak valid");
                }
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TicTacToe loginGame = new TicTacToe();
            loginGame.setVisible(true);
        });
    }
}

class TicTacToeGame extends JFrame {

    private JButton[][] boardButtons;
    private boolean isPlayer1Turn;

    private int movesCount;


    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        boardButtons = new JButton[3][3];
        isPlayer1Turn = true;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();

                button.addActionListener(new ButtonActionListener(row, col));
                button.setBackground(Color.gray);
                button.setFocusPainted(false);
                Font customFont = new Font("Arial", Font.BOLD,24);
                panel.add(button);
                button.setForeground(Color.RED);
                panel.add(button);
                boardButtons[row][col] = button;
            }
        }

        add(panel);
    }

    private class ButtonActionListener implements ActionListener {
        private int row;
        private int col;

        public ButtonActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button.getText().isEmpty()) {
                if (isPlayer1Turn) {
                    button.setText("X");
                } else {
                    button.setText("O");
                }

                isPlayer1Turn = !isPlayer1Turn;
                movesCount++;

                if (movesCount >= 5) {
                    if  (checkWin()) {
                        String winner = isPlayer1Turn ? "Player o" : "Player x";
                        JOptionPane.showMessageDialog(null, winner + " menang!");
                        resetGame();
                    } else if (movesCount == 9) {
                        JOptionPane.showMessageDialog(null, "Itu seri!");
                        resetGame();
                    }
                }
            }
        }

        private boolean checkWin() {
            String[][] board = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = boardButtons[i][j].getText();
                }
            }

            // Cek rows
            for (int i = 0; i < 3; i++) {
                if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                    return true;
                }
            }

            // Cek columns
            for (int j = 0; j < 3; j++) {
                if (!board[0][j].isEmpty() && board[0][j].equals(board[1][j]) && board[0][j].equals(board[2][j])) {
                    return true;
                }
            }

            // Cek diagonals
            if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
                return true;
            }
            if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) &&

                    board[0][2].equals(board[2][0])) {
                return true;
            }

            return  false;
        }

        private void resetGame() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardButtons[i][j].setText("");
                }
            }

            isPlayer1Turn = true;
            movesCount = 0;
        }
    }
}
