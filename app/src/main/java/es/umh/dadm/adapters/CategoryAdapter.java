package es.umh.dadm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import es.umh.dadm.categoryactivities.EditCategoryActivity;
import es.umh.dadm.R;
import es.umh.dadm.category.Category;
import es.umh.dadm.category.CategoryWrapper;
import es.umh.dadm.storage.ExternalStorage;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    private final Context context;
    private CategoryWrapper catWrapper;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_row, parent, false);
        catWrapper = CategoryWrapper.getInstance();
        return new CategoryAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Holder holder, int position) {
        Category category = catWrapper.getCategoryArrayList().get(position);

        holder.category_id.setText(category.getId());
        holder.category_sDesc.setText(category.getShortDesc());
        holder.category_image.setImageBitmap(ExternalStorage.loadBitmapFromSDCard(category.getImageName()));

        holder.itemView.setOnLongClickListener((view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("¿Desea realmente borrar esta categoría?")
                    .setPositiveButton("Sí", (dialogInterface, i) -> {
                        deleteCategory(position);
                        dialogInterface.cancel();
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
            builder.show();
            return true;
        }));

        holder.itemView.setOnClickListener(view -> {
            updateCategory(category, position);
        });
    }

    private void updateCategory(Category category, int position) {
        Intent intent = new Intent(context, EditCategoryActivity.class);
        intent.putExtra("CATEGORY_KEY", category);
        intent.putExtra("CATEGORY_POSITION", position);
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteCategory(int position) {
        catWrapper.removeCategory(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return CategoryWrapper.getInstance().getCategoryArrayList().size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        protected ImageView category_image;
        protected TextView category_id, category_sDesc;

        public Holder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_id = itemView.findViewById(R.id.category_id);
            category_sDesc = itemView.findViewById(R.id.category_sDesc);
        }
    }
}
