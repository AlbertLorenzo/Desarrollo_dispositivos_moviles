package es.umh.dadm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.umh.dadm.R;
import es.umh.dadm.category.Category;
import es.umh.dadm.storage.ExternalStorage;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    private final Context context;
    private ArrayList<Category> categoryList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new CategoryAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Holder holder, int position) {
        Category category = this.categoryList.get(position);

        holder.category_id.setText(String.valueOf(category.getId()));
        holder.category_image.setImageBitmap(ExternalStorage.loadBitmapFromSDCard(category.getImageName(), context));
    }

    @Override
    public int getItemCount() {
        return this.categoryList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        protected ImageView category_image;
        protected TextView category_id;

        public Holder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_id = itemView.findViewById(R.id.category_id);
        }
    }
}
