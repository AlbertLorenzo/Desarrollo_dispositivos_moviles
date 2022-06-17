package es.umh.dadm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.umh.dadm.adapters.TicketAdapter;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.categoryactivities.AddCategoryActivity;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.TicketWrapper;
import es.umh.dadm.ticketactivities.AddTicketActivity;


public class MainActivity extends AppCompatActivity {

    private final Context context = MainActivity.this;
    private FloatingActionButton btn_open_add_ticket_activity;
    private TicketWrapper ticketWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setButtonListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Cada vez que la actividad entra en pausa y se modifica/elimina un ticket
     * volverá a esta actividad y se actualizarán los datos en la DB y en el adaptador del RecyclerView
     */
    @Override
    protected void onResume() {
        gatherTicketsData();
        setMainViewAdapter();
        showEmpty();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            openCategoriesActivity();
        } else if (item.getItemId() == R.id.main_menu_add_category) {
            openAddCategoryActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCategoriesActivity() {
        Intent intent = new Intent(context, CategoriesActivity.class);
        startActivity(intent);
    }

    private void openAddCategoryActivity() {
        Intent intent = new Intent(context, AddCategoryActivity.class);
        startActivity(intent);
    }

    private void setViews() {
        btn_open_add_ticket_activity = findViewById(R.id.btn_open_add_ticket_activity);
    }

    private void setButtonListeners() {
        btn_open_add_ticket_activity.setOnClickListener((View) -> {
            Intent intent = new Intent(context, AddTicketActivity.class);
            startActivity(intent);
        });
    }

    public void gatherTicketsData() {
        SqliteHelper dbHelper = new SqliteHelper(context);
        Cursor cursor = dbHelper.getTicketsData();
        ticketWrapper = new TicketWrapper(cursor);
    }

    private void showEmpty() {
        if (this.ticketWrapper.getTicketsList().size() == 0) {
            Toast.makeText(context, "Actualmente no existe ningún ticket.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setMainViewAdapter() {
        RecyclerView recyclerView = findViewById(R.id.main_recycleview);
        TicketAdapter ticketAdapter = new TicketAdapter(context, ticketWrapper.getTicketsList());
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}