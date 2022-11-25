package ru.ivanmurzin.awesomebird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        Canvas canvas = surfaceHolder.lockCanvas();
        if (color == 0) canvas.drawColor(Color.GREEN);
        if (color == 1) canvas.drawColor(Color.YELLOW);
        if (color == 2) canvas.drawColor(Color.RED);
        color = (color + 1) % 3;
        surfaceHolder.unlockCanvasAndPost(canvas);
        drawThread = new DrawThread();
        drawThread.start();
    }
    private int color = 0;

    private class DrawThread extends Thread {
        private volatile boolean running = true;
        @Override
        public void run() {
            while (running) {
                Canvas canvas = surfaceHolder.lockCanvas();
                try {
                    Thread.sleep(1000);
                    // что-нить рисовать
                    if (color == 0) canvas.drawColor(Color.GREEN);
                    if (color == 1) canvas.drawColor(Color.YELLOW);
                    if (color == 2) canvas.drawColor(Color.RED);
                    color = (color + 1) % 3;
                } catch (Exception e) {
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // при изменении ориентации
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
    }
}
