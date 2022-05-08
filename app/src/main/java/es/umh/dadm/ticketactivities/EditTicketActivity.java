package es.umh.dadm.ticketactivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
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
import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;

public class EditTicketActivity extends AppCompatActivity {

    private EditText input_category, input_price, input_date, input_shortDesc, input_longDesc, input_location;
    private ImageView img_view_edit_ticket_image;
    private Button btn_edit_ticket_image_camera, btn_edit_ticket, btn_edit_ticket_image_gallery;
    private final Context context = EditTicketActivity.this;
    private ActivityResultLauncher<String> imgUri;
    private ActivityResultLauncher<Intent> imgCamera;
    private Ticket ticket;
    private SqliteHelper dbHelper;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);
        setViews();
        setTicketData();
        setDataToViews();
        activityLauncher();
        setButtonListener();
    }

    private void activityLauncher() {
        imgUri = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                        img_view_edit_ticket_image.setImageURI(result);
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
                        img_view_edit_ticket_image.setImageBitmap(bitmap);
                    }
                }
        );
    }

    private void setTicketData() {
        ticket = (Ticket) getIntent().getSerializableExtra("TICKET_KEY");
    }

    private void setViews() {
        btn_edit_ticket = findViewById(R.id.btn_edit_ticket);
        btn_edit_ticket_image_camera = findViewById(R.id.btn_edit_ticket_image_camera);
        btn_edit_ticket_image_gallery = findViewById(R.id.btn_edit_ticket_image_gallery);

        img_view_edit_ticket_image = findViewById(R.id.img_view_edit_ticket_image);

        input_category = findViewById(R.id.input_edit_category);
        input_price = findViewById(R.id.input_edit_price);
        input_date = findViewById(R.id.input_edit_date);
        input_shortDesc = findViewById(R.id.input_edit_short_desc);
        input_longDesc = findViewById(R.id.input_edit_long_desc);
        input_location = findViewById(R.id.input_edit_location);
    }

    private void setDataToViews() {
        img_view_edit_ticket_image.setImageBitmap(ExternalStorage.loadBitmapFromSDCard(ticket.getImage()));
        input_category.setText(String.valueOf(ticket.getCategory()));
        input_price.setText(String.valueOf(ticket.getPrice()));
        input_date.setText(ticket.getDate());
        input_shortDesc.setText(ticket.getShortDesc());
        input_longDesc.setText(ticket.getLongDesc());
        input_location.setText(ticket.getLocation());
    }

    /**
     *
     * Se crea un nuevo objeto de tipo ticket
     * si el objeto Bitmap no es nulo significa que el usuario ha capturado una nueva imagen
     * por lo tanto, se borra la anterior y se actualiza con la nueva.
     *
     * @return devuelve un nuevo ticket
     */
    private Ticket getNewTicket() {
        int timeStamp = (int) (new Date().getTime() / 1000);
        String newImageName;
        Ticket newTicket = new Ticket();

        newTicket.setId(ticket.getId());
        newTicket.setCategory(Integer.parseInt(String.valueOf(input_category.getText())));
        newTicket.setPrice(Double.parseDouble(String.valueOf(input_price.getText())));
        newTicket.setDate(String.valueOf(input_date.getText()));
        newTicket.setShortDesc(String.valueOf(input_shortDesc.getText()));
        newTicket.setLongDesc(String.valueOf(input_longDesc.getText()));
        newTicket.setLocation(String.valueOf(input_location.getText()));

        if (bitmap != null) {
            newImageName = newTicket.getShortDesc() + timeStamp;
            ExternalStorage.deleteBitmapFromSDCard(ticket.getImage());
        } else {
            newImageName = ticket.getImage();
        }

        newTicket.setImage(newImageName);
        return newTicket;
    }

    private void setButtonListener() {
        btn_edit_ticket.setOnClickListener(view -> {
            Ticket newTicket = getNewTicket();
            dbHelper = new SqliteHelper(context);
            dbHelper.updateTicket(newTicket);
            if (bitmap != null) {
                ExternalStorage.BitmapToSDCard(bitmap, newTicket.getImage());
            }
            Toast.makeText(context, "Ticket actualizado correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        });

        btn_edit_ticket_image_camera.setOnClickListener(view -> {
            Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imgCamera.launch(cInt);
        });

        btn_edit_ticket_image_gallery.setOnClickListener(view -> {
            imgUri.launch("image/*");
        });
    }
}