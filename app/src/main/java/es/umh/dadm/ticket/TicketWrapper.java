package es.umh.dadm.ticket;

import android.database.Cursor;

import java.util.ArrayList;

public class TicketWrapper {
    private ArrayList<Ticket> ticketsList;

    public TicketWrapper(Cursor cursor) {
        this.ticketsList = new ArrayList<>();
        iterateCursor(cursor);
    }

    public ArrayList<Ticket> getTicketsList() {
        return ticketsList;
    }

    private void iterateCursor(Cursor cursor) {
        while (cursor.moveToNext()) {
            Ticket listItem = new Ticket();

            listItem.id = Integer.parseInt(cursor.getString(0));
            listItem.image = cursor.getString(1);
            listItem.category = Integer.parseInt(cursor.getString(2));
            listItem.price = Double.parseDouble(cursor.getString(3));
            listItem.date = cursor.getString(4);
            listItem.shortDesc = cursor.getString(5);
            listItem.longDesc = cursor.getString(6);
            listItem.location = cursor.getString(7);

            ticketsList.add(listItem);
        }
    }
}
