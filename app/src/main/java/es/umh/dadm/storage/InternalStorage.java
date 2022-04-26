package es.umh.dadm.storage;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

import es.umh.dadm.category.Category;

public class InternalStorage {
    private static final String FILE_NAME = "categorias.txt";
    private StringBuilder fileData = new StringBuilder();

    public InternalStorage(Context context) {
        if (checkFileExists(context)) {
            readinternal(context);
        }
    }

    private boolean checkFileExists(Context context) {
        File file = context.getFileStreamPath(FILE_NAME);
        return file.exists();
    }

    public void readinternal(Context context) {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(context.openFileInput(FILE_NAME)));
            String line;
            while ((line = fin.readLine()) != null) {
                fileData.append(line).append("\n");
            }
            fin.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }

    public void writeInternal(Context context, String data) {
        try {
            OutputStreamWriter fout = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE | Context.MODE_APPEND));
            this.fileData.append(data).append("\n");
            fout.append(data).append("\n");
            fout.flush();
            fout.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

    public String getRawData() {
        return this.fileData.toString();
    }

    public boolean internalIsEmpty() {
        return this.fileData.toString().isEmpty();
    }

    private Category[] getCategoryArray() {
        Gson gson = new Gson();
        Category[] dataArray = gson.fromJson(Arrays.toString(this.getRawData().split("\\n")), Category[].class);
        return dataArray;
    }

    public String[] getLabels() {
        Category[] dataArray = getCategoryArray();
        String[] labels = new String[dataArray.length];
        for (int i = 0; i < dataArray.length; i++) {
            labels[i] = String.valueOf(dataArray[i].getId());
        }
        return labels;
    }

    public ArrayList<Category> getDataAsArrayList() {

        return new ArrayList<>(Arrays.asList(getCategoryArray()));
    }

}
