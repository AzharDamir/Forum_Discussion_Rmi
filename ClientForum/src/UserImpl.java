import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


public class UserImpl extends UnicastRemoteObject implements User, ActionListener {
    private Forum forum;
    private int id;

    private JFrame chatFrame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton enterButton;
    private JFrame enterchatFrame;
    private JMenuBar menuBar;
    private JMenuItem qui;
    private JMenuItem quitter;
    public UserImpl(Forum forum) throws RemoteException {
        this.forum = forum;
        buildGUI();
    }

    public void setId(int id) {
        this.id = id;
        int nvid=id+1;
        chatFrame.setTitle("Forum - User " + nvid);
    }

    public int getId() {
        return id;
    }

    public proxy getProxy() {
        try {
            return new ProxyImpl(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void ecrire(String msg) {
        chatArea.append(msg + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource().toString());
        String command = e.getActionCommand();
        if (command.equals("Send")) {
            String message = inputField.getText();
            if (!message.equals("")) {
                try {
                    forum.dire(id, message);
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                inputField.setText("");
            }
        }
        else if(e.getSource()==qui) {
            try {
                JOptionPane.showMessageDialog(chatFrame,forum.qui());
            } catch (HeadlessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else if(e.getSource()==quitter) {
            try {
                try {
                    forum.quiter(id);
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                int nvid=id+1;
                JOptionPane.showMessageDialog(chatFrame,"user: "+nvid+" a quitt�");
                System.out.println("Qui menu item clicked");
                enterchatFrame.setVisible(true);
                chatFrame.setVisible(false);
            } catch (HeadlessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == enterButton) {
            try {

                id = forum.entrer(getProxy());
                int nvid=id+1;
                chatFrame.setTitle("Forum - User " +": "+nvid);
                enterchatFrame.setVisible(false);
                chatFrame.setVisible(true);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        } else {
            String message = inputField.getText();
            if (!message.equals("")) {
                try {
                    forum.dire(id, message);
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                inputField.setText("");
            }
        }
    }

    public void buildGUI() {

        chatFrame = new JFrame("Forum");
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        JMenu actions = new JMenu("Options");
        qui = new JMenuItem("Qui?");
        quitter = new JMenuItem("Quitter");
        actions.add(qui);
        actions.add(quitter);
        menuBar.add(actions);
        qui.addActionListener(this);
        quitter.addActionListener(this);
        chatFrame.setJMenuBar(menuBar);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setForeground(Color.BLACK);
        inputField.setBackground(Color.WHITE);
        inputField.addActionListener(this);
        inputPanel.add(inputField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        ImageIcon sendIcon = new ImageIcon("C:\\Users\\azhar damir\\Downloads\\send.png");
        Image scaledImage = sendIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        sendButton.setIcon(new ImageIcon(scaledImage));
        sendButton.addActionListener(this);
        inputPanel.add(sendButton, BorderLayout.EAST);


        chatFrame.getContentPane().add(chatPanel);

        chatFrame.setSize(600, 600);

        enterchatFrame = new JFrame("Enter");
        enterchatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // enterchatFrame.setLocationRelativeTo(null);

        // Creation de Entrer frame
        enterchatFrame.setSize(new Dimension(650, 500));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("C:\\Users\\azhar damir\\Downloads\\chat.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }};
        panel.setLayout(null);
        JLabel label = new JLabel("<html>Veuillez entrer au Forum <br> afin de communiquer avec les autres! </html>");
        label.setFont(new Font("Helvetica", Font.BOLD, 26));
        label.setForeground(Color.BLUE);
        panel.add(label);
        label.setBounds(100, 280, 550, 80);



        enterButton = new JButton("Entrer au Forum");
        enterButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        enterButton.setForeground(Color.WHITE);
        enterButton.setBackground(Color.blue);
        enterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(enterButton);
        enterButton.setBounds(150, 380, 300, 50);
        enterButton.addActionListener(this);

        enterchatFrame.add(panel);
        enterchatFrame.setVisible(true);
        chatFrame.setVisible(false);
    }

}