/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import javafx.scene.control.TextField;

/**
 *
 * @author JScholz
 */
public class NumberTextField extends TextField {

    public NumberTextField( double width ) {
    
        super();
        
        setPrefWidth( width );
        
    }
    
    public NumberTextField( double width, String startValue ) {
    
        super( startValue );
        
        setPrefWidth( width );
        
    }
    
    @Override
    public void replaceText(int start, int end, String text) {
        
        if( validate(text) ) {
            
            int i = Integer.parseInt(text);
            
            if( i >= 3) super.replaceText(start, end, text);
            
        }
        
    }

    @Override
    public void replaceSelection(String text) {
        
        if( validate(text) ) {
            super.replaceSelection(text);
        }
        
    }

    private boolean validate(String text) {
        
        return (text.equals("") || text.matches("[0-9]"));
        
    }
    
}
