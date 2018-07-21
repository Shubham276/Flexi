package india.com.flexi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import india.com.flexi.ConnectivityController;
import india.com.flexi.R;

public class Splash extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView splash;
    private String url = "http://ch.lnwfile.com/_/ch/_raw/0s/af/vk.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        splash = (ImageView) findViewById(R.id.glide);
        if(ConnectivityController.isNetworkAvailable(getApplicationContext())) {
        imageDownload();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(4000);
                    Intent intent = new Intent(Splash.this, RechargeActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
        else {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Internet Not Available");
            dialog.setCancelable(false);
            dialog.show();
        }
}



    @Override
    protected void onStop() {
        super.onStop();
        this.finish();

    }

    public void  imageDownload()
    {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this).load(url).error(R.drawable.flexi).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(splash);

    }

}

