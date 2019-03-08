package pingpong;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class controls and detects the player inputs, it also updates the
 * scores.
 *
 * @author Doan Mai, 000748737 hereby declares that this is solely my work.
 **/
public class Input
{

    /**
     * Boolean variables that control the direction the paddle is going in *
     **/
    private boolean up = false, down = false, stop = false;
    /**
     * This controls what direction the paddle is going when it calls the setY
     * method. - 1 = up 1 = down*
     **/
    private double dir;

    /**
     * method that detects the key press and controls movement
     *
     * @param scene
     * @param p1 
     *
     **/
    public void keyPress ( Scene scene , Paddle p1 )
    {
        // the event handler that detects when a key is pressed
        scene.addEventHandler( KeyEvent.KEY_PRESSED , ( key ) ->
        {
            // Detects if the key is  up
            if ( key.getCode() == KeyCode.UP )
            {
                // Set up to true and everything else false
                up = true;
                down = false;
                stop = false;
                // Detects if key press is down
            } else if ( key.getCode() == KeyCode.DOWN )
            {
                // sets down to true and everything else false
                down = true;
                up = false;
                stop = false;
            }
        } );
        // event handler that detects when a key is released
        scene.addEventHandler( KeyEvent.KEY_RELEASED , ( key ) ->
        {

            // If key releaseed is up
            if ( key.getCode() == KeyCode.UP )
            {
                // set stop true
                up = false;
                down = false;
                stop = true;

                // if key released is down
            } else if ( key.getCode() == KeyCode.DOWN )
            {
                //stop = true
                up = false;
                down = false;
                stop = true;
            }
        } );
        // sets direction to -1
        if ( up )
        {
            dir = -1;
        // sets direction to 1
        } else if ( down )
        {
            dir = 1;
        // sets direction to 0
        } else if ( stop )
        {
            dir = 0;
        }
        p1.setyPos( dir ); // moves the paddle
        // stops the paddle from moving outsie the bounds of the game
        if ( p1.getY() < 100 || p1.getY() > 620 )
        {
            // makes the paddle go in the opposite direction  
            p1.setyPos( -dir );
        }

    }
    /**
     * updates the score 
     * @param p1
     * @param AI
     * @param play1
     * @param playAI 
     **/
    public void updateScore ( Label p1 , Label AI , Paddle play1 , Paddle playAI )
    { 
        // resets the labels with the new scores
        p1.setText( play1.getScore() );
        AI.setText( playAI.getScore() );
    }

    /**
     * Changes the text of the buttons depending on the state of the game
     * @param state
     * @return text
     **/
    public String setText ( int state )
    {
        /** String variable for the text on the button**/
        String text;
        // if paused change the text to start
        if ( state == 0 )
        {
            text = "Start";
        } else
        {
            // else set the text to pause
            text = "Pause";
        }
        return text;
    }
}
