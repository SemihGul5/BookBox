package com.example.bookbox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bookbox.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<Book> bookArrayList;
    BookAdapter bookAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        setTitle("Kitaplarım");
        binding.recyclerView.setHasFixedSize(true);

        //Adapter bağlama işlemi
        bookArrayList= new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter=new BookAdapter(bookArrayList);
        binding.recyclerView.setAdapter(bookAdapter);





        getData();



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, BookAdd.class);
                startActivity(intent);

            }
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        try {
            //bütün veritabanını dolaşacak ve listeye kaydedecek
            SQLiteDatabase database= openOrCreateDatabase("Book",MODE_PRIVATE,null);
            Cursor cursor= database.rawQuery("SELECT * FROM book",null);

            int idix=cursor.getColumnIndex("id");
            int nameix=cursor.getColumnIndex("name");
            int descriptionix=cursor.getColumnIndex("description");
            int imageix=cursor.getColumnIndex("image");

            while (cursor.moveToNext()){
                int id=cursor.getInt(idix);
                String name=cursor.getString(nameix);
                String description=cursor.getString(descriptionix);

                byte[] bytes= cursor.getBlob(imageix);



                Book book= new Book(id,0,name,description,bytes);

                bookArrayList.add(book);


            }
            bookAdapter.notifyDataSetChanged();
            cursor.close();


        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this,"Bir hata oluştu!!!!",Toast.LENGTH_SHORT).show();
        }


    }

}