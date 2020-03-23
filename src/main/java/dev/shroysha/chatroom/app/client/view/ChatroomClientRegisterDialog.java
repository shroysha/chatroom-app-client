package dev.shroysha.chatroom.app.client.view;


import dev.shroysha.chatroom.app.client.controller.ChatroomClientController;

import javax.swing.*;
import java.awt.*;

public class ChatroomClientRegisterDialog extends JDialog {

    private final JTextField nameField = new JTextField(20);
    private final JButton nameButton = new JButton("Confirm");

    public ChatroomClientRegisterDialog(ChatroomClientFrame parent) {
        super(parent, "Enter Name", true);
        init();
    }

    private void init() {
        this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.setSize(new Dimension(250, 110));
        this.setResizable(false);
        this.setLayout(new FlowLayout());

        this.add(new JLabel("Enter Your Name to Begin Chat"));
        this.add(nameField);
        this.add(nameButton);
        this.getRootPane().setDefaultButton(nameButton);

        nameButton.addActionListener(actionEvent -> {
            try {
                String desiredUsername = nameField.getText();
                ChatroomClientController.createUser(desiredUsername);
                ChatroomClientRegisterDialog.this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }


}
