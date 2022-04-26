package es.umh.dadm;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.umh.dadm.adapter.CategoryAdapter;
import es.umh.dadm.storage.InternalStorage;

public class CategoriesActivity extends AppCompatActivity {

    private final Context context = CategoriesActivity.this;
    private InternalStorage internalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        loadInternalData();

        setCategoryAdapter();
    }

    private void loadInternalData() {
        internalStorage = new InternalStorage(context);
    }

    private void setCategoryAdapter() {
        RecyclerView recyclerView = findViewById(R.id.category_recycleview);
        CategoryAdapter categoryAdapter = new CategoryAdapter(context, internalStorage.getDataAsArrayList());
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
