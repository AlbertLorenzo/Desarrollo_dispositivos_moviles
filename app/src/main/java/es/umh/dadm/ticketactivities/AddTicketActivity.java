package es.umh.dadm.ticketactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import es.umh.dadm.R;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.storage.InternalStorage;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;
import es.umh.dadm.validator.TextValidator;

public class AddTicketActivity extends AppCompatActivity {

    private EditText input_image, input_price, input_date, input_shortDesc, input_longDesc, input_location;
    private Spinner input_spinner_category;
    private Button btn_add_ticket;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        checkCategories();

        setViews();

        setSpinner();

        setButtonListener();

        validate();
    }

    private void checkCategories() {
        if (!(CategoryWrapper.getInstance().getCategoryArrayList().size() > 0)) {
            Toast.makeText(context, "Antes de añadir un ticket debes crear una categoría.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void validate() {
        input_price.addTextChangedListener(new TextValidator(input_price) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.trim().isEmpty()) {
                    textView.setError("Este campo no puede estar vacío.");
                }
            }
        });
    }

    private void setSpinner() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CategoryWrapper.getInstance().getLabels());
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_spinner_category.setAdapter(adp);
    }

    private void setViews() {
        input_image = findViewById(R.id.input_image);
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

        ticket.setImage(input_image.getText().toString());
        ticket.setCategory(Integer.parseInt(input_spinner_category.getSelectedItem().toString()));
        ticket.setPrice(Double.parseDouble(input_price.getText().toString()));
        ticket.setLocation(input_location.getText().toString());
        ticket.setDate(input_date.getText().toString());
        ticket.setShortDesc(input_shortDesc.getText().toString());
        ticket.setLongDesc(input_longDesc.getText().toString());

        dbHelper.insertTicket(ticket);
    }

    private void setButtonListener() {
        btn_add_ticket.setOnClickListener(view -> {
                    insertTicket();
                }
        );
    }
}