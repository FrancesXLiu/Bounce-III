package bounce;

import java.awt.Color;
public class DynamicRectangleShape extends RectangleShape{
    private Color color = Color.red;
    private boolean colored = true;

    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height){
        super(x, y, deltaX, deltaY, width, height);
	}
	
	public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, String text){
		super(x, y, deltaX, deltaY, width, height, text);
	}

    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, Color color){
        super(x, y, deltaX, deltaY, width, height);
        this.color = color;
	}
	
	public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, Color color, String text){
		super(x, y, deltaX, deltaY, width, height, text);
		this.color = color;
	}

    public void doPaint(Painter painter){
        if(colored){
            painter.setColor(this.color);
            painter.fillRect(_x, _y, _width, _height);
            painter.setColor(Color.black);
        }else{
            painter.drawRect(_x, _y, _width, _height);
        }
    }

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
}