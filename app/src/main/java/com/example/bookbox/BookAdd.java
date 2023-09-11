package com.example.bookbox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bookbox.databinding.ActivityBookAddBinding;
import com.example.bookbox.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BookAdd extends AppCompatActivity {
    private ActivityBookAddBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap selectedImage;
    SQLiteDatabase database;
    ArrayList<String> category;
    ArrayList<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityBookAddBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        registerLauncher();

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

        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,category);
        binding.spinner.setAdapter(stringArrayAdapter);
        bookList= new ArrayList<>();
    }


    public void registerLauncher(){
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK){
                    Intent intentFromResult=result.getData();//kullanıcının seçtiği resmi aldık
                    if(intentFromResult!=null){
                        Uri imageData= intentFromResult.getData();//kullanıcın seçtiği resim!!!!
                        //binding.imageView.setImageURI(imageData);

                        //bu resmi veritabanına kaydetmek için aşağıdakiler yapılır.!!!!!!!!!
                        try {
                            if(Build.VERSION.SDK_INT>=28){
                                ImageDecoder.Source source= ImageDecoder.createSource(BookAdd.this.getContentResolver(),imageData);
                                selectedImage=ImageDecoder.decodeBitmap(source);
                                binding.imageView2.setImageBitmap(selectedImage);

                            }
                            else{
                                selectedImage=MediaStore.Images.Media.getBitmap(BookAdd.this.getContentResolver(),imageData);
                                binding.imageView2.setImageBitmap(selectedImage);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(BookAdd.this,"HATA",Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            }
        });



        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent intentToGallery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }
                else{
                    Toast.makeText(BookAdd.this,"İzin verilmedi",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void save(View view){

        String name= binding.bookNameText.getText().toString();
        String writer= binding.writerNameText.getText().toString();
        String category= binding.spinner.getSelectedItem().toString();
        String date= binding.editTextDate2.getText().toString();
        String publisher=binding.publisherText.getText().toString();
        String description= binding.descriptionText.getText().toString();

        Bitmap bitmap= makeSmallerImage(selectedImage,120);
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();



        try {
            database=openOrCreateDatabase("Book",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS book(id INTEGER PRIMARY KEY,name VARCHAR, writer VARCHAR, category VARCHAR, date VARCHAR, publisher VARCHAR, description VARCHAR, image BLOB)");
            String sql="INSERT INTO book(name,writer,category,date,publisher,description,image) VALUES (?,?,?,?,?,?,?)";
            SQLiteStatement sqLiteStatement= database.compileStatement(sql);
            sqLiteStatement.bindString(1,name);
            sqLiteStatement.bindString(2,writer);
            sqLiteStatement.bindString(3,category);
            sqLiteStatement.bindString(4,date);
            sqLiteStatement.bindString(5,publisher);
            sqLiteStatement.bindString(6,description);
            sqLiteStatement.bindBlob(7,bytes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(BookAdd.this,"Bir hata oluştu",Toast.LENGTH_SHORT).show();
        }
    }




    public Bitmap makeSmallerImage(Bitmap image, int maximumSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maximumSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maximumSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image,width,height,true);
    }

    public void selectImage(View view) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(BookAdd.this, android.Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                //izin gerekli
                //kullanıcıya açıklama göstermek zorunda mıyız kontrolü
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_MEDIA_IMAGES)){
                    //açıklama gerekli
                    Snackbar.make(view,"Galeriye gitmek için izin gereklidir.",Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //izin işlemleri
                            permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
                        }
                    }).show();
                }
                else{
                    //izin işlemleri
                    permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
                }

            }
            else{
                //izin daha önceden verilmiş, galeriye git
                Intent intentToGallery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
        else{
            if(ContextCompat.checkSelfPermission(BookAdd.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //izin gerekli
                //kullanıcıya açıklama göstermek zorunda mıyız kontrolü
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //açıklama gerekli
                    Snackbar.make(view,"Galeriye gitmek için izin gereklidir.",Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //izin işlemleri
                            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }).show();
                }
                else{
                    //izin işlemleri
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }
            else{
                //izin daha önceden verilmiş, galeriye git
                Intent intentToGallery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
    }
}