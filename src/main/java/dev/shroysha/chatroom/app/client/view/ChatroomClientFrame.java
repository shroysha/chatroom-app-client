package dev.shroysha.chatroom.app.client.view;

import dev.shroysha.chatroom.app.client.controller.ChatroomClientController;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatroomClientFrame extends JFrame {

    private static final String TEXT_SUBMIT = "text-submit";
    private static final String INSERT_BREAK = "insert-break";

    private final JPanel contentPanel = new JPanel(new FlowLayout());
    private final JScrollPane messageBoxScroller = new JScrollPane();
    private final JPanel textInputPanel = new JPanel();
    private final JTextArea messageBox = new JTextArea();
    private final JButton sendButton = new JButton("Send");
    private final MessagesPanel messagePanel = new MessagesPanel();

    public ChatroomClientFrame() {
        super("Group Chat");
        init();

        ChatroomClientRegisterDialog registerDialog = new ChatroomClientRegisterDialog(this);
        registerDialog.setVisible(true);
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setResizable(false);

        sendButton.setBackground(Color.BLUE);
        sendButton.setPreferredSize(new Dimension(80, 50));

        messageBox.setPreferredSize(new Dimension(300, 50));
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        messageBoxScroller.setViewportView(messageBox);

        textInputPanel.add(messageBox);
        textInputPanel.add(sendButton);
        contentPanel.add(textInputPanel);
        contentPanel.add(messagePanel);

        InputMap input = messageBox.getInputMap();
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        input.put(enter, TEXT_SUBMIT);
        KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
        input.put(shiftEnter, INSERT_BREAK);
        ActionMap actions = messageBox.getActionMap();
        AbstractAction sendMessageAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String message = messageBox.getText();
                messageBox.setText("");
                ChatroomClientController.sendMessage(message);
            }
        };
        actions.put(TEXT_SUBMIT, sendMessageAction);
        sendButton.addActionListener(sendMessageAction);

        this.add(contentPanel, BorderLayout.CENTER);
        this.setBackground(Color.BLUE);
        this.getRootPane().setDefaultButton(sendButton);
    }


    private class MessagesPanel extends JScrollPane {

        private final JTextPane allMessagesPane = new JTextPane();
        private final Style leftStyle = allMessagesPane.addStyle("LeftStyle", null);
        private final Style youStyle = allMessagesPane.addStyle("YouStyle", null);
        private final Style plainStyle = allMessagesPane.addStyle("PlainStyle", null);

        public MessagesPanel() {
            init();
        }

        private void init() {
            this.setPreferredSize(new Dimension(380, 300));

            StyleConstants.setForeground(leftStyle, Color.red);
            StyleConstants.setFontSize(leftStyle, 14);
            StyleConstants.setItalic(leftStyle, true);

            StyleConstants.setForeground(youStyle, Color.blue);
            StyleConstants.setFontSize(youStyle, 14);

            StyleConstants.setForeground(plainStyle, Color.black);
            StyleConstants.setFontSize(plainStyle, 14);

            allMessagesPane.setPreferredSize(new Dimension(360, 300));
            allMessagesPane.setEditable(false);

            this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            this.setViewportView(allMessagesPane);
        }

        protected void repaintMessages(ArrayList<String> messages) {
            try {
                StyledDocument doc = allMessagesPane.getStyledDocument();
                allMessagesPane.setText("");

                for (int i = 0; i < messages.size(); i++) {
                    String message = messages.get(i);
                    Style style = null;
//                    if (message.contains(stringConstant)) {
//                        message = message.replace(stringConstant, "");
//                        style = leftStyle;
//                    } else if (message.substring(0, myName.length()).equals(myName)) {
//                        message = "You" + message.substring(myName.length());
//                        style = youStyle;
//
//                    } else {
//                        style = plainStyle;
//                    }
                    //messageArea.append(message + "\n\n");
                    message += "\n\n";
                    doc.insertString(doc.getLength(), message, style);
                }

                allMessagesPane.select(doc.getLength(), doc.getLength());
                ChatroomClientFrame.this.toFront();
            } catch (Exception ex) {
                Logger.getLogger(ChatroomClientFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}



