package android.reee123;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.reee123.R.styleable.View;
import static android.widget.GridLayout.spec;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private int IMAGE_W = 0;
    private int IMAGE_H = 0;
    private int arri = 0;
    private int arrj = 0;
    private int kat = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //DisplayMetrics disp = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(disp);

        //IMAGE_H = IMAGE_W = disp.widthPixels/2;
        System.out.println(IMAGE_H);

    }


    public void addImageToGrid(String imageURL) {


        //GridLayout layout = (GridLayout) findViewById(R.id.images_grid);
        LinearLayout layout = (LinearLayout) findViewById(R.id.images_grid);

        ImageView imageView = new ImageView(this);
        imageView.setMaxHeight(IMAGE_H);
        imageView.setMaxWidth(IMAGE_W);
        imageView.setMinimumHeight(IMAGE_H);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(IMAGE_W, IMAGE_H));
        URL url = null;
        try {
            url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            Bitmap img = BitmapFactory.decodeStream(is);
            imageView.setImageBitmap(img);
            layout.addView(imageView);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addImageToGrid(Bitmap imageURL) {
        //GridLayout layout = (GridLayout) findViewById(R.id.images_grid);
        FlexboxLayout layout = (FlexboxLayout) findViewById(R.id.images_grid);

        ImageView imageView = new ImageView(this);
        imageView.setMaxHeight(1000);
        imageView.setMaxWidth(1000);
        imageView.setMinimumHeight(250);
        imageView.setMinimumWidth(250);

        //imageView.setMaxHeight(IMAGE_H);
        //imageView.setMaxWidth(IMAGE_W);


        //if(arrj>1) arrj =0;
        //imageView.setLayoutParams(new GridLayout.LayoutParams(spec(arri++),spec(arrj++)));
        imageView.setImageBitmap(imageURL);
        layout.addView(imageView);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addImageToGrid(imageBitmap);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1 = 0;
        float x2 = 0;
        float DISTANCE = 100;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }


                    }

                    // Right to left swipe action
                    else {
                        System.out.println("rehehelllyyy");
                    }

                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

}
