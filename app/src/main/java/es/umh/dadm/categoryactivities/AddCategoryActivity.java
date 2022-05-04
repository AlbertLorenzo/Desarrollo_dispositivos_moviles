package es.umh.dadm.categoryactivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import es.umh.dadm.R;
import es.umh.dadm.category.Category;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.storage.InternalStorage;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText input_id, input_short_desc, input_long_desc, input_details;
    private ImageView img_view_category_image;
    private Button btn_add_category, btn_add_category_image;
    private ActivityResultLauncher<String> imgUri;
    private final Context context = this;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        setViews();

        activityLauncher();

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

    private void setViews() {
        btn_add_category = findViewById(R.id.btn_add_category);
        btn_add_category_image = findViewById(R.id.btn_add_category_image);
        input_id = findViewById(R.id.input_cat_id);
        input_short_desc = findViewById(R.id.input_cat_short_desc);
        input_long_desc = findViewById(R.id.input_cat_long_desc);
        input_details = findViewById(R.id.input_cat_details);
        img_view_category_image = findViewById(R.id.img_view_category_image);
    }

    private Category getNewObject() {
        int timeStamp = (int) (new Date().getTime() / 1000);
        Category category = new Category(
                input_id.getText().toString(),
                input_short_desc.getText().toString(),
                input_long_desc.getText().toString(),
                input_details.getText().toString(),
                input_id.getText().toString() + input_short_desc.getText().toString() + timeStamp
        );
        return category;
    }

    private void setButtonListeners() {
        btn_add_category_image.setOnClickListener(view -> {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
            imgUri.launch("image/*");
        });

        btn_add_category.setOnClickListener(view -> {
            Category category = getNewObject();
            CategoryWrapper.getInstance().insertCategory(category);
            ExternalStorage.BitmapToSDCard(bitmap, context, category.getImageName() + ".jpg");
            Toast.makeText(context, "Categoría añadida.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}
