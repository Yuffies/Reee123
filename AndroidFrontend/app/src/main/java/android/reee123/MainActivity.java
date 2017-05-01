package android.reee123;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayout;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private FlexboxLayout flexboxLayout;
    private int IMAGE_W = 250;
    private int IMAGE_H = 250;
    private int currentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flexboxLayout = (FlexboxLayout) findViewById(R.id.images_grid);
        flexboxLayout.setTop(0);
    }

    public void addImageToGrid(Bitmap imageURL) {
        final ImageView imageView = new ImageView(this);
        final Context context = this.getApplicationContext();
        imageView.setId(currentId++);
        imageView.setMinimumHeight(IMAGE_H);
        imageView.setMinimumWidth(IMAGE_W);
        imageView.setImageBitmap(imageURL);

        // Tilføj onClickListener til at kunne previewe billederne
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                Intent intent = new Intent(getApplicationContext(), ImagePreview.class);
                intent.putExtra("image", b);
                startActivity(intent);
            }
        });
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
