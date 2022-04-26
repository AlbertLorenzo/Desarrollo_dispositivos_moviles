package es.umh.dadm.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import es.umh.dadm.ticket.Ticket;

public class SqliteHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DB_NAME = "BDTickets.db";
    private static int DB_VER = 1;

    private static final String TABLE_NAME = "Tickets";

    private static final String COL_ID = "_id";
    private static final String COL_IMAGE = "foto";
    private static final String COL_CATEGORY = "categoria";
    private static final String COL_PRICE = "precio";
    private static final String COL_DATE = "fecha";
    private static final String COL_SDESC = "desCorta";
    private static final String COL_LDESC = "desLarga";
    private static final String COL_LOCATION = "localiz";

    private static final String ticketsTable = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, "
            + COL_IMAGE + " text, " + COL_CATEGORY + " int not null, " + COL_PRICE + " real not null, " +
            COL_DATE + " long not null, " + COL_SDESC + " text not null, " + COL_LDESC + " text not null, "
            + COL_LOCATION + " text);";

    public SqliteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ticketsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTicket(Ticket ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Log.i("Ticket: ", ticket.toString());

        cv.put(COL_IMAGE, ticket.getImage());
        cv.put(COL_CATEGORY, ticket.getCategory());
        cv.put(COL_PRICE, ticket.getPrice());
        cv.put(COL_DATE, ticket.getDate());
        cv.put(COL_SDESC, ticket.getShortDesc());
        cv.put(COL_LDESC, ticket.getLongDesc());
        cv.put(COL_LOCATION, ticket.getLocation());

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Err.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Ticket añadido correctamente.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTicket(Ticket ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Log.i("Ticket: ", ticket.toString());

        cv.put(COL_IMAGE, ticket.getImage());
        cv.put(COL_CATEGORY, ticket.getCategory());
        cv.put(COL_PRICE, ticket.getPrice());
        cv.put(COL_DATE, ticket.getDate());
        cv.put(COL_SDESC, ticket.getShortDesc());
        cv.put(COL_LDESC, ticket.getLongDesc());
        cv.put(COL_LOCATION, ticket.getLocation());

        long result = db.update(TABLE_NAME, cv, "_id = ?", new String[]{String.valueOf(ticket.getId())});

        if (result == -1) {
            Toast.makeText(context, "Err.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTicket(String _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{_id});

        if (result == -1) {
            Toast.makeText(context, "Err.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "ticket eliminado", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getTicketsData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = null;

        if (db != null) {
            data = db.rawQuery(query, null);
        }

        return data;
    }
}