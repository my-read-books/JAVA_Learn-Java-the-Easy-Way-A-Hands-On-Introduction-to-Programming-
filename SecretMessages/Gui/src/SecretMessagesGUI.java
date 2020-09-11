import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecretMessagesGUI extends JFrame{
    private JTextArea txtIn;
    private JTextArea txtOut;
    private JTextField txtKey;
    private JButton decodeEncodeButton;
    private JLabel keyLabel;
    private JPanel mainWindow;
    private JSlider slider1;

    public String encode(String message, int keyVal){
        char key = (char) keyVal;
        String output = "";
        for (int x = 0; x < message.length(); x++) {
            char input = message.charAt(x);
            if (input >= 'A' && input <= 'Z')
            {
                input += key;
                if (input > 'Z')
                    input -= 26;
                if (input < 'A')
                    input += 26;
            }
            else if (input >= 'a' && input <= 'z')
            {
                input += key;
                if (input > 'z')
                    input -= 26;
                if (input < 'a')
                    input += 26;
            }
            else if (input >= '0' && input <= '9')
            {
                input += (keyVal% 10);
                if (input > '9')
                    input -= 10;
                if (input < '0')
                    input += 10;
            }
            output += input;
        }
        return output;
    }

    public SecretMessagesGUI(){
        setTitle("Secret message");
        getContentPane().setLayout(null);
        txtIn.setBounds(10, 11, 564, 140);
        getContentPane().add(txtIn);

        txtOut.setBounds(10, 210, 564, 140);
        getContentPane().add(txtOut);
        txtKey.setBounds(258, 173, 44, 20);
        getContentPane().add(txtKey);
        keyLabel.setBounds(202, 176, 46, 14);
        getContentPane().add(keyLabel);


        decodeEncodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String message = txtIn.getText();
                int key = Integer.parseInt(txtKey.getText());
                String output = encode(message, key);
                txtOut.setText(output);
            } catch (Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Please enter a whole number value for the encryption key.");
                }
            }
        });

        decodeEncodeButton.setBounds(312, 172, 144, 23);
        getContentPane().add(decodeEncodeButton);

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                txtKey.setText("" + slider1.getValue());
                String message = txtIn.getText();
                int key = slider1.getValue();
                String output = encode(message, key);
                txtOut.setText(output);
            }
        });
        slider1.setBounds(10, 170,180,23);
        getContentPane().add(slider1);
    }

    public static void main(String[] args) {
        SecretMessagesGUI theApp = new SecretMessagesGUI();
        theApp.setSize(new java.awt.Dimension(600, 400));
        theApp.setVisible(true);
    }
}