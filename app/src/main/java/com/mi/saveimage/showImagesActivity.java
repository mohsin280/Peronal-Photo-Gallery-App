package com.mi.saveimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class showImagesActivity extends AppCompatActivity {
    private DatabaseHandler objectDatabaseHandler;
    private RecyclerView rv_showImages;
    private RvAdapter objectRvAdapter;
    Button btn_showImages;
    Button btn_showImagesAsc;
    Button btn_delete;
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        try{
            rv_showImages=findViewById(R.id.rv_showImages);
            btn_showImages=findViewById(R.id.btn_showImages);
            btn_showImagesAsc=findViewById(R.id.btn_showImagesAsc);
            btn_delete=findViewById(R.id.btn_delete);
            btn_search=findViewById(R.id.btn_search);
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
        btn_showImagesAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataAsc();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertBoxDelete("Delete");

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertBoxSearch("Serach");
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
    public void getDataAsc(){
        try{
            objectRvAdapter=new RvAdapter(objectDatabaseHandler.getAllImagesDataInAscending());
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
    public void AlertBoxDelete(String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Type a image name!!");
        builder.setView(input);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteDataAlertBox(input);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void deleteDataAlertBox(EditText input)
    {
        Integer deletedRows = objectDatabaseHandler.deleteData(input.getText().toString());
        if(deletedRows > 0)
            Toast.makeText(this,"Data Deleted!",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Wrong Input !!Data not Deleted!",Toast.LENGTH_LONG).show();
    }
    public void AlertBoxSearch(String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Type a image name!!");
        builder.setView(input);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                updateRecycle(input.getText().toString());


            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void updateRecycle(String input)
    {
        ArrayList<ModelClass> arrayList = objectDatabaseHandler.getImage(input);
        objectRvAdapter=new RvAdapter(arrayList);
        rv_showImages.setHasFixedSize(true);
        LinearLayoutManager ilm = new LinearLayoutManager(this) ;
        rv_showImages.setLayoutManager(ilm);
        //ilm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_showImages.setAdapter(objectRvAdapter);
    }


}
