package sketch.km.com.etchasketch.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import sketch.km.com.etchasketch.R;

public class CanvasView extends View implements View.OnTouchListener {
    Paint mBlack;
    int mCursorSize;
    ArrayList<Point> cursorPositions;


    public CanvasView(Context context) {
        super(context);
        // TODO: call one constructor from the other
        initView();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int nextX = (int) motionEvent.getX();
        int nextY = (int) motionEvent.getY();
        cursorPositions.add(new Point(nextX, nextY));

        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cursorPositions.isEmpty()) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            cursorPositions.add(new Point(width/2, height/2));
        }

        drawLines(canvas);
        drawCursor(canvas);
    }

    private void initView() {
        mBlack = new Paint();
        mBlack.setColor(getResources().getColor(android.R.color.black));
        mBlack.setStrokeWidth(3);
        mCursorSize = (int) (getResources().getDimension(R.dimen.cursor_size));
        cursorPositions = new ArrayList<>(1);
        setOnTouchListener(this);
    }

    private void drawLines(Canvas canvas) {
        Point prev = cursorPositions.get(0);
        for (Point next: cursorPositions) {
            canvas.drawLine(prev.x, prev.y, next.x, next.y, mBlack);
            prev = next;
        }
    }

    private void drawCursor(Canvas canvas) {
        Point curr = cursorPositions.get(cursorPositions.size() - 1);
        canvas.drawCircle(curr.x, curr.y, mCursorSize, mBlack);
    }

    public class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
