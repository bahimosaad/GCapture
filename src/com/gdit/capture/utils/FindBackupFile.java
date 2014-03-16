/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.utils;

import java.io.File;

/**
 *
 * @author Administrator
 */
public class FindBackupFile {
    
    public String findFile(String name,File file){
        try{
            File[] list = file.listFiles();
            if(list!=null){
                for(File fil:list){
                    if(fil.isDirectory()){
                        if(name.equalsIgnoreCase(fil.getName())){
                            System.out.println(fil.getAbsolutePath());
                            return fil.getAbsolutePath();
                            
                        }
                        else{
                            findFile(name,fil);
                        }
                    }
                }
                
            }
            return null;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
