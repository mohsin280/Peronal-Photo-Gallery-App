package com.mi.saveimage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.graphics.BitmapFactory.decodeByteArray;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    public static final String DATABASE_NAME = "mydb.db";
    public static final int DATABASE_VERSION = 2;
    private static String createTableQuery="create table imageInfo (imageName TEXT PRIMARY KEY" +
            ",image BLOB)";
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTableQuery);
            Toast.makeText(context, "Database created successfully", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
     public void storeImages(ModelClass object)
     {
         try {
             SQLiteDatabase objectSqlLiteDatabase =this.getWritableDatabase();
             Bitmap imageToStoreBitmap=object.getImage();

             objectByteArrayOutputStream=new ByteArrayOutputStream();
             imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
            imageInBytes=objectByteArrayOutputStream.toByteArray();
             ContentValues obj = new ContentValues();
             obj.put("imageName",object.imageName);
             obj.put("image",imageInBytes);
             long result=objectSqlLiteDatabase.insert("imageInfo",null,obj);
             if(result!=-1)
             {
                 Toast.makeText(context, "data inserted into a table!!", Toast.LENGTH_LONG).show();
             }
             else{
                 Toast.makeText(context, "Fail to add in table!!", Toast.LENGTH_LONG).show();
             }
         }
         catch (Exception e) {
             Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
         }
     }
     public ArrayList<ModelClass> getAllImagesData(){
        try{
            SQLiteDatabase objSQLiteDatabase = this.getReadableDatabase();
            ArrayList<ModelClass> objModelClassList = new ArrayList<>();
            Cursor c = objSQLiteDatabase.rawQuery("select * from imageInfo",null);
            if(c.getCount()!=0)
            {
                while(c.moveToNext())
                {
                    String imageName=c.getString(0);
                    byte[] imageBytes = c.getBlob(1);
                    Bitmap objBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                    objModelClassList.add(new ModelClass(imageName,objBitmap));
                }
                return objModelClassList;
            }
            else{
                Toast.makeText(context, "Database is Empty!!", Toast.LENGTH_LONG).show();
                return null;
            }

        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
