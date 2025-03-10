// Wongsatorn Suwannarit 6581167

package Ex8_6581167;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class MainApplication extends JFrame implements KeyListener {
    private JLabel          contentpane;
    private CharacterLabel  []charLabels;
    private CharacterLabel  flyingLabel;
    private ItemLabel       itemLabel;
    
    private int framewidth   = MyConstants.FRAME_WIDTH;
    private int frameheight  = MyConstants.FRAME_HEIGHT;
    private int groundY      = MyConstants.GROUND_Y;
    private int skyY         = MyConstants.SKY_Y;
    private int chwidth      = MyConstants.CH_WIDTH;
    private int chheight     = MyConstants.CH_HEIGHT;
    private int itwidth      = MyConstants.IT_WIDTH;
    private int itheight     = MyConstants.IT_HEIGHT;

    public static void main(String[] args) { new MainApplication(); }	    
    
    public MainApplication() {
	    setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	    setVisible(true);
        setTitle("Wings off");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	// set background image by using JLabel as contentpane
        setContentPane(contentpane = new JLabel());
        MyImageIcon background = new MyImageIcon(MyConstants.FILE_BG).resize(framewidth, frameheight);
        contentpane.setIcon(background);
	    contentpane.setLayout(null);

        charLabels    = new CharacterLabel[2];
	    charLabels[0] = new CharacterLabel(MyConstants.FILE_CH_1_MAIN, MyConstants.FILE_CH_1_ALT, chwidth, chheight, this); 
        charLabels[0].setName("Marmite");        
        charLabels[0].setMoveConditions(200, groundY, true, false); 
        
        charLabels[1] = new CharacterLabel(MyConstants.FILE_CH_2_MAIN, MyConstants.FILE_CH_2_ALT, chwidth, chheight, this); 
        charLabels[1].setName("Butter");        
        charLabels[1].setMoveConditions(500, groundY, true, false);
                    
        itemLabel = new ItemLabel(MyConstants.FILE_ITEM, itwidth, itheight, this);
        itemLabel.setMoveConditions(200, skyY, true, true);        

        // first added label is at the front, last added label is at the back
        contentpane.add(itemLabel);
        contentpane.add(charLabels[0]);
        contentpane.add(charLabels[1]);
        
        addKeyListener(this);

        // validate();
	    repaint();
    }
    
    public CharacterLabel[] getAllCharLabels() { return charLabels; }    
    public CharacterLabel   getFlyingLabel()   { return flyingLabel; }    
    public void setFlying(CharacterLabel cb)   { flyingLabel = cb; }

    public int getGroundY()                    { return groundY; }

    @Override
    public void keyPressed(KeyEvent e) {
        int key_Pressed = e.getKeyCode();
        System.out.printf("{%s} ", e.getKeyText(key_Pressed));
        switch (key_Pressed) {
            case KeyEvent.VK_W:
                charLabels[0].moveUp();
                break;
            case KeyEvent.VK_A:
                charLabels[0].moveLeft();
                break;
            case KeyEvent.VK_S:
                charLabels[0].moveDown();
                break;
            case KeyEvent.VK_D:
                charLabels[0].moveRight();
                break;
            case KeyEvent.VK_UP:
                charLabels[1].moveUp();
                break;
            case KeyEvent.VK_DOWN:
                charLabels[1].moveDown();
                break;
            case KeyEvent.VK_LEFT:
                charLabels[1].moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                charLabels[1].moveRight();
                break;
            default:
                if (key_Pressed == KeyEvent.VK_ESCAPE && flyingLabel != null) {
                    flyingLabel.wingsOff();
                    System.out.println("1");
                    itemLabel.reset();
                    System.out.println("2");
                    flyingLabel = null;
                    System.out.println("3");
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}

////////////////////////////////////////////////////////////////////////////////
abstract class BaseLabel extends JLabel
{
    protected String           name;
    protected MyImageIcon      iconMain, iconAlt;
    protected int              curX, curY, width, height;
    protected boolean          horizontalMove, verticalMove;
    protected MainApplication  parentFrame;  

    protected int              speed = MyConstants.SPEED;
    
    public BaseLabel() { }
    public BaseLabel(String file1, String file2, int w, int h, MainApplication pf) {
        width = w; height = h;
        iconMain = new MyImageIcon(file1).resize(width, height);  
	    setIcon(iconMain);        
	    setHorizontalAlignment(JLabel.CENTER);
        parentFrame = pf;
        
        if (file2 != null) iconAlt = new MyImageIcon(file2).resize(width, height);
        else               iconAlt = null;
    }

    public void setName(String n)   { name = n; }
    public String getName()         { return name; }
    public void setMainIcon()       { setIcon(iconMain); }    
    public void setAltIcon()        { setIcon(iconAlt); }
    
    public void setMoveConditions(int x, int y, boolean hm, boolean vm) {
        curX = x; curY = y; 
        setBounds(curX, curY, width, height);
        setMoveConditions(hm, vm);
    }

    public void setMoveConditions(boolean hm, boolean vm) {
        horizontalMove = hm; verticalMove = vm;
    }
    
    abstract public void updateLocation(); 
}

////////////////////////////////////////////////////////////////////////////////
class CharacterLabel extends BaseLabel implements MouseListener { 

    public CharacterLabel(String file1, String file2, int w, int h, MainApplication pf) {
        super(file1, file2, w, h, pf);
        addMouseListener(this);
    }
    
    public void updateLocation() {
        setLocation(curX, curY);
        System.out.printf("Moving: (%d,%d)\n", curX, curY);
    }
    
    // speed = MyConstants.SPEED
    public void moveUp() {
        if (verticalMove) {
            if (curY - speed > 0) {
                curY -= speed;
                updateLocation();
            }
        }
    }
    public void moveDown() {
        if (verticalMove) {
            if (curY + speed < parentFrame.getHeight() - 80)
                curY += speed;
            updateLocation();
        }
    }
    public void moveLeft() {
        if (horizontalMove) {
            if (curX - speed > -80)
                curX -= speed;
            else
                curX = parentFrame.getWidth() - 10;
            updateLocation();
        }
    }
    public void moveRight() {
        if (horizontalMove) {
            if (curX + speed < parentFrame.getWidth())
                curX += speed;
            else
                curX = -width;
            updateLocation();
        }
    }

    public void wingsOff() {
        curY = parentFrame.getGroundY();
        updateLocation();

        setMoveConditions(true, false);
        setMainIcon();
        parentFrame.setTitle("Wings off");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (verticalMove) {
            Random rand = new Random();
            curX = rand.nextInt(0, parentFrame.getWidth() - 60);
            curY = rand.nextInt(0, parentFrame.getHeight() - 80);
            System.out.printf("{Entered %s} ", this.getName());
            updateLocation();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

////////////////////////////////////////////////////////////////////////////////
class ItemLabel extends BaseLabel implements MouseMotionListener {
    protected boolean inUse = false;

    public ItemLabel(String file, int w, int h, MainApplication pf) {
        width = w; height = h;
	    setIcon(new MyImageIcon(file).resize(width, height));        
	    setHorizontalAlignment(JLabel.CENTER);
        parentFrame = pf;
        addMouseMotionListener(this);
    }
    
    public void updateLocation() {
        setLocation(curX, curY);
        if (!inUse) {
            System.out.printf("Dragging: (%d,%d)\n", curX, curY);
        }
    }

    public void checkIntersection() {
        CharacterLabel[] characters = parentFrame.getAllCharLabels();
        if (!inUse) {
            for (CharacterLabel character : characters) {
                if (this.getBounds().intersects(character.getBounds())) {
                    this.setVisible(false);
                    character.setAltIcon();
                    character.setMoveConditions(true, true);
                    parentFrame.setFlying(character);
                    parentFrame.setTitle("Wings on " + character.getName());
                    inUse = true;
                    break;
                }
            }
        }
    }

    public void reset() {
        CharacterLabel[] characters = parentFrame.getAllCharLabels();
        Random rand = new Random();
        
        while (true) {  // Making sure, wings doesn't overlaps with other label when adding it back
            curX = rand.nextInt(0, parentFrame.getWidth() - 60);
            curY = rand.nextInt(0, parentFrame.getHeight() - 80);
            updateLocation();

            boolean intersect = false;
            for (CharacterLabel character : characters) {
                if (this.getBounds().intersects(character.getBounds())) {
                    intersect = true;
                    break;
                }
            }
            if (intersect) continue;
            else break;
        }

        setVisible(true);
        inUse = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        curX = getX() + e.getX() - width / 2;
        curY = getY() + e.getY() - height / 2;
        this.updateLocation();
        checkIntersection();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
