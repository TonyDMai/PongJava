package pingpong;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The purpose of this program is to make a Game using GUI. This class controls
 * and runs all the drawing and calling of models in the game.
 *
 * @author Doan Mai 000748737
 */
public class PingPong extends Application
{

    // TODO: Instance Variables for View Components and Model
    private Canvas canvas = new Canvas( 1200 , 700 );
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    /**
     * Models for the game *
     **/
    private Ball ball; //Creates a ball for the game
    private Paddle p1; //Creates the Paddle that player will control
    private Paddle AI; //Creates the Paddle that AI controls
    private TextField velSet;
    // private Paddle p2; // FOR FUTURE TWO PLAYER MODE
    // private boolean enable = false; // FOR FUTURE TWO PLAYER MODE

    private Thread t = new Thread( () -> drawModels( gc ) ); // Creates a thread and ties it to drawmodel. This will let drawmodel run on a frame by frame instance
    private Pane root = new Pane();
    private Scene scene = new Scene( root , 1200 , 700 ); // set the size here
    private Input in = new Input(); // Input Class

    /**
     * Labels and Buttons on the GUI *
     **/
    private Label playerScore, AIScore, descrip; // Labels for scores and the description of the textbox
    private Button pause, velButton, reset; // Buttons for the game
    private int state = 1; // State on off -1 = off, 1 = on

    /**
     * Action Event Handler for the velocity set button Set the velocity to
     * indicated value
     * @param e
     **/
    private void velHandler ( ActionEvent e )
    {
        int velocity = Integer.parseInt( velSet.getText() ); // Takes the value inputted by user and converts it to INT
        ball.setVelocity( velocity ); // Sets the velocity of the ball based on the previous value

    }

    /**
     * Event Handler for the pause button. pauses the game or resumes it
     * @param e
    **/
    private void pauseHandler ( ActionEvent e )
    {
        state *= -1; // Compliments the State to pause or start the game
        Platform.runLater( () ->
        {
            if ( state == -1 ) // Changes the text to indicate the new function of the button
            {
                pause.setText( "Start" );
            } else
            {
                pause.setText( "Pause" );
            }
        } );
    }

    /**
     * Event Handler for the reset button. Resets the game
     * @param e 
     **/
    private void resetHandler ( ActionEvent e )
    {
        ball.reset( canvas );
        p1.reset( canvas );
        AI.reset( canvas );
    }

    /**
     * This Method Draws all the models and updates the game. It will also run or pause the game based
     * on what state it is in.
     * @param gc
     */
    public void drawModels ( GraphicsContext gc )
    {
        // Continuously run this code on a thread
        while ( true )
        {
            switch ( state )
            {
                // State 1 Game not Paused
                case 1:
                    in.keyPress( scene , p1 ); //Check for button inputs
                    AI.AI( ball , canvas ); // AI Movements
                    gc.setFill( Color.BLACK ); // Redraw the canvas
                    gc.fillRect( 0 , 100 , 1200 , 600 ); // Redraw the canvas
                    Platform.runLater( () -> // Tells the program to run when it has time
                    {
                        ball.update( canvas , gc , p1 , AI ); // Update the ball position -> also checks for collision at this time
                        ball.draw( gc ); // Draw the ball
                        p1.draw( gc ); // Draw the player
                        AI.draw( gc ); // Draw the AI
                    } );

                    Platform.runLater( () ->
                    {
                        in.updateScore( playerScore , AIScore , p1 , AI ); // Updates the score
                    } );
                    try
                    {
                        Thread.sleep( 1000 / 50 ); // Run thread at 50 FPS, this prevents contiunous flickering compared to 60fps
                    } catch ( InterruptedException e )
                    {

                    }
                    break;
                case 0: // Pause setting, basically runs nothing
                    break;
            }
        }
    }

    /**
     *START METHOD.
     * this method contains all the labels, buttons and textfields
     * @param stage
     * @throws Exception
     */
    @Override
    public void start ( Stage stage ) throws Exception
    {

        stage.setTitle( "PONG" ); // set the window title here
        stage.setScene( scene );
        // TODO: Add your GUI-building code here
        gc.setFill( Color.BLACK );
        gc.fillRect( 0 , 0 , 1200 , 700 );
        gc.setStroke( Color.WHITE );
        gc.setLineWidth( 10 );
        gc.strokeLine( 0 , 100 , canvas.getWidth() , 100 );
        // 1. Create the model
        ball = new Ball( canvas ); // Creates the ball
        p1 = new Paddle( 1100 , canvas ); // Creates player paddle at right side
        AI = new Paddle( 100 , canvas ); // Creates AI paddle at left side
        // Assigning the scores to the associated labels
        playerScore = new Label( p1.getScore() ); 
        AIScore = new Label( AI.getScore() );
        //Creating the other textfields, labels and buttons
        velSet = new TextField( "Double goes here" );
        descrip = new Label( "Type a double in the textbox and click the button to change the speed of the ball" );
        pause = new Button( "Pause" );
        velButton = new Button( "Set Velocity" );
        reset = new Button( "Reset" );

        // Add components to the pane
        root.getChildren().addAll( canvas , playerScore , AIScore , velSet , descrip , velButton , pause , reset );
        // 4. Configure the components (colors, fonts, size, location)
        playerScore.relocate( 1100 , 0 );
        playerScore.setTextFill( Color.WHITE );
        playerScore.setFont( Font.font( "Arial" , 75 ) );

        AIScore.relocate( 60 , 0 );
        AIScore.setTextFill( Color.WHITE );
        AIScore.setFont( Font.font( "Arial" , 75 ) );

        velSet.relocate( 200 , 35 );
        velSet.setFocusTraversable( false ); // sets it so that the textfield doesnt interfere with the game on load. If it
                                             // is focused the player cant use keyboard inputs

        velButton.relocate( 350 , 35 );
        pause.relocate( 500 , 35 );
        descrip.relocate( 200 , 20 );
        reset.relocate( 600 , 35 );
        descrip.setTextFill( Color.WHITE );
        // 5. Add Event Handlers and do final setup
        velButton.setOnAction( this::velHandler );
        pause.setOnAction( this::pauseHandler );
        reset.setOnAction( this::resetHandler );
        // 6. Show the stage
        stage.show();

        t.start();

    }

    /**
     * Make no changes here.
     * @param args unused
     */
    public static void main ( String[] args )
    {
        launch( args );
    }

    /**
     * Closes the program on clicking the exit button
     **/
    @Override
    public void stop ()
    {
        System.exit( 0 );
    }
}
