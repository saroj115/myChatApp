package server;/*
 * author: Saroj
 * */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class server {
    private JFrame serverframe;
    private JTextArea jTextArea;
    private JScrollPane jScrollPane;
    private JTextField jTextField;
    private ServerSocket serverSocket;
    private InetAddress inet_Address;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private AudioInputStream audioInputStream;
    // ---------------thread created-------------------
    Thread thread = new Thread() {
        public void run() {
            while (true) {
                readMessage();
            }
        }
    };

    //-------------thread end------------------------
    server() {
        serverframe = new JFrame("Server");
        serverframe.setSize(500, 500);
        serverframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        Font font = new Font("Ayuthaya", 1, 12);
        jTextArea.setFont(font);
        jScrollPane = new JScrollPane(jTextArea);
        serverframe.add(jScrollPane);
        jTextField = new JTextField();
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(jTextField.getText());
                jTextArea.append(jTextField.getText() + "\n");
                jTextField.setText("");
            }

        });
        jTextField.setEditable(false);
        serverframe.add(jTextField, BorderLayout.SOUTH);
        serverframe.setVisible(true);
    }

    public void waitingForClient() {
        try {
            String ip_address = getIPAddress();
            ServerSocket serverSocket = new ServerSocket(1111);
            jTextArea.setText("To connect with server please provide Ip address: " + ip_address);
            socket = serverSocket.accept();
            jTextArea.setText("CLIENT CONNECTED\n");
            jTextArea.append("----------------\n");
            jTextField.setEditable(true);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getIPAddress() {
        String ip_address = "";
        try {
            inet_Address = InetAddress.getLocalHost();
            ip_address = inet_Address.getHostAddress();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ip_address;
    }

    void setIoStream() // to read and write data
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
            showMessage("Client: " + message);
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
            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName));
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

