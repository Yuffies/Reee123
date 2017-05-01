package android.reee123;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private FlexboxLayout flexboxLayout;
    private int IMAGE_W = 0;
    private int IMAGE_H = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flexboxLayout = (FlexboxLayout) findViewById(R.id.images_grid);
        flexboxLayout.setTop(0);
    }

    public void addImageToGrid(String imageURL) {
        ImageView imageView = new ImageView(this);
        imageView.setMaxHeight(IMAGE_H);
        imageView.setMaxWidth(IMAGE_W);
        imageView.setMinimumHeight(IMAGE_H);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(IMAGE_W, IMAGE_H));
        URL url;
        try {
            url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            Bitmap img = BitmapFactory.decodeStream(is);
            imageView.setImageBitmap(img);
            flexboxLayout.addView(imageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addImageToGrid(Bitmap imageURL) {
        ImageView imageView = new ImageView(this);
        imageView.setMinimumHeight(250);
        imageView.setMinimumWidth(250);
        imageView.setImageBitmap(imageURL);
        flexboxLayout.addView(imageView);
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
                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
