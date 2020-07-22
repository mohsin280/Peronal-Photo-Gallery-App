package com.mi.saveimage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    ArrayList<ModelClass> objectModelClassList;

    public RvAdapter(ArrayList<ModelClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelClass objectModelClass = objectModelClassList.get(position);
        holder.tv_imageDetails.setText(objectModelClass.getImageName());
        holder.iv_singleImage.setImageBitmap(objectModelClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_imageDetails ;
        private ImageView iv_singleImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_imageDetails=itemView.findViewById(R.id.tv_imageDetails);
            iv_singleImage=itemView.findViewById(R.id.iv_singleImage);
        }
    }
}
