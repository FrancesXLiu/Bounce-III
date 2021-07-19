package bounce.forms;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;
import javax.imageio.ImageIO;

import bounce.ImageRectangleShape;
import bounce.NestingShape;
import bounce.ShapeModel;
import bounce.forms.util.Form;
import bounce.forms.util.FormHandler;

public class ImageShapeFormHandler implements FormHandler{
    private ShapeModel _model;
    private NestingShape _parentOfNewShape;
    private SwingWorker<BufferedImage, Void> _worker;
    private Form _form;
    private int deltaX;
    private int deltaY;

	
	/**
	 * Creates a SimpleImageShapeFormHandler.
	 * 
	 * @param model the ShapeModel to which the handler should add a newly 
	 *        constructed ImageRectangleShape object. 
	 * @param parent the NestingShape object that will serve as the parent for
	 *        a new ImageRectangleShape instance.
	 */
	public ImageShapeFormHandler(ShapeModel model, NestingShape parent) {
		_model = model;
		_parentOfNewShape = parent;
	}
	
	/**
	 * Reads form data that describes an ImageRectangleShape. Based on the 
	 * data, this SimpleImageShapeFormHandler creates a new ImageRectangleShape 
	 * object, adds it to a ShapeModel and to a NestingShape within the model.
	 * 
	 * @param form the Form that contains the ImageRectangleShape data.
	 */
	@Override
	public void processForm(Form form) {
        _form = form;
        _worker = new Worker();
        _worker.execute();
    }
    
    private class Worker extends SwingWorker<BufferedImage, Void>{
        @Override
        protected BufferedImage doInBackground() throws Exception {
            long startTime = System.currentTimeMillis();
        
            // Read field values from the form.
            File imageFile = (File)_form.getFieldValue(File.class, ImageFormElement.IMAGE);
            int width = _form.getFieldValue(Integer.class, ShapeFormElement.WIDTH);
            deltaX = _form.getFieldValue(Integer.class, ShapeFormElement.DELTA_X);
            deltaY = _form.getFieldValue(Integer.class, ShapeFormElement.DELTA_Y);

            // Load the original image (ImageIO.read() is a blocking call).
            BufferedImage fullImage = null;
            try {
                fullImage = ImageIO.read(imageFile);
            } catch(IOException e) {
                System.out.println(e);
                System.out.println("Error loading image.");
            }
            
            int fullImageWidth = fullImage.getWidth();
            int fullImageHeight = fullImage.getHeight();
                    
            BufferedImage scaledImage = fullImage;
                    
            // Scale the image if necessary.
            if(fullImageWidth > width) {
                double scaleFactor = (double)width / (double)fullImageWidth;
                int height = (int)((double)fullImageHeight * scaleFactor);
                        
                scaledImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
                Graphics2D g = scaledImage.createGraphics();
                        
                // Method drawImage() scales an already loaded image. The 
                // ImageObserver argument is null because we don't need to monitor 
                // the scaling operation.
                g.drawImage(fullImage, 0, 0, width, height, null);
            }
            
            // Create the new Shape and add it to the model.
            ImageRectangleShape imageShape = new ImageRectangleShape(deltaX, deltaY, scaledImage);
            _model.add(imageShape, _parentOfNewShape);
            
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Image loading and scaling took " + elapsedTime + "ms.");

            return scaledImage;
        }
    }
}