package main;

import java.awt.event.*;

public class Controls implements KeyListener, MouseListener, MouseMotionListener {

    private boolean mouseClicked;
//    public boolean workerPressed;


    @Override
    public void mouseClicked(MouseEvent e) {
        if (mouseClicked) {
            mouseClicked = false;
        } else {
            mouseClicked = true;
        }
    }


    public boolean MouseIsClicked() {
        return mouseClicked;
    }



















    @Override
    public void mousePressed(MouseEvent e) {
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
