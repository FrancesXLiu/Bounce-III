package bounce;

import java.util.*;
import java.awt.*;

/**
 * Abstract superclass to represent the general concept of a Shape. This class
 * defines state common to all special kinds of Shape instances and implements
 * a common movement algorithm. Shape subclasses must override method paint()
 * to handle shape-specific painting. 
 *
 */
public abstract class Shape {
	
	protected boolean colored = true;
	protected boolean texted = false;
	/**
	 * Returns the NestingShape that contains the Shape that method parent is called on. If the callee
	 * object is not a child within a NestingShape instance this method returns null.
	 */
	public Shape parent = null;
	public void setParent(NestingShape parent){
		this.parent = (NestingShape)parent;
	}

	public NestingShape parent(){
		return (NestingShape)this.parent;
	}

	public java.util.List <Shape> path(){
		ArrayList<Shape> path = new ArrayList<Shape>();
	    Shape shape = this;
	    path.add(shape);
	    while(shape.parent()!=null){
	        path.add(shape.parent());
	        shape = shape.parent();
	    }
	    Collections.reverse(path);
	    return path;
	}

	// === Constants for default values. ===
	protected static final int DEFAULT_X_POS = 0;
	
	protected static final int DEFAULT_Y_POS = 0;
	
	protected static final int DEFAULT_DELTA_X = 5;
	
	protected static final int DEFAULT_DELTA_Y = 5;
	
	protected static final int DEFAULT_HEIGHT = 35;

	protected static final int DEFAULT_WIDTH = 25;
	// ===

	// === Instance variables, accessible by subclasses.
	protected int _x;

	protected int _y;

	protected int _deltaX;

	protected int _deltaY;

	protected int _width;

	protected int _height;

	protected String _text;

	


	/**
	 * Creates a Shape object with default values for instance variables.
	 */
	public Shape() {
		this(DEFAULT_X_POS, DEFAULT_Y_POS, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * Creates a Shape object with a specified x and y position.
	 */
	public Shape(int x, int y) {
		this(x, y, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * Creates a Shape instance with specified x, y, deltaX and deltaY values.
	 * The Shape object is created with a default width and height.
	 */
	public Shape(int x, int y, int deltaX, int deltaY) {
		this(x, y, deltaX, deltaY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Creates a Shape instance with specified x, y, deltaX, deltaY, width and
	 * height values.
	 */
	public Shape(int x, int y, int deltaX, int deltaY, int width, int height) {
		_x = x;
		_y = y;
		_deltaX = deltaX;
		_deltaY = deltaY;
		_width = width;
		_height = height;
	}

	public Shape(int x, int y, int deltaX, int deltaY, int width, int height, String text){
		_x = x;
		_y = y;
		_deltaX = deltaX;
		_deltaY = deltaY;
		_width = width;
		_height = height;
		_text = text;
		texted = true;
	}

	public Shape(int x, int y, int deltaX, int deltaY, String text){
		_x = x;
		_y = y;
		_deltaX = deltaX;
		_deltaY = deltaY;
		_text = text;
		texted = true;
	}
	
	/**
	 * Moves this Shape object within the specified bounds. On hitting a 
	 * boundary the Shape instance bounces off and back into the two- 
	 * dimensional world. 
	 * @param width width of two-dimensional world.
	 * @param height height of two-dimensional world.
	 */
	public void move(int width, int height) {
		int nextX = _x + _deltaX;
        int nextY = _y + _deltaY;
        
		if (nextX <= 0) {
			nextX = 0;
            _deltaX = -_deltaX;
            colored = true;
		} else if (nextX + _width >= width) {
			nextX = width - _width;
            _deltaX = -_deltaX;
            colored = true;
		} 

		if (nextY <= 0 && nextX!=0 && nextX+_width<width) {
			nextY = 0;
            _deltaY = -_deltaY;
            colored = false;
		} else if (nextY + _height >= height && nextX+_width<width && nextX!=0) {
			nextY = height - _height;
            _deltaY = -_deltaY;
            colored = false;
		} else if (nextY<=0 && nextX+_width >= width){
		    nextY = 0;
		    nextX = width-_width;
		    _deltaY = -_deltaY;
		    colored = true;
		} else if (nextY + _height >= height && nextX + _width >= width){
		    nextY = height - _height;
		    nextX = width - _width;
		    _deltaY = -_deltaY;
		    colored = true;
		} else if (nextX <= 0 && nextY + _height >= height){
		    nextX = 0;
		    nextY = height - _height;
		    _deltaY = -_deltaY;
		    colored = true;
		}

		_x = nextX;
        _y = nextY;
	}

	protected abstract void doPaint(Painter painter);

	public void paint(Painter painter){
		doPaint(painter);
		if(texted==true){
			painter.drawCenteredText(_text, (_x+_width/2), (_y+_height/2));
		}
	}



	/**
	 * Returns this Shape object's x position.
	 */
	public int x() {
		return _x;
	}
	
	/**
	 * Returns this Shape object's y position.
	 */
	public int y() {
		return _y;
	}
	
	/**
	 * Returns this Shape object's speed and direction.
	 */
	public int deltaX() {
		return _deltaX;
	}
	
	/**
	 * Returns this Shape object's speed and direction.
	 */
	public int deltaY() {
		return _deltaY;
	}
	
	/**
	 * Returns this Shape's width.
	 */
	public int width() {
		return _width;
	}
	
	/**
	 * Returns this Shape's height.
	 */
	public int height() {
		return _height;
	}

	/**
	 * Returns this Shape's text.
	 */
	public String text(){
		return _text;
	}
	
	/**
	 * Returns a String whose value is the fully qualified name of this class 
	 * of object. E.g., when called on a RectangleShape instance, this method 
	 * will return "bounce.RectangleShape".
	 */
	@Override
	public String toString() {
		return getClass().getName();
	}
}
