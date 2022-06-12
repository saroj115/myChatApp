package client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;


public class client {
    private JFrame clientframe;
    private JTextArea jTextArea;
    private JScrollPane jScrollPane;
    public JTextField jTextField;
    private Socket socket;
    String ip_address;
    private DataInputStream dis;
    private DataOutputStream dos;
    // --------------------thread creation start--------------------
    Thread thread = new Thread() {
        public void run() {
            while (true) {
                readMessage();
            }
        }

    };

    //---------------------thread creation end-----------------
    client() {
        ip_address = JOptionPane.showInputDialog("Enter Ip address");
        if (ip_address != null) {
            if (!ip_address.equals("")) {
                connectToServer();
                clientframe = new JFrame("Client");
                clientframe.setSize(500, 500);
                clientframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jTextArea = new JTextArea();
                jTextArea.setEditable(false);
                Font font = new Font("Ayuthaya", 1, 12);
                jTextArea.setFont(font);
                jScrollPane = new JScrollPane(jTextArea);
                clientframe.add(jScrollPane);
                jTextField = new JTextField();
                jTextField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendMessage(jTextField.getText());
                        jTextArea.append(jTextField.getText() + "\n");
                        jTextField.setText("");
                    }
                });
                clientframe.add(jTextField, BorderLayout.SOUTH);
                clientframe.setVisible(true);
            }
        }

    }

    void connectToServer() {
        try {
            socket = new Socket(ip_address, 1111);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void setIoStream()  // to read and write data
    {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        thread.start();
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void readMessage() {
        try {
            String message = dis.readUTF();
            showMessage("Server: " + message);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void showMessage(String message) {
        jTextArea.append(message + "\n");
        chatSound();

    }

    public void chatSound() {
        try {
            String soundName = "/Users/sarojthapa/Documents/Intellij projects/myChatApp/src/sound/mixkit-bell-notification-933.wav";

            Clip clip = AudioSystem.getClip();
            AudioInputStream AudioInputStream = AudioSystem.getAudioInputStream(new File(soundName));
            ;
            clip.open(AudioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


