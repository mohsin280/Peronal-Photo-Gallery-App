package com.mi.saveimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_imgName;
    private ImageView iv_image;
    private Button btn_saveImg;
    private Button btn_gallery;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    DatabaseHandler objectDatabaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_imgName=findViewById(R.id.et_imgName);
        iv_image=findViewById(R.id.iv_image);
        btn_saveImg=findViewById(R.id.btn_saveImg);
        btn_gallery=findViewById(R.id.btn_gallery);

        objectDatabaseHandler=new DatabaseHandler(this);

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        btn_saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeImage(v);
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,showImagesActivity.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
            {
                imageFilePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                iv_image.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void storeImage(View view)
    {
        try {
            if(!et_imgName.getText().toString().isEmpty() && iv_image.getDrawable()!=null && imageToStore!=null)
            {
                objectDatabaseHandler.storeImages(new ModelClass(et_imgName.getText().toString(),imageToStore));
            }
            else{
                Toast.makeText(this,"please select image or type image name  ",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
