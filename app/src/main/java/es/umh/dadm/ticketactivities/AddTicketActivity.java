package es.umh.dadm.ticketactivities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Date;

import es.umh.dadm.R;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.storage.InternalStorage;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;
import es.umh.dadm.validator.TextValidator;

public class AddTicketActivity extends AppCompatActivity {

    private EditText input_price, input_date, input_shortDesc, input_longDesc, input_location;
    private Spinner input_spinner_category;
    private Button btn_add_ticket, btn_add_ticket_image_from_camera, btn_add_ticket_image_from_gallery;
    private ActivityResultLauncher<String> imgUri;
    private ActivityResultLauncher<Intent> imgCamera;
    private Bitmap bitmap;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        checkCategories();
        setViews();
        setSpinner();
        activityLauncher();
        setButtonListener();
    }

    private void activityLauncher() {
        imgUri = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
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
                    }
                }
        );
    }

    private void checkCategories() {
        if (!(CategoryWrapper.getInstance().getCategoryArrayList().size() > 0)) {
            Toast.makeText(context, "Antes de añadir un ticket debes crear una categoría.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setSpinner() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CategoryWrapper.getInstance().getLabels());
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_spinner_category.setAdapter(adp);
    }

    private void setViews() {
        btn_add_ticket_image_from_camera = findViewById(R.id.btn_add_ticket_image_from_camera);
        btn_add_ticket_image_from_gallery = findViewById(R.id.btn_add_ticket_image_from_gallery);
        input_spinner_category = findViewById(R.id.input_spinner_category);
        input_price = findViewById(R.id.input_price);
        input_date = findViewById(R.id.input_date);
        input_shortDesc = findViewById(R.id.input_short_desc);
        input_longDesc = findViewById(R.id.input_long_desc);
        input_location = findViewById(R.id.input_location);
        btn_add_ticket = findViewById(R.id.btn_add_ticket);
    }

    private void insertTicket() {
        SqliteHelper dbHelper = new SqliteHelper(context);
        Ticket ticket = new Ticket();
        int timeStamp = (int) (new Date().getTime() / 1000);

        ticket.setImage(input_shortDesc.getText().toString() + timeStamp);
        ticket.setCategory(Integer.parseInt(input_spinner_category.getSelectedItem().toString()));
        ticket.setPrice(Double.parseDouble(input_price.getText().toString()));
        ticket.setLocation(input_location.getText().toString());
        ticket.setDate(input_date.getText().toString());
        ticket.setShortDesc(input_shortDesc.getText().toString());
        ticket.setLongDesc(input_longDesc.getText().toString());

        ExternalStorage.BitmapToSDCard(bitmap, ticket.getImage());
        dbHelper.insertTicket(ticket);
    }

    private void validate() {
        EditText[] et = {input_shortDesc, input_longDesc, input_location, input_price, input_price};
        boolean empty = TextValidator.checkDate(input_date);
        boolean date = TextValidator.validate(et);
        if (empty && date) {
            insertTicket();
            finish();
        }
    }

    private void setButtonListener() {
        btn_add_ticket.setOnClickListener(view -> {
                    validate();
                }
        );

        btn_add_ticket_image_from_camera.setOnClickListener(view -> {
            Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imgCamera.launch(cInt);
        });

        btn_add_ticket_image_from_gallery.setOnClickListener(view -> {
            imgUri.launch("image/*");
        });
    }
}