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
public class ThreadTemp extends Thread {
    
    int read;
    Queries q;
    
    public ThreadTemp(int read, Queries q){
        this.read = read;
        this.q = q;
    }
    public void run(){
        try{
            if(this.q.insertTemp(read)){
                System.out.println("Registro guardado en sensor de temperatura");
            }else{
                //System.out.println("Error: registro no guardado en sensor de temperatura");
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
    
}
