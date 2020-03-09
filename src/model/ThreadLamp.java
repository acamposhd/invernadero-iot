/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.MainScreen;

/**
 *
 * @author fields
 */
public class ThreadLamp extends Thread{


    public ThreadLamp() {    
    }
    
        public void run(){
        try{
        wait(100000);
        if(MainScreen.auxLed == "1"){
            
        }else{
            MainScreen.auxLed = "1";
        }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
}
