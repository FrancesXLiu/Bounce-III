package bounce.views;

import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import bounce.ShapeModelListener;
import bounce.ShapeModel;
import bounce.ShapeModelEvent;
import bounce.Shape;


public class TreeModelAdapterWithShapeModelListener extends TreeModelAdapter implements ShapeModelListener{
    private ArrayList<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
    TreeModelEvent treeEvent;

    public TreeModelAdapterWithShapeModelListener(ShapeModel shape){
        super(shape);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
    
    @Override
    public void update(ShapeModelEvent shapeEvent){
        try{
            ShapeModel shapeFiredEvent = shapeEvent.source();
            TreePath path = new TreePath(shapeEvent.parent().path().toArray());
            int[] shapeIndexArray = {shapeEvent.index()};
            Shape[] thisShape = {shapeEvent.operand()};
            treeEvent = new TreeModelEvent(shapeFiredEvent, path, shapeIndexArray, thisShape);
        } catch (NullPointerException e){
            return;
        }
        

        switch(shapeEvent.eventType()){
            case ShapeAdded:
                for(int i=0; i<listeners.size(); i++){
                    listeners.get(i).treeNodesInserted(treeEvent);
                }
                break;
            case ShapeRemoved:
                for(int i=0; i<listeners.size(); i++){
                    listeners.get(i).treeNodesRemoved(treeEvent);
                }
                break;
            default:
                break;
        }
    }
}

