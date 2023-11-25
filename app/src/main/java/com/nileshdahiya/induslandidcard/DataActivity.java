package com.nileshdahiya.induslandidcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class DataActivity extends AppCompatActivity {

    EditText acn,ifs,cid,name,mob,adh,adr;
    Button sub,reset,select;
    ImageView iv;
    private static final int PICK_IMAGE_REQUEST = 1;
    static final int WRITE_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        String myFolder = "/indusland";
        // Create the target directory if it doesn't exist
        File directory = new File(Environment.getExternalStorageDirectory() +myFolder );
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!directory.exists()) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                directory.mkdirs();
            } else {
                // Request write permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
            }
        }
        acn = findViewById(R.id.acnumtxt2);
        ifs = findViewById(R.id.ifsctxt2);
        cid = findViewById(R.id.ciftxt2);
        name = findViewById(R.id.nametxt2);
        mob = findViewById(R.id.numtxt2);
        adh = findViewById(R.id.adhartxt2);
        adr = findViewById(R.id.addresstxt2);
        sub = findViewById(R.id.generate);
        reset = findViewById(R.id.reset);
        select = findViewById(R.id.select);
        iv = findViewById(R.id.custimg);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateId();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetField();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }

    private void resetField() {
        acn.setText(" ");
        ifs.setText(" ");
        cid.setText(" ");
        name.setText(" ");
        mob.setText(" ");
        adr.setText(" ");
        adh.setText(" ");
        iv.setImageResource(R.drawable.ic_launcher_foreground);
        Toast.makeText(this,"field reset Complete!",Toast.LENGTH_LONG).show();

    }

    private void generateId() {



        if (iv.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            Intent i = new Intent(DataActivity.this, MainActivity.class);
            i.putExtra("image", byteArray);
            i.putExtra("acnum",acn.getText().toString());
            i.putExtra("ifs",ifs.getText().toString());
            i.putExtra("cid",cid.getText().toString());
            i.putExtra("name",name.getText().toString());
            i.putExtra("mob",mob.getText().toString());
            i.putExtra("adr",adr.getText().toString());
            i.putExtra("adh",adh.getText().toString());

            startActivity(i);

        }else {
           Toast.makeText(this,"Select Image",Toast.LENGTH_LONG).show();


        }


    }
//    public void selectImage(View view) {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                iv.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}