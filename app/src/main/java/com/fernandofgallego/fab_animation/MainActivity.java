package com.fernandofgallego.fab_animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;


public class MainActivity extends Activity {

    View controls2;
    View controls;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controls = findViewById(R.id.controls);
        controls2 = findViewById(R.id.controls2);
        fab = (FloatingActionButton) findViewById(R.id.fab_1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doReveal();
            }
        });

        ((View)controls.getParent()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getApplicationContext(),String.format("%f %f", motionEvent.getX(),motionEvent.getY()),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void doReveal() {
        // get the center for the clipping circle
        final int cx = (controls2.getLeft() + controls2.getRight()) / 2;
        final int cy = (controls2.getTop() + controls2.getBottom()) / 2;
        // get the final radius for the clipping circle
        final int finalRadius = Math.max(controls2.getWidth(), controls2.getHeight());
        try {

//            controls2.setVisibility(View.VISIBLE);
//            reveal.setDuration(1000);
//            reveal.start();

            final float fabW = dpTopx(32);
            float fcx = (fab.getLeft() + fab.getRight()) / 2 -fabW;
            float fcy = (fab.getTop() + fab.getBottom()) / 2 -fabW;

            Path path = new Path();
            float left = fabW;
            float top = fab.getTop()-50-fabW;
            float right = fab.getLeft();
            float bottom = fab.getBottom()+50;



//            path.moveTo(fcx,fcy);
            path.arcTo(left,top,right,bottom,0f,90f,true);


            Animator translate = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
            translate.setInterpolator(new AccelerateInterpolator());
            translate.setDuration(500);
            translate.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    Animator reveal = ViewAnimationUtils.createCircularReveal(controls2, 550,200, fab.getWidth(), finalRadius);
                    controls2.setVisibility(View.VISIBLE);
//                  reveal.setStartDelay(400);
                    reveal.start();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            translate.start();



            Animator elevation = ObjectAnimator.ofFloat(fab,"elevation",fab.getElevation(),0.1f);
            elevation.start();


            Log.i("VIEW", String.format("%f %f %d %d", fcx, fcy, cx, cy));



//            reveal.setStartDelay(500);
//            reveal.start();

        }catch (Exception e) {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public float dpTopx(int dp) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
