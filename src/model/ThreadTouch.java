/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author fields
 */
public class ThreadTouch extends Thread{
    
    int read;
    Queries q;
    
    public ThreadTouch(int read, Queries q){
        this.read = read;
        this.q = q;
    }
    public void run(){
        try{
            if(this.q.insertTouch(read)){
                System.out.println("Registro guardado en sensor touch");
            }else{
                //System.out.println("Error: registro no guardado en sensor touch");
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
    
}
