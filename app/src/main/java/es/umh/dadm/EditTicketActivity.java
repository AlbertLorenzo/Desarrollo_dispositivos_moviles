package es.umh.dadm;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;

public class EditTicketActivity extends AppCompatActivity {

    private EditText input_image, input_category, input_price, input_date, input_shortDesc, input_longDesc, input_location;
    private final Context context = EditTicketActivity.this;
    private Ticket ticket;
    private SqliteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);

        setViews();

        setTicketData();

        setDataToViews();

        setButtonListener();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void setTicketData() {
        Gson gson = new Gson();
        this.ticket = gson.fromJson(getIntent().getStringExtra("TicketGson"), (Type) Ticket.class);
    }

    private void setViews() {
        input_image = findViewById(R.id.input_edit_image);
        input_category = findViewById(R.id.input_edit_category);
        input_price = findViewById(R.id.input_edit_price);
        input_date = findViewById(R.id.input_edit_date);
        input_shortDesc = findViewById(R.id.input_edit_short_desc);
        input_longDesc = findViewById(R.id.input_edit_long_desc);
        input_location = findViewById(R.id.input_edit_location);
    }

    private void setDataToViews() {
        input_image.setText(ticket.getImage());
        input_category.setText(String.valueOf(ticket.getCategory()));
        input_price.setText(String.valueOf(ticket.getPrice()));
        input_date.setText(ticket.getDate());
        input_shortDesc.setText(ticket.getShortDesc());
        input_longDesc.setText(ticket.getLongDesc());
        input_location.setText(ticket.getLocation());
    }

    private void setButtonListener() {
        Button btn_edit_ticket = findViewById(R.id.btn_edit_ticket);

        btn_edit_ticket.setOnClickListener(view -> {
            updateTicket();
            finish();
        });
    }

    private void updateTicket() {
        dbHelper = new SqliteHelper(context);

        Ticket new_ticket = new Ticket(
                String.valueOf(input_image.getText()),
                Integer.parseInt(String.valueOf(input_category.getText())),
                Double.parseDouble(String.valueOf(input_price.getText())),
                String.valueOf(input_date.getText()),
                String.valueOf(input_shortDesc.getText()),
                String.valueOf(input_longDesc.getText()),
                String.valueOf(input_location.getText())
        );

        new_ticket.setId(ticket.getId());

        dbHelper.updateTicket(new_ticket);
    }

}