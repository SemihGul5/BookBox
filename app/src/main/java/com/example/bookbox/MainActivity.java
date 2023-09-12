package com.example.bookbox;

import static com.example.bookbox.DbHelper.TABLENAME;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    SQLiteDatabase database;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        setTitle("Kitaplarım");
        binding.recyclerView.setHasFixedSize(true);


        dbHelper = new DbHelper(this);
        //Adapter bağlama işlemi
        bookArrayList= new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        bookAdapter= new BookAdapter(bookArrayList,this,R.layout.recycler_list,database);

        binding.recyclerView.setAdapter(bookAdapter);
        getData();

        //floating action button (fab)
        fab();

    }

    private void fab() {
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

            //bütün veritabanını dolaşacak ve listeye kaydedecek

            database=dbHelper.getReadableDatabase();
            Cursor cursor= database.rawQuery("SELECT * FROM "+TABLENAME+"",null);

            int idix=cursor.getColumnIndex("id");
            int nameix=cursor.getColumnIndex("name");
            int descriptionix=cursor.getColumnIndex("description");
            int imageix=cursor.getColumnIndex("image");
            int categoryix= cursor.getColumnIndex("category");
            int dateix= cursor.getColumnIndex("date");
            int publisherix= cursor.getColumnIndex("publisher");
            int writerix= cursor.getColumnIndex("writer");

            while (cursor.moveToNext()){
                int id=cursor.getInt(idix);
                String name=cursor.getString(nameix);
                String description=cursor.getString(descriptionix);
                byte[] bytes= cursor.getBlob(imageix);
                String writer= cursor.getString(writerix);
                String category= cursor.getString(categoryix);
                String date=cursor.getString(dateix);
                String publisher=cursor.getString(publisherix);

                Book book= new Book(id,bytes,name,writer,category,date,publisher,description);
                bookArrayList.add(book);

            }

            cursor.close();
            bookAdapter.notifyDataSetChanged();
    }

}