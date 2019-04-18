package src;

import java.awt.image.BufferedImage;

//will use the HighScore.txt file to store top 5 HighScores
//will show as a list
/*
                 HIGHSORES
        1. NAME             SCORE
        2. NAME             SCORE
        3. NAME             SCORE
        4. NAME             SCORE
        5. NAME             SCORE

 */
public class Points {
    private int numOfPoints;
    //got to load HighScores each time and draw to this image
    private BufferedImage HighScores;

    Points(int p){
        this.numOfPoints = p;
    }

    public void setPoints(int p){
        this.numOfPoints = p;
    }

    public int getPoints(){
        return this.numOfPoints;
    }
    //this is going to load all the names and scores into the text file
    public void loadHighScores(){

    }
    //this is going to update the BufferedImage if a new highscore was made
    public void setHighScores(){

    }

}
