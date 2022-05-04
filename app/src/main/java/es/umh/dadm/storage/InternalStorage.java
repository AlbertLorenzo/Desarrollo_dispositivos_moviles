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

import es.umh.dadm.appcontext.App;
import es.umh.dadm.category.Category;

public class InternalStorage {
    private static final String FILE_NAME = "categorias.json";

    /*
        No es la mejor idea guardarlo en un SB de forma permanente
        pero evita más código y tener un mareo de instancias
     */
    private final StringBuilder fileData = new StringBuilder();

    public InternalStorage() {
        if (fileExists() && !fileIsEmpty()) {
            loadData();
        }
    }

    private void loadData() {
        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(
                            App.getContext().openFileInput(FILE_NAME)
                    )
            );
            String line;
            while ((line = fin.readLine()) != null) {
                fileData.append(line).append("\n");
            }
            fin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void appendLine(String data) {
        try {
            OutputStreamWriter fout = new OutputStreamWriter(
                    App.getContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE | Context.MODE_APPEND)
            );
            fout.append(data).append("\n");
            fout.flush();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeNewData(String data) {
        try {
            OutputStreamWriter fout = new OutputStreamWriter(
                    App.getContext().openFileOutput(
                            FILE_NAME, Context.MODE_PRIVATE
                    )
            );
            fout.write(data);
            fout.flush();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean fileIsEmpty() {
        File file = App.getContext().getFileStreamPath(FILE_NAME);
        return file.length() == 0;
    }

    private boolean fileExists() {
        File file = App.getContext().getFileStreamPath(FILE_NAME);
        return file.exists();
    }

    public Category[] filDataAsArray() {
        Gson gson = new Gson();
        return gson.fromJson(Arrays.toString(this.fileData.toString().split("\\n")), Category[].class);
    }

    public ArrayList<Category> fileDataAsArrayList() {
        return new ArrayList<>(
                Arrays.asList(filDataAsArray())
        );
    }
}
