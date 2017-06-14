package lab.wasikrafal.shoot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafał on 31.05.2017.
 */

public class BubbleView extends View
{
    private int diameter;
    private int circle_diameter;
    private int x;
    private int y;
    private int width;
    private int height;

    private ShapeDrawable bubble;
    List<Paint> paintList = new ArrayList<Paint>();
    List<Boolean> checkList = new ArrayList<Boolean>();

    Context c;

    public BubbleView(Context context) {
        super(context);
        c=context;
        createBubble();
    }

    private void createBubble() {
        WindowManager wm = (WindowManager)
               c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        x = width/2;
        y = height/2;
        diameter = height/6;
        circle_diameter = height/10;
        bubble = new ShapeDrawable(new OvalShape());
        bubble.setBounds(x, y, x + diameter, y + diameter);
        bubble.getPaint().setColor(0xff74AC23);

        for(int i=0; i<4; i++) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.FILL);
            paintList.add(p);
            checkList.add(false);
        }
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(width/3, height/3, circle_diameter, paintList.get(0));
        canvas.drawCircle(width/3, (height/3)*2, circle_diameter, paintList.get(1));
        canvas.drawCircle((width/3)*2, (height/3), circle_diameter, paintList.get(2));
        canvas.drawCircle((width/3)*2, (height/3)*2, circle_diameter, paintList.get(3));

        bubble.draw(canvas);
    }

    protected void move(float f, float g) {
        int tempx = (int) (x + g);
        int tempy = (int) (y + f);
        if (tempx>0 && tempx< width-diameter ) {
            x=tempx;
            bubble.setBounds(x, y, x + diameter, y + diameter);
        }
        if (tempy>0 && tempy< height-diameter ) {
            y=tempy;
            bubble.setBounds(x, y, x + diameter, y + diameter);
        }
    }

    public void isIn ()
    {
        if (x < width/3 && x> width/3-circle_diameter && y<height/3 && y>height/3-circle_diameter)
            changeColor(0 ,Color.GREEN);

        if (x < width/2 && x> width/3-circle_diameter && y<(height/3)*2 && y>(height/3)*2-circle_diameter)
            changeColor(1 ,Color.GREEN);

        if (x < (width/3)*2 && x> (width/3)*2-circle_diameter && y<height/3 && y>height/3-circle_diameter)
            changeColor(2 ,Color.GREEN);

        if (x < (width/3)*2 && x> (width/3)*2-circle_diameter && y<(height/3)*2 && y>(height/3)*2-circle_diameter)
            changeColor(3 ,Color.GREEN);

        isDone();
    }

    private void changeColor (int pos, int color)
    {
        paintList.get(pos).setColor(color);
        checkList.set(pos,true);
    }

    public boolean isDone()
    {
        boolean test = checkList.get(0) && checkList.get(1) && checkList.get(2) && checkList.get(3);

        return test;
    }


}
