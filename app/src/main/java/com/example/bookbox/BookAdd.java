package com.example.bookbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.bookbox.databinding.ActivityBookAddBinding;
import com.example.bookbox.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class BookAdd extends AppCompatActivity {
    private ActivityBookAddBinding binding;
    ArrayList<String> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityBookAddBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        category= new ArrayList<>();
        category.add("Roman");
        category.add("Bilim Kurgu");
        category.add("Korku");
        category.add("Macera");
        category.add("Gizem");
        category.add("Bilim ve Teknoloji");
        category.add("Tarih");
        category.add("Biyografi");
        category.add("Çocuk ve Gençlik");
        category.add("Felsefe");
        category.add("Psikoloji");
        category.add("İş ve Ekonomi");
        category.add("Sağlık ve Fitness");
        category.add("Sanat ve Edebiyat");
        category.add("Seyahat");
        category.add("Spor");
        category.add("Yiyecek ve İçecek");
        category.add("Müzik");
        category.add("Din ve Mitoloji");
        category.add("Hobi ve El Sanatları");

        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,category);
        binding.spinner.setAdapter(stringArrayAdapter);
    }
}