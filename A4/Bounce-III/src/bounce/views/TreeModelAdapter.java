package bounce.views;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;

import bounce.NestingShape;
import bounce.Shape;
import bounce.ShapeModel;
//import bounce.ShapeModelEvent;
//import bounce.ShapeModelListener;

public class TreeModelAdapter implements TreeModel {
    private ShapeModel _adaptee;

    public TreeModelAdapter(ShapeModel shape){
        _adaptee = shape;
    }
    
    @Override
    public Object getRoot(){
        return _adaptee.root();
    }

    @Override
    public Object getChild(Object parent, int index){
        if(parent instanceof NestingShape && (index>=0 && index < getChildCount(parent))){
            return ((NestingShape)parent).shapeAt(index);
        }else{
            return null;
        }
    }

    @Override
    public int getChildCount(Object parent){
        if(parent instanceof NestingShape){
            return ((NestingShape)parent).shapeCount();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isLeaf(Object node){
        if(node instanceof NestingShape){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {}
    
    @Override
    public int getIndexOfChild(Object parent, Object child){
        if(parent instanceof NestingShape && child instanceof Shape){
            return ((NestingShape)parent).indexOf((Shape)child);
        }else{
            return -1;
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener l){}

    @Override
    public void removeTreeModelListener(TreeModelListener l){}


}