package com.lyte.adaboo.lyteapp;

/**
 * Created by adaboo on 4/30/17.
 */

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.PixelFormat;
        import android.os.Bundle;
        import android.view.Window;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.LinearLayout;

public class splash extends Activity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        StartAnimations();
       // Intent mains = new Intent(splash.this, show_progress.class);
       // startActivity(mains);



    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(splash.this,
                            show_progress.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    splash.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    splash.this.finish();
                }

            }
        };
        splashTread.start();



    }

}