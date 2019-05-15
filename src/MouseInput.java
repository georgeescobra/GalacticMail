package src;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import src.Game.*;


public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component btn = e.getComponent();
        if(btn.getName().equals("Start")){
            src.Game.newGame.startGame();
        }else if(btn.getName().equals("HighScore")){
            src.Game.newGame.showLeaderBoard();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
