package es.umh.dadm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;
import es.umh.dadm.validator.TextValidator;

public class AddTicketActivity extends AppCompatActivity {

    private EditText input_image, input_category, input_price, input_date, input_shortDesc, input_longDesc, input_location;
    private Button btn_add_ticket;
    private final Context context = this;
    private SqliteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        setViews();

        setButtonListener();

        /*
        input_category.addTextChangedListener(new TextValidator(input_image) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.trim().isEmpty()) {
                    textView.setError("vasio miarma");
                }
            }
        });
        */
    }

    private void setViews() {
        input_image = findViewById(R.id.input_image);
        input_category = findViewById(R.id.input_category);
        input_price = findViewById(R.id.input_price);
        input_date = findViewById(R.id.input_date);
        input_shortDesc = findViewById(R.id.input_short_desc);
        input_longDesc = findViewById(R.id.input_long_desc);
        input_location = findViewById(R.id.input_location);
        btn_add_ticket = findViewById(R.id.btn_add_ticket);
    }

    private void insertTicket() {
        dbHelper = new SqliteHelper(context);

        Ticket ticket = new Ticket();

        ticket.setImage(input_image.getText().toString());
        ticket.setCategory(Integer.parseInt(input_category.getText().toString()));
        ticket.setPrice(Double.parseDouble(input_price.getText().toString()));
        ticket.setLocation(input_location.getText().toString());
        ticket.setDate(input_date.getText().toString());
        ticket.setShortDesc(input_shortDesc.getText().toString());
        ticket.setLongDesc(input_longDesc.getText().toString());

        dbHelper.insertTicket(ticket);
    }

    private void setButtonListener() {
        btn_add_ticket.setOnClickListener(view -> insertTicket());
    }
}