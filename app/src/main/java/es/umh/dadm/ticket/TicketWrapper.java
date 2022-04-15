package es.umh.dadm.ticket;

import android.database.Cursor;

import java.util.ArrayList;

public class TicketWrapper extends Ticket {
    private ArrayList<Ticket> ticketsList;
    private Cursor cursor;

    public TicketWrapper(Cursor cursor) {
        this.ticketsList = new ArrayList<>();
        this.cursor = cursor;
    }

    public ArrayList<Ticket> getTicketsList() {
        return ticketsList;
    }

    public void iterateCursor() {
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

    public void closeCursor() {
        this.cursor.close();
    }

}
