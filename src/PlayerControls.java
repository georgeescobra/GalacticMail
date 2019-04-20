package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerControls implements KeyListener{
    private SpaceMailMan player;
    private final int up;
    private final int down;

    //this should only be allowed when attached to planets
    private final int right;
    private final int left;
    private final int launch;

    public PlayerControls(SpaceMailMan p, int up, int down, int left, int right, int launch){
        this.player = p;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.launch = launch;
    }

    @Override
    public void keyTyped(KeyEvent ke){

    }

    @Override
    public void keyPressed(KeyEvent ke){
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.player.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.player.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.player.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.player.toggleRightPressed();
        }
        if (keyPressed == launch){
            this.player.toggleLaunchPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();
        if (keyReleased  == up) {
            this.player.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.player.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.player.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.player.unToggleRightPressed();
        }
        if (keyReleased == launch){
            this.player.unToggleLaunchPressed();
        }

    }

}
