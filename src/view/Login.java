/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import model.Data;
import model.Queries;

/**
 *
 * @author fields
 */
public class Login extends javax.swing.JFrame {

    int i;
    /**
     * Creates new form landing
     */

    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(7, ino);
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {

            try {
                if (multi.dataReceptionCompleted()) {

                    i++;

                    String rfidValue = (multi.getMessage(6));
                    multi.flushBuffer();
                    if ("Alberto".equals(rfidValue) || "Emiliano".equals(rfidValue)) {
                        ino.flushSerialPort();
                        ino.killArduinoConnection();
                        MainScreen m = new MainScreen();
                        m.setVisible(true);
                        m.setLocationRelativeTo(null);
                        m.userLbl.setText(rfidValue);
//                        Login l = new Login();
                        dispose();
                    }

                }
            } catch (ArduinoException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    };

    public Login() {

        initComponents();
        try {
            ino.arduinoRXTX("COM4", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginBtn = new javax.swing.JButton();
        OwnerInfo = new javax.swing.JButton();
        usernameJtxt = new javax.swing.JTextField();
        passTxt = new javax.swing.JPasswordField();
        signUpBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Invernadero IOT");
        setMinimumSize(new java.awt.Dimension(805, 630));
        setResizable(false);
        getContentPane().setLayout(null);

        loginBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/loginBtn.png"))); // NOI18N
        loginBtn.setBorder(null);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });
        getContentPane().add(loginBtn);
        loginBtn.setBounds(250, 350, 320, 60);

        OwnerInfo.setBorderPainted(false);
        OwnerInfo.setContentAreaFilled(false);
        OwnerInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OwnerInfo.setFocusPainted(false);
        OwnerInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OwnerInfoActionPerformed(evt);
            }
        });
        getContentPane().add(OwnerInfo);
        OwnerInfo.setBounds(10, 540, 410, 40);

        usernameJtxt.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        usernameJtxt.setForeground(new java.awt.Color(255, 255, 255));
        usernameJtxt.setToolTipText("Username");
        usernameJtxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        usernameJtxt.setOpaque(false);
        getContentPane().add(usernameJtxt);
        usernameJtxt.setBounds(260, 240, 300, 40);

        passTxt.setForeground(new java.awt.Color(255, 255, 255));
        passTxt.setToolTipText("Password");
        passTxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        passTxt.setOpaque(false);
        getContentPane().add(passTxt);
        passTxt.setBounds(270, 290, 300, 40);

        signUpBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/signup_btn.png"))); // NOI18N
        signUpBtn.setText("jButton1");
        signUpBtn.setBorderPainted(false);
        signUpBtn.setContentAreaFilled(false);
        signUpBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpBtnActionPerformed(evt);
            }
        });
        getContentPane().add(signUpBtn);
        signUpBtn.setBounds(250, 420, 320, 50);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/landing_wall.jpg"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, 0, 810, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed

        Data d = new Data();
        Queries q = new Queries();
        d.setUsername(usernameJtxt.getText());
        d.setPw(passTxt.getText());
        if (q.login(d) != 0) {
            try {
                ino.flushSerialPort();
                ino.killArduinoConnection();
            } catch (SerialPortException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);

            } catch (ArduinoException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            MainScreen main = new MainScreen();
            main.setVisible(true);
            main.setLocationRelativeTo(null);
            main.userLbl.setText(usernameJtxt.getText());
            usernameJtxt.setText("");
            passTxt.setText("");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "LOGIN ERROR", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_loginBtnActionPerformed

    private void OwnerInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OwnerInfoActionPerformed

        try {
            ino.flushSerialPort();
            ino.killArduinoConnection();
        } catch (SerialPortException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ArduinoException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        OwnerInfo o = new OwnerInfo();
        o.setVisible(true);
        o.setLocationRelativeTo(null);
        o.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispose();
    }//GEN-LAST:event_OwnerInfoActionPerformed

    private void signUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signUpBtnActionPerformed
        try {
            ino.flushSerialPort();
            ino.killArduinoConnection();
        } catch (SerialPortException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ArduinoException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddUser au = new AddUser();
        au.setVisible(true);
        au.setLocationRelativeTo(null);
        au.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispose();
    }//GEN-LAST:event_signUpBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OwnerInfo;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField passTxt;
    private javax.swing.JButton signUpBtn;
    private javax.swing.JTextField usernameJtxt;
    // End of variables declaration//GEN-END:variables

}
