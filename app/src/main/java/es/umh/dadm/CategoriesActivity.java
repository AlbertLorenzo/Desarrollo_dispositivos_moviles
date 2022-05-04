package es.umh.dadm;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.umh.dadm.adapters.CategoryAdapter;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.storage.InternalStorage;

public class CategoriesActivity extends AppCompatActivity {

    private final Context context = CategoriesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        setCategoryAdapter();
    }

    private void setCategoryAdapter() {
        RecyclerView recyclerView = findViewById(R.id.category_recycleview);
        CategoryAdapter categoryAdapter = new CategoryAdapter(context);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
