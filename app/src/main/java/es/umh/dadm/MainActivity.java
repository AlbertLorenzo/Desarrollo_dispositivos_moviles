package es.umh.dadm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import es.umh.dadm.adapter.TicketAdapter;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.TicketWrapper;

public class MainActivity extends AppCompatActivity {

    private final Context context = MainActivity.this;
    private SqliteHelper dbHelper;
    private Button btn_open_add_category_activity, btn_open_category_adapter;
    private FloatingActionButton btn_open_add_ticket_activity;
    private TicketWrapper ticketWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hideWindowTitle();

        setContentView(R.layout.activity_main);

        setViews();

        setButtonListeners();
    }

    @Override
    protected void onResume() {
        gatherTicketsData();
        setMainViewAdapter();
        super.onResume();
    }

    private void setViews() {
        btn_open_add_category_activity =  findViewById(R.id.btn_open_add_category_activity);
        btn_open_add_ticket_activity = findViewById(R.id.btn_open_add_ticket_activity);
        btn_open_category_adapter = findViewById(R.id.btn_open_category_adapter);
    }

    private void setButtonListeners() {
        btn_open_add_ticket_activity.setOnClickListener((View) -> {
            Intent intent = new Intent(context, AddTicketActivity.class);
            startActivity(intent);
        });

        btn_open_add_category_activity.setOnClickListener((View) -> {
            Intent intent = new Intent(context, AddCategoryActivity.class);
            startActivity(intent);
        });

        btn_open_category_adapter.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoriesActivity.class);
            startActivity(intent);
        });
    }

    private void hideWindowTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void gatherTicketsData() {
        dbHelper = new SqliteHelper(context);
        Cursor cursor = dbHelper.getTicketsData();
        if (cursor.getColumnCount() == 0) {
            Toast.makeText(this, "vasio miarma", Toast.LENGTH_SHORT).show();
        } else {
            ticketWrapper = new TicketWrapper(cursor);
        }
    }

    private void setMainViewAdapter() {
        RecyclerView recyclerView = findViewById(R.id.main_recycleview);
        TicketAdapter ticketAdapter = new TicketAdapter(context, ticketWrapper.getTicketsList());
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}