/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;

/**
 *
 * @author joshtag096
 */
public class Actions {
    private Actions(){
        
    }
    
    public static void closeWindow(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
}
