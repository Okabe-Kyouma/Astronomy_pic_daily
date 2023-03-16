package com.example.astronomypicdaily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astronomypicdaily.Structure.RequestManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    ImageView text_image;
    TextView text_title,text_explain,text_Owner_name,text_time;
    Button button;
    Bitmap bitmap;
    BitmapDrawable bitmapDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_image = findViewById(R.id.text_image);
        text_title = findViewById(R.id.text_title);
        text_explain = findViewById(R.id.text_explain);
        text_Owner_name = findViewById(R.id.text_Owner_name);
        text_time = findViewById(R.id.text_time);

        RequestManager requestManager = new RequestManager(this);

        requestManager.getResponse(listener);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitmapDrawable = (BitmapDrawable) text_image.getDrawable();
                bitmap = bitmapDrawable.getBitmap();

                FileOutputStream fileOutputStream = null;

                File card = Environment.getExternalStorageDirectory();
                File directory = new File(card.getAbsolutePath() + "/Download");
                directory.mkdir();

                String filename = String.format("%d.jpg",System.currentTimeMillis());
                File output = new File(directory,filename);

                Toast.makeText(MainActivity.this,"Image Saved Successfully",Toast.LENGTH_SHORT).show();

                try{

                    fileOutputStream = new FileOutputStream(output);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(output));
                    sendBroadcast(intent);


                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(String copyright, String date, String explanation, String hdurl, String media_type, String service_version, String title, String url, String message) {

              display(copyright,date,explanation,hdurl,media_type,service_version,title,url);

        }

        @Override
        public void onError(String message) {

            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();

        }
    };

    private void display(String copyright, String date, String explanation, String hdurl, String media_type, String service_version, String title, String url) {

        text_title.setText(title);
        text_time.setText(date);
        text_explain.setText(explanation);
        text_Owner_name.setText(copyright);

        Picasso.get().load(hdurl).into(text_image);


    }


}