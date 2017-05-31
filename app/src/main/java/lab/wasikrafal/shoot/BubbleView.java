package lab.wasikrafal.shoot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Rafał on 31.05.2017.
 */

public class BubbleView extends View
{
    private int diameter;
    private int x;
    private int y;
    private ShapeDrawable bubble;
    Context c;
    public BubbleView(Context context) {
        super(context);
        c=context;
        createBubble();
    }
    private int width; private int height;
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
        diameter = 100;
        bubble = new ShapeDrawable(new OvalShape());
        bubble.setBounds(x, y, x + diameter, y + diameter);
        bubble.getPaint().setColor(0xff74AC23);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
}
