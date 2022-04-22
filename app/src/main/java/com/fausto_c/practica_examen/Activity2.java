package com.fausto_c.practica_examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    ImageView imagen;
    Button btntomarFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        imagen = findViewById(R.id.iVFoto);
        btntomarFoto = findViewById(R.id.btTakePicture);
        btntomarFoto.setOnClickListener(this);
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.resolveActivity(getPackageManager());
        startActivityForResult(intent,1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btTakePicture:
                takePicture();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap foto = (Bitmap) bundle.get("data");

            imagen.setImageBitmap(foto);
        }
    }
}