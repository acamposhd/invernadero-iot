/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invernadero.iot;

import view.MainScreen;
import view.Login;

/**
 *
 * @author fields
 */
public class InvernaderoIot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Login l = new Login();
        l.show();
        */
        Login l = new Login();
        l.setVisible(true);
        l.setLocationRelativeTo(null);
    }
    
}
