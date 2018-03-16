package lab.wasikrafal.shoot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
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
    private int objx;
    private int objy;
    private int enemyx;
    private int enemyy;

    Path[] objectives = new Path[4];
    //Path[] enemies = new Path[4];
    Paint[] objectivesPaint = new Paint[4];
    boolean[] check = new boolean[4];

    private ShapeDrawable bubble;
    ShapeDrawable[] enemies = new ShapeDrawable[4];

    //List<Paint> paintList = new ArrayList<Paint>();
    //List<Boolean> checkList = new ArrayList<Boolean>();

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
        diameter = height/8;
        circle_diameter = height/10;
        objx = width/4;
        objy=height/4;

        enemyx = width/12;
        enemyy = height/12;

        bubble = new ShapeDrawable();
        bubble.setBounds(x, y, x + diameter, y + diameter);
        bubble.getPaint().setColor(0xff74AC23);



        for(int i=0; i<4; i++)
        {
            objectivesPaint[i] = new Paint();
            objectivesPaint[i].setColor(Color.RED);
            objectivesPaint[i].setStyle(Paint.Style.FILL);


            check[i] = false;

            //checkList.add(false);
        }

        objectives[0] = new Path();
        objectives[0].addCircle(objx, objy, circle_diameter, Path.Direction.CCW);

        objectives[1] = new Path();
        objectives[1].addCircle(objx, objy*3, circle_diameter, Path.Direction.CCW);

        objectives[2] = new Path();
        objectives[2].addCircle(objx*3, objy, circle_diameter, Path.Direction.CCW);

        objectives[3] = new Path();
        objectives[3].addCircle(objx*3, objy*3, circle_diameter, Path.Direction.CCW);

        enemies[0] = new ShapeDrawable();
        enemies[0].setBounds(enemyx, enemyy, enemyx*2, enemyy*2);
        enemies[0].getPaint().setColor(Color.BLUE);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i=0; i<4; i++)
            canvas.drawPath(objectives[i], objectivesPaint[i]);

        enemies[0].draw(canvas);
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

    public void moveEnemy()
    {

    }

    public void isIn ()
    {
        if (x < objx+circle_diameter && x> objx-circle_diameter && y<objy+circle_diameter && y>objy-circle_diameter)
            changeColor(0 ,Color.GREEN);

        if (x < objx+circle_diameter && x> objx-circle_diameter && y<objy*3+circle_diameter && y>objy*3-circle_diameter)
            changeColor(1 ,Color.GREEN);

        if (x < objx*3+circle_diameter && x> objx*3 -circle_diameter && y<objy+circle_diameter && y>objy-circle_diameter)
            changeColor(2 ,Color.GREEN);

        if (x < objx*3 +circle_diameter && x> objx*3 -circle_diameter && y<objy*3+circle_diameter && y>objy*3-circle_diameter)
            changeColor(3 ,Color.GREEN);

        isDone();
    }

    public boolean checkHit()
    {
       if(isHit(0)) {

           return true;
       }
       return false;
    }

    private boolean isHit(int enemy)
    {

        if (x>enemyx && x<enemyx*2 && y>enemyy && y< enemyy*2)
            return true;
        return false;
    }

    public void resetBubble()
    {
        x = width/2;
        y = height/2;
        bubble.setBounds(x, y, x + diameter, y + diameter);

    }

    private void changeColor (int pos, int color)
    {
        objectivesPaint[pos].setColor(color);
        check[pos] = true;
    }

    public boolean isDone()
    {
        boolean test = check[0] && check[1] && check[2] && check[3];

        return test;
    }


}
