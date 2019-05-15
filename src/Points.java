package src;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;


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
public class Points extends Object {
    private double numOfPoints;
    String name;
    private BufferedReader pointRead;
    public static ArrayList<Points> pointHolder = new ArrayList<>();
    private ArrayList<Points> tempHold = new ArrayList<>();
    //got to load HighScores each time and draw to this image


    Points(double p) {
        this.numOfPoints = p;
    }

    Points(String name, double score) {
        this.name = name;
        this.numOfPoints = score;
    }

    public void setPoints(double p) {
        this.numOfPoints = p;
    }

    public double getPoints() {
        return this.numOfPoints;
    }

    //this is going to load all the names and scores into the text file
    public void loadHighScores() {
        try {
            FileReader file = new FileReader("./src/HighScores.txt");
            this.pointRead = new BufferedReader(file);
            String temp;
            int counter = 0;
            while ((temp = this.pointRead.readLine()) != null && counter < 9) {
                StringTokenizer s = new StringTokenizer(temp, " ");
                String elem1 = s.nextToken();
                String elem2 = s.nextToken();
                double points = Double.parseDouble(elem2);
                tempHold.add(new Points(elem1, points));
                counter++;
            }
            this.pointRead.close();

        } catch (IOException e) {
            System.out.println("***COULD NOT READ FILE***" + e);
        }
        sortList();

    }

    //this is going to update the BufferedImage if a new highscore was made
    public void setHighScore(Points points, int index) {
        pointHolder.add(index, points);
        pointHolder.remove(pointHolder.size() - 1);

        try {
            Writer fileWriter = new FileWriter("./src/HighScores.txt");
            for (int i = 0; i < pointHolder.size(); i++) {
                System.out.println(pointHolder.get(i).name + " " + pointHolder.get(i).numOfPoints);
                fileWriter.write(pointHolder.get(i).name + " " + (pointHolder.get(i).numOfPoints) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("***COULD NOT WRITE TO FILE");
        }
    }

    //0 would be highest score
    public int newHighScore(Points playerScore) {
        for (int i = 0; i < pointHolder.size(); i++) {
            if (playerScore.numOfPoints >= pointHolder.get(i).numOfPoints) {
                return i;
            }
        }
        return -12;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void sortList() {
        int i = 0;
        while (tempHold.size() > 0) {
            Points max = tempHold.get(i);
            int ind = i;
            for (int j = i + 1; j < tempHold.size(); j++) {
                Points comp = tempHold.get(j);
                if (comp.numOfPoints > max.numOfPoints) {
                    max = comp;
                    ind = j;
                }
            }
            pointHolder.add(max);
            tempHold.remove(ind);

        }


    }
}
