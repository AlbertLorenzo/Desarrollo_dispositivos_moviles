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

import java.io.IOException;
import java.util.Date;

import es.umh.dadm.R;
import es.umh.dadm.category.Category;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.validator.TextValidator;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText input_id, input_short_desc, input_long_desc, input_details;
    private ImageView img_view_category_image;
    private Button btn_add_category, btn_add_category_camera_image, btn_add_category_gallery_image;
    private ActivityResultLauncher<String> imgUri;
    private ActivityResultLauncher<Intent> imgCamera;
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

        imgCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Bundle bundle;
                    if (result.getData() != null) {
                        bundle = result.getData().getExtras();
                        bitmap = (Bitmap) bundle.get("data");
                        img_view_category_image.setImageBitmap(bitmap);
                    }
                }
        );
    }

    private void setViews() {
        btn_add_category = findViewById(R.id.btn_add_category);
        btn_add_category_gallery_image = findViewById(R.id.btn_add_category_gallery_image);
        btn_add_category_camera_image = findViewById(R.id.btn_add_category_camera_image);
        input_id = findViewById(R.id.input_cat_id);
        input_short_desc = findViewById(R.id.input_cat_short_desc);
        input_long_desc = findViewById(R.id.input_cat_long_desc);
        input_details = findViewById(R.id.input_cat_details);
        img_view_category_image = findViewById(R.id.img_view_category_image);
    }

    private Category getNewCategory() {
        int timeStamp = (int) (new Date().getTime() / 1000);
        Category category = new Category(
                input_id.getText().toString(),
                input_short_desc.getText().toString(),
                input_long_desc.getText().toString(),
                input_details.getText().toString(),
                input_id.getText().toString() + timeStamp
        );
        return category;
    }

    private void validate() {
        EditText[] et = {input_id, input_details, input_long_desc, input_short_desc};
        boolean check = TextValidator.validate(et);
        if (check) {
            Category category = getNewCategory();
            CategoryWrapper.getInstance().insertCategory(category);
            ExternalStorage.BitmapToSDCard(bitmap, category.getImageName());
            Toast.makeText(context, "Categoría añadida.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgCamera.launch(intent);
    }

    private void openGallery() {
        imgUri.launch("image/*");
    }

    private void setButtonListeners() {
        btn_add_category_camera_image.setOnClickListener(view -> {
            openCamera();
        });

        btn_add_category_gallery_image.setOnClickListener(view -> {
            openGallery();
        });

        btn_add_category.setOnClickListener(view -> {
            validate();
        });
    }

}
