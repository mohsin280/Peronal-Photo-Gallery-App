package com.mi.saveimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class showImagesActivity extends AppCompatActivity {
    private DatabaseHandler objectDatabaseHandler;
    private RecyclerView rv_showImages;
    private RvAdapter objectRvAdapter;
    Button btn_showImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        try{
            rv_showImages=findViewById(R.id.rv_showImages);
            btn_showImages=findViewById(R.id.btn_showImages);
            objectDatabaseHandler = new DatabaseHandler(this);
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }


        btn_showImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }
    public void getData(){
       try{
           objectRvAdapter=new RvAdapter(objectDatabaseHandler.getAllImagesData());
           rv_showImages.setHasFixedSize(true);
           LinearLayoutManager ilm = new LinearLayoutManager(this) ;
           rv_showImages.setLayoutManager(ilm);
           //ilm.setOrientation(LinearLayoutManager.VERTICAL);
           rv_showImages.setAdapter(objectRvAdapter);
       }
       catch (Exception e)
       {
           Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
       }
    }
}
