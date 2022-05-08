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

    /**
     * La lectura de las categorías sólo se hará una única vez
     */
    private CategoryWrapper() {
        gson = new Gson();
        context = App.getContext();
        internalStorage = new InternalStorage();
        categoryArrayList = internalStorage.fileDataAsArrayList();
    }

    /**
     * Esta clase sigue un patrón singleton para manejar los datos de las categorías
     * así siempre se accede a la misma instancia y sólo se lee el fichero al recoger la instancia por primera vez
     * @return devuelve la instancia
     */
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

    public void updateCategory(Category newCategory, int position) {
        Category item = this.categoryArrayList.get(position);
        item.setLongDesc(newCategory.getLongDesc());
        item.setShortDesc(newCategory.getShortDesc());
        item.setDetails(newCategory.getDetails());
        if (!item.getImageName().equals(newCategory.getImageName())) {
            ExternalStorage.deleteBitmapFromSDCard(item.getImageName());
            item.setImageName(newCategory.getImageName());
        }
        internalStorage.writeNewData(dataAsString());
    }

    public void removeCategory(int position) {
        ExternalStorage.deleteBitmapFromSDCard(categoryArrayList.get(position).getImageName());
        categoryArrayList.remove(position);
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
