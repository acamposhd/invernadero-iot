/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JOptionPane;

/**
 *
 * @author fields
 */
public class ThreadUser extends Thread {
    
    Data d;
    Queries q;
    
    public ThreadUser(Data d, Queries q){
        this.d = d;
        this.q = q;
    }
    public void run(){
        try{
            if(this.q.insertUser(d)){
               JOptionPane.showMessageDialog(null, "Usuario registrado satisfactoriamente", "ÉXITO", WIDTH);
            }else{
                //System.out.println("Error: registro no guardado en sensor touch");
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
    
}
