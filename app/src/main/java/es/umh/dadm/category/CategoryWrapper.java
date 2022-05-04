package es.umh.dadm.category;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import es.umh.dadm.appcontext.App;
import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.storage.InternalStorage;

public class CategoryWrapper {
    private static CategoryWrapper instance;
    private final ArrayList<Category> categoryArrayList;
    private final InternalStorage internalStorage;
    private final Context context;
    private final Gson gson;

    private CategoryWrapper() {
        gson = new Gson();
        context = App.getContext();
        internalStorage = new InternalStorage();
        categoryArrayList = internalStorage.fileDataAsArrayList();
    }

    public static CategoryWrapper getInstance() {
        if (instance == null) {
            instance = new CategoryWrapper();
        }
        return instance;
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public String dataAsString() {
        StringBuilder sb = new StringBuilder();

        for (Category item : categoryArrayList) {
            sb.append(gson.toJson(item)).append("\n");
        }

        return sb.toString();
    }

    public void updateCategory(Category newCategory) {
        for (Category item : this.categoryArrayList) {
            if (item.getId().equals(newCategory.getId())) {
                item.setLongDesc(newCategory.getShortDesc());
                item.setShortDesc(newCategory.getLongDesc());
                item.setDetails(newCategory.getDetails());
                if (!item.getImageName().equals(newCategory.getImageName())) {
                    ExternalStorage.deleteBitmapFromSDCard(item.getImageName(), context);
                    item.setImageName(newCategory.getImageName());
                }
            }
        }
        internalStorage.writeNewData(dataAsString());
    }

    public void removeCategory(Category deletedCategory) {
        for (int i = 0; i < categoryArrayList.size(); i++) {
            if (categoryArrayList.get(i).getId().equals(deletedCategory.getId())) {
                ExternalStorage.deleteBitmapFromSDCard(categoryArrayList.get(i).getImageName(), context);
                categoryArrayList.remove(i);
                break;
            }
        }
        internalStorage.writeNewData(dataAsString());
    }

    public void insertCategory(Category category) {
        this.categoryArrayList.add(category);
        internalStorage.appendLine(category.stringify());
    }

    public String[] getLabels() {
        String[] labels = new String[this.categoryArrayList.size()];
        int i = 0;
        for (Category item : this.categoryArrayList) {
            labels[i] = item.getId();
            i++;
        }
        return labels;
    }
}
