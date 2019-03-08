package pingpong;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 * This class controls the collision, ball movement and ball creation.
 * @author Doan Mai, 000748737 hereby declares that this is solely my work.
 */
public class Ball
{
    /** The components of the ball, position, direction and size of the ball**/
    private double xPos, yPos, yDir, xDir, radius;
    /** the velocity of the ball **/
    private double velocity = 12;
    // private double accel = 0.01;
    /** Boolean that tells the collider to run and check collision**/
    private boolean collider = true;

    /**
     * spawns the ball by running the reset method 
     * @param canvas 
     **/
    public Ball ( Canvas canvas )
    {
        this.reset( canvas );
    }

    /**
     * Resets the ball position
     * @param canvas 
     **/
    public void reset ( Canvas canvas )
    {
        // sets the position to the center of the game screen
        this.xPos = canvas.getWidth() / 2; 
        this.yPos = canvas.getHeight() / 2;
        this.radius = 12; // sets radius to 10
        this.randSpawn(); // randomly generate direction the ball is moving
        this.velocity = 10; // reset velocity to 10
    }

    /**
     * returns the y position of the ball
     * @return yPos
     **/
    public double getY ()
    {
        return this.yPos;
    }

    /**
     * Generates a random ball direction
     **/
    public void randSpawn ()
    {        
        // y velocity is split up with x and y pos if y is a decimal number then the velocity slower
        this.xDir = Math.random() * 1 + ( -1 ); // generates a random number from -1 to 1 for the ball - 1 = left 1 = right
        this.yDir = Math.random() * 1 + ( -0.7 ); // causes the ball to move in a random y direction
    }

    /**
     * Updates the Ball position
     * @param canvas
     * @param gc
     * @param p1
     * @param AI 
     **/
    public void update ( Canvas canvas , GraphicsContext gc , Paddle p1 , Paddle AI )
    {
        //    velocity += accel;
        // Moves the ball based on velocity
        this.xPos += xDir * velocity;
        this.yPos += yDir * velocity;
        // runs the collider
        if ( collider )
        {
            // creates a new tread for the collider to run
            Thread t = new Thread( () -> collide( canvas , gc , p1 , AI ) );
            t.start(); // runs the thread
        }
    }
    /**
     * CHecks for collision
     * @param canvas
     * @param gc
     * @param p1
     * @param AI 
     **/
    public void collide ( Canvas canvas , GraphicsContext gc , Paddle p1 , Paddle AI )
    {
        
        String path = Ball.class.getResource("soundeffect_1.mp3").toString(); // sets the path of the sound import
        Media sound = new Media(path); // creates a new media object with the sound file
        MediaPlayer player = new MediaPlayer( sound ); // creates a new media player to play the sound
        
        // Checks position of y and reflects if hits the top or bottom border
        if ( this.yPos <= 110 || this.yPos >= 690 )
        {
             player.play(); // plays the sound effect
            yDir *= -1; // reflects the ball
        }
        // Checks for collision with player paddle
        if ( ( this.xPos <= p1.getX() && p1.getX() - this.xPos <= 10 ) && ( this.yPos <= p1.getY() + p1.getHeight() && this.yPos >= p1.getY() ) )
        {
             player.play();
            xDir *= -1;
            // CODE UNDERNEATH WILL BE FOR CHANGING THE DIRECTION BASED ON WHERE ON THE PADDLE IT IS HIT
//            if (yPos >= p1.getY() + p1.getHeight()/4 || yPos >= p1.getY() + p1.getHeight() - p1.getHeight()/4)         
//            {
//                if (yDir > 0)
//                { yDir = -0.7;}
//                else if (yDir <1)
//                {yDir = 0.7;}
//            }
        }
        // checks for collision with AI paddle
        if ( ( this.xPos <= AI.getX() + AI.getWidth() && AI.getX() - this.xPos <= 10 ) && ( this.yPos <= AI.getY() + AI.getHeight() && this.yPos >= AI.getY() ) )
        {
             player.play();
            xDir *= -1;
        }
        // checks if p1 scored
        if ( this.xPos < 0 )
        {
            p1.score(); // adds 1 to score
            this.reset( canvas ); // resets ball
        // checks if AI scored
        } else if ( this.xPos > canvas.getWidth() )
        {
            AI.score(); // adds 1 to AI score
            this.reset( canvas ); // resets 
        }   
    }

    /**
     * Changes the velocity based on value given in text field
     * @param x 
     **/
    public void setVelocity ( int x )
    {
        this.velocity = x;
    }
    /**
     * draws the ball
     * @param gc 
     **/
    public void draw ( GraphicsContext gc )
    {
        gc.setFill( Color.WHITE );
        gc.fillOval( xPos , yPos , radius , radius );
    }
}
