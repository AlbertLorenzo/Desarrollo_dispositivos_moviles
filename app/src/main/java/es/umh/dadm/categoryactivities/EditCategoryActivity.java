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

public class EditCategoryActivity extends AppCompatActivity {

    private EditText input_edit_category_sDesc, input_edit_category_details, input_edit_category_lDesc;
    private Button btn_edit_category_confirm, btn_edit_category_gallery, btn_edit_category_camera;
    private ImageView img_view_edit_category_image;
    private Category category;
    private final Context context = EditCategoryActivity.this;
    private ActivityResultLauncher<String> imgUri;
    private ActivityResultLauncher<Intent> imgCamera;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        setViews();

        setCategoryData();

        setDataToViews();

        activityLauncher();

        setButtonListeners();
    }

    private void setViews() {
        input_edit_category_details = findViewById(R.id.input_edit_category_details);
        input_edit_category_sDesc = findViewById(R.id.input_edit_category_sDesc);
        input_edit_category_lDesc = findViewById(R.id.input_edit_category_lDesc);
        img_view_edit_category_image = findViewById(R.id.img_view_edit_category_image);
        btn_edit_category_gallery = findViewById(R.id.btn_edit_category_gallery);
        btn_edit_category_camera = findViewById(R.id.btn_edit_category_camera);
        btn_edit_category_confirm = findViewById(R.id.btn_edit_category_confirm);
    }

    private void setCategoryData() {
        category = (Category) getIntent().getSerializableExtra("CATEGORY_KEY");
    }

    private void setDataToViews() {
        input_edit_category_details.setText(category.getDetails());
        input_edit_category_sDesc.setText(category.getShortDesc());
        input_edit_category_lDesc.setText(category.getLongDesc());
        img_view_edit_category_image.setImageBitmap(ExternalStorage.loadBitmapFromSDCard(category.getImageName(), context));
    }

    private void activityLauncher() {
        imgUri = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    img_view_edit_category_image.setImageURI(result);
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
                        img_view_edit_category_image.setImageBitmap(bitmap);
                    }
                }
        );
    }

    private Category getNewCategory() {
        int timeStamp = (int) (new Date().getTime() / 1000);
        String newImageName;

        if (bitmap != null) {
            newImageName = category.getId() + input_edit_category_sDesc.getText().toString() + timeStamp;
            ExternalStorage.BitmapToSDCard(bitmap, context, newImageName + ".jpg");
        } else {
            newImageName = category.getImageName();
        }

        return new Category(
                category.getId(),
                input_edit_category_sDesc.getText().toString(),
                input_edit_category_lDesc.getText().toString(),
                input_edit_category_details.getText().toString(),
                newImageName
        );
    }

    private void setButtonListeners() {
        btn_edit_category_confirm.setOnClickListener(view -> {
            Category newCategory = getNewCategory();
            CategoryWrapper.getInstance().updateCategory(newCategory);
            Toast.makeText(context, "Categoría actualizada.", Toast.LENGTH_SHORT).show();
        });

        btn_edit_category_gallery.setOnClickListener(view -> {
            imgUri.launch("image/*");
        });

        btn_edit_category_camera.setOnClickListener(view -> {
            Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imgCamera.launch(cInt);
        });
    }
}
