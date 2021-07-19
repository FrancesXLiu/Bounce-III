package bounce;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Simple GUI program to show an animation of shapes. Class AnimationViewer is
 * a special kind of GUI component (JPanel), and as such an instance of 
 * AnimationViewer can be added to a JFrame object. A JFrame object is a 
 * window that can be closed, minimised, and maximised. The state of an
 * AnimationViewer object comprises a list of Shapes and a Timer object. An
 * AnimationViewer instance subscribes to events that are published by a Timer.
 * In response to receiving an event from the Timer, the AnimationViewer iterates 
 * through a list of Shapes requesting that each Shape paints and moves itself.
 * 
 * 
 */
@SuppressWarnings("serial")
public class AnimationViewer extends JPanel implements ActionListener {
	// Frequency in milliseconds for the Timer to generate events.
	private static final int DELAY = 20;

	// Collection of Shapes to animate.
	private List<Shape> _shapes;

	private Timer _timer = new Timer(DELAY, this);

	/**
	 * Creates an AnimationViewer instance with a list of Shape objects and 
	 * starts the animation.
	 */
	public AnimationViewer() {
		_shapes = new ArrayList<Shape>();
	
		// Populate the list of Shapes.
		Shape s1 = new RectangleShape(20, 10, 2, 1, 60, 30, "Hello!!");
		_shapes.add(s1);

		/*Shape s3 = new OvalShape();
		_shapes.add(s3);

		
		
		Shape s4 = new DynamicRectangleShape(10, 10, 7, 5, 20, 37);
		_shapes.add(s4);

		Shape s5 = new DynamicRectangleShape(0, 0, 12, 6, 20, 30, Color.CYAN);
		_shapes.add(s5);

		Image image = ImageRectangleShape.makeImage("Holden.jpg", 50);
		Shape s2 = new ImageRectangleShape(1, 1, image);
		_shapes.add(s2);*/

		NestingShape top = new NestingShape(0, 0, 2, 2, 100, 100);
        NestingShape middle = new NestingShape(25, 50, 1, 2, 50, 50, "CS");
        NestingShape bottom = new NestingShape(40, 20, 1, 1, 10, 20);
		OvalShape oval = new OvalShape(1, 1, 2, 2, 9, 10);
		DynamicRectangleShape dynamic = new DynamicRectangleShape(40, 20, 2, 1, 20, 28, Color.CYAN);
		DynamicRectangleShape dynamicTexted = new DynamicRectangleShape(10, 10, 3, 2, 40, 25, Color.GRAY, "Haha");
		OvalShape oval2 = new OvalShape(200, 200, 1, 3, 30, 35, "230");


        middle.add(bottom);
		top.add(dynamic);
		top.add(middle);
		bottom.add(oval);
		_shapes.add(top);
		_shapes.add(dynamicTexted);
		_shapes.add(oval2);


		// Start the animation.
		_timer.start();
	}

	/**
	 * Called by the Swing framework whenever this AnimationViewer object
	 * should be repainted. This can happen, for example, after an explicit 
	 * repaint() call or after the window that contains this AnimationViewer 
	 * object has been opened, exposed or moved.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Call inherited implementation to handle background painting.
		super.paintComponent(g);
		
		// Calculate bounds of animation screen area.
		int width = getSize().width;
		int height = getSize().height;
		
		// Create a GraphicsPainter that Shape objects will use for drawing.
		// The GraphicsPainter delegates painting to a basic Graphics object.
		Painter painter = new GraphicsPainter(g);
		
		// Progress the animation.
		for(Shape s : _shapes) {
			s.paint(painter);
			s.move(width, height);
		}
	}

	/**
	 * Notifies this AnimationViewer object of an ActionEvent. ActionEvents are
	 * received by the Timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Request that the AnimationViewer repaints itself. The call to 
		// repaint() will cause the AnimationViewer's paintComponent() method 
		// to be called.
		repaint();
	}
	
	/**
	 * Main program method to create an AnimationViewer object and display this
	 * within a JFrame window.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Animation viewer");
				frame.add(new AnimationViewer());
		
				// Set window properties.
				frame.setSize(500, 500);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
