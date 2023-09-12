package com.example.bookbox;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookbox.databinding.RecyclerListBinding;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    ArrayList<Book> bookArrayList;
    Context context;
    int singleData;
    SQLiteDatabase sqLiteDatabase;

    public BookAdapter(ArrayList<Book> bookArrayList, Context context, int singleData, SQLiteDatabase sqLiteDatabase) {
        this.bookArrayList = bookArrayList;
        this.context = context;
        this.singleData = singleData;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerListBinding binding= RecyclerListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new BookHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        final Book book= bookArrayList.get(position);
        byte[] bytes=book.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.binding.recyclerImage.setImageBitmap(bitmap);
        holder.binding.recyclerDescText.setText(book.getDescrp());
        holder.binding.recyclerTitleBookText.setText(book.getName());



        holder.binding.recyclerFlowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3 noktalı menüyü bağlama işlemi
                PopupMenu popupMenu= new PopupMenu(context,holder.binding.recyclerFlowMenu);
                popupMenu.inflate(R.menu.flow_menu);


                //3 noktalı menü elemanlarına tıklanınca ne olacağının yazıldığı bölüm
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.flowDelete){

                            // silme işlemi yapılacak

                        } else if (menuItem.getItemId()==R.id.flowEdit) {

                            Intent intent = new Intent(context, EditActivity.class);
                            // edit ekranına gidilecek
                        }
                        return false;
                    }
                });
                popupMenu.show();


            }
        });


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
