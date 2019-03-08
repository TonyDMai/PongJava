package pingpong;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class controls the paddles. It contains the score position and velocity of each paddle
 * @author Doan Mai, 000748737 hereby declares that this is solely my work.
 */
public class Paddle
{
    /** Width of the paddle, the paddle is drawn 10px smaller than it actually is. This is to account for the ball radius when detecting collision**/
    private final double WIDTH = 30;
    /** Height of the paddle **/
    private final double HEIGHT = 80;
    /** xPos and yPos of the paddle **/
    private double xPos = 0, yPos = 0;
    /** velocity of the Paddle **/
    private double velocity = 5;
    /** score associated of the paddle **/
    private int score = 0;
    
    /**
     * Paddle Constructor, assigns the x position and y position using the canvas as a base
     * @param x
     * @param canvas 
     **/
    public Paddle ( double x , Canvas canvas )
    {
        this.xPos = x;
        this.yPos = ( canvas.getHeight() / 2 );
    }
    
    /**
     * Changes the y position based on the direction the player is moving
     * @param y 
     **/
    public void setyPos ( double y )
    {

        this.yPos += velocity * y;
    }

    /**
     * returns the x position
     * @return xPos 
     **/
    public double getX ()
    {
        return xPos;
    }
    /**
     * returns the y position
     * @return yPos
     **/
    public double getY ()
    {
        return yPos;
    }

    /**
     * Returns the width of the paddle
     * @return returns the width of the paddle 
     **/
    public double getWidth ()
    {
        return WIDTH;
    }

    /**
     * returns the height of the paddle
     * @return height
     **/
    public double getHeight ()
    {
        return HEIGHT;
    }

    /**
     * Draws the paddle
     * @param gc 
     **/
    public void draw ( GraphicsContext gc )
    {
        gc.setFill( Color.WHITE );
        gc.fillRect( xPos , yPos , WIDTH - 10 , HEIGHT );
    }
    /**
     * Controls the AI movement 
     * @param ball
     * @param canvas 
     **/
    public void AI ( Ball ball , Canvas canvas )
    {
        if ( ( ball.getY() > this.yPos ) && ( this.yPos < canvas.getHeight() - this.HEIGHT ) )
        {
            this.yPos += velocity;
        } else if ( ( ball.getY() < this.yPos ) && this.yPos > 100 )
        {
            this.yPos -= velocity;
        }
    }
    /**
     * Adds the score 
     **/
    public void score ()
    {
       score ++ ;
    }
    
    /**
     * returns the score of the associated player
     * @return score
     **/
    public String getScore()
    {
        return "" + score;
    }
    /**
     * resets the score and paddle position
     * @param canvas 
     **/
    public void reset(Canvas canvas)
    {
        this.yPos = canvas.getHeight() /2;
        score = 0;
    }
}
