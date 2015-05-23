package test.com.ibeacontest.Splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import test.com.ibeacontest.R;
import test.com.ibeacontest.activities.BaseActivity;
import test.com.ibeacontest.activities.DeviceScanActivity;
import test.com.ibeacontest.utils.BlurBuilder;

/**
 * Created by Egor on 23.05.2015.
 */
public class SplashScreenActivity extends BaseActivity{
    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_splash;
    }

    @Override
    protected String getTitleToolBar() {
        return null;
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ImageView imgBck = (ImageView) findViewById(R.id.img_bck);

        Bitmap blurredBitmap = BlurBuilder.blur(mContext, BitmapFactory.decodeResource(getResources(), R.drawable.splash_image));
        imgBck.setImageDrawable(new BitmapDrawable(getResources(), blurredBitmap));

        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try {
                    sleep(2500);
                    startActivity(new Intent(SplashScreenActivity.this, DeviceScanActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        logoTimer.start();

    }
}
