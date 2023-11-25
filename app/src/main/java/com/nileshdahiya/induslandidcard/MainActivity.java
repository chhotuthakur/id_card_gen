package com.nileshdahiya.induslandidcard;

import static com.nileshdahiya.induslandidcard.DataActivity.WRITE_PERMISSION_REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    
    Button save;
    RelativeLayout rl;
    TextView acn,ifs,cid,name,mob,adh,adr;
    String acnum,ifss,cids,names,numb,adrs,adhr;
//    private static final int WRITE_PERMISSION_REQUEST_CODE = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acn = findViewById(R.id.acnum);
        ifs = findViewById(R.id.ifsc);
        cid = findViewById(R.id.cif);
        name = findViewById(R.id.name);
        mob = findViewById(R.id.num);
        adh = findViewById(R.id.adhar);
        adr = findViewById(R.id.address);
        save = findViewById(R.id.savebutton);
        rl = findViewById(R.id.idcard);




        acnum = getIntent().getStringExtra("acnum");
        ifss = getIntent().getStringExtra("ifs");
        cids = getIntent().getStringExtra("cid");
        names = getIntent().getStringExtra("name");
        numb = getIntent().getStringExtra("mob");
        adrs = getIntent().getStringExtra("adr");
        adhr = getIntent().getStringExtra("adh");

        acn.setText(acnum);
        ifs.setText(ifss);
        cid.setText(cids);
        name.setText(names);
        mob.setText(numb);
        adh.setText(adrs);
        adr.setText(adhr);

        ImageView imageViewSecond = findViewById(R.id.idimage);

        // Receive the image from the intent
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        // Set the received image to the ImageView in the SecondActivity
        imageViewSecond.setImageBitmap(bitmap);
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveRelativeLayoutToImage(rl,numb+ ".png");
            }

            
        });
    }

//    private void saveID() {
//
//        // Find the specific layout view by its ID
//        View specificLayout = findViewById(R.id.idcard);
//
//        // Capture the specific layout and save it to an image file
//        Bitmap screenshot = ScreenshotUtils.captureView(specificLayout);
//        ScreenshotUtils.saveBitmapToFile(screenshot, numb);
//    }

    public void saveRelativeLayoutToImage(View rl, String fileName) {
        String myFolder = "/indusland";
        // Create the target directory if it doesn't exist
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            File directory = new File(Environment.getExternalStorageDirectory() + myFolder);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Enable drawing cache
            rl.setDrawingCacheEnabled(true);

            // Capture the view as a bitmap
            Bitmap bitmap = Bitmap.createBitmap(rl.getWidth(), rl.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            rl.draw(canvas);

            // Write the bitmap to file
            File imageFile = new File(directory, numb+".png");
            try {
                FileOutputStream outStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();

                Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DataActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error saving id", Toast.LENGTH_SHORT).show();
            }

            // Disable drawing cache
            rl.setDrawingCacheEnabled(false);

        } else {
            // Request write permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
        }
    }

}