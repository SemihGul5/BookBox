package com.example.bookbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookbox.databinding.RecyclerListBinding;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    ArrayList<Book> bookArrayList;

    public BookAdapter(ArrayList<Book> bookArrayList) {
        this.bookArrayList = bookArrayList;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerListBinding binding= RecyclerListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new BookHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        holder.binding.recyclerBookTitleText.setText(bookArrayList.get(position).getName());
        holder.binding.recyclerDescTex.setText(bookArrayList.get(position).getDescrp());

        byte[] bytes= bookArrayList.get(position).getBytes();
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.binding.recyclerImage.setImageBitmap(bitmap);
       // bookArrayList.get(position).getImage();
        //image ekle
        //holder.binding.recyclerImage.setImageBitmap(bookArrayList.get(position).getImage());



    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder{
        RecyclerListBinding binding;

        public BookHolder(RecyclerListBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
