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

import java.util.ArrayList;
import java.util.Objects;

import es.umh.dadm.adapter.Adapter;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;
import es.umh.dadm.ticket.TicketWrapper;

public class MainActivity extends AppCompatActivity {

    private final Context context = MainActivity.this;
    private ArrayList<Ticket> ticketsList;
    private SqliteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideWindowTitle();

        setContentView(R.layout.activity_main);

        gatherTicketsData();

        setMainViewAdapter();

        setButtonListeners();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void setButtonListeners() {
        Button btn_open_add_category_activity;
        FloatingActionButton btn_open_add_ticket_activity;

        btn_open_add_category_activity =  findViewById(R.id.btn_open_add_category_activity);
        btn_open_add_ticket_activity = findViewById(R.id.btn_open_add_ticket_activity);

        btn_open_add_ticket_activity.setOnClickListener((View) -> {
            Intent intent = new Intent(context, AddTicketActivity.class);
            startActivity(intent);
        });

        btn_open_add_category_activity.setOnClickListener((View) -> {
            Intent intent = new Intent(context, AddCategoryActivity.class);
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
            TicketWrapper wrapper = new TicketWrapper(cursor);
            wrapper.iterateCursor();
            this.ticketsList = wrapper.getTicketsList();
            wrapper.closeCursor();
        }
    }

    private void setMainViewAdapter() {
        RecyclerView recyclerView = findViewById(R.id.main_recycleview);
        Adapter adapter = new Adapter(context, this.ticketsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}