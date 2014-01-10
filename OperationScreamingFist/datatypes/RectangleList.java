package OperationScreamingFist.datatypes;

import java.util.ArrayList;

public class RectangleList {
    
    private ArrayList<Rectangle> rects; 
    
    public RectangleList() {
        rects = new ArrayList<Rectangle>();
    }
    
    public void add(Rectangle r) {
        rects.add(r);
    }
    
    public Rectangle get(int index) {
        return rects.get(index);
    }
    
    public int size() {
        return rects.size();
    }

    public int whichRectContains(int x, int y) {
        for (int i =0; i < size(); i++) {
            if (get(i).contains(x,y)) {
                return i;
            }
        }
        return -1;
    }

}
