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
public class ThreadSoil extends Thread {
    
    int read;
    Queries q;
    
    public ThreadSoil(int read, Queries q){
        this.read = read;
        this.q = q;
    }
    public void run(){
        try{
            if(this.q.insertSoil(read)){
                System.out.println("Registro guardado en sensor de tierra");
            }else{
                //System.out.println("Error: registro no guardado en sensor de tierra");
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
    
}
