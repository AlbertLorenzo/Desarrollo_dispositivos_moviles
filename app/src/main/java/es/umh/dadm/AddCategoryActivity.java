package es.umh.dadm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.umh.dadm.category.Category;
import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.storage.InternalStorage;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText input_id, input_short_desc, input_long_desc, input_details;
    private ImageView img_view_category_image;
    private Button btn_add_category, btn_add_category_image;
    private ActivityResultLauncher<String> imgUri;
    private final Context context = this;
    private InternalStorage internalStorage;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        setViews();

        activityLauncher();

        loadInternalData();

        setButtonListeners();
    }

    private void activityLauncher() {
        imgUri = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    img_view_category_image.setImageURI(result);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void loadInternalData() {
        internalStorage = new InternalStorage(context);
    }

    private void setViews() {
        btn_add_category = findViewById(R.id.btn_add_category);
        btn_add_category_image = findViewById(R.id.btn_add_category_image);
        input_id = findViewById(R.id.input_cat_id);
        input_short_desc = findViewById(R.id.input_cat_short_desc);
        input_long_desc = findViewById(R.id.input_cat_long_desc);
        input_details = findViewById(R.id.input_cat_details);
        img_view_category_image = findViewById(R.id.img_view_category_image);
    }

    private String getSerializedData() {

        Category category = new Category(
                input_id.getText().toString(),
                input_short_desc.getText().toString(),
                input_long_desc.getText().toString(),
                input_details.getText().toString(),
                input_id.getText().toString() + input_short_desc.getText().toString());

        Gson gson = new Gson();
        return gson.toJson(category);
    }

    private void setButtonListeners() {
        btn_add_category_image.setOnClickListener(view -> {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
            imgUri.launch("image/*");
        });

        btn_add_category.setOnClickListener(view -> {
            String categorySerialized = getSerializedData();
            internalStorage.writeInternal(context, categorySerialized);
            String imageName = input_id.getText().toString() + input_short_desc.getText().toString();
            ExternalStorage.BitmapToSDCard(bitmap, context, imageName + ".jpg");
            Toast.makeText(context, "Categoría añadida.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}
