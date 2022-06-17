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

import java.util.ArrayList;

import es.umh.dadm.storage.ExternalStorage;
import es.umh.dadm.ticketactivities.EditTicketActivity;
import es.umh.dadm.R;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.Holder> {

    private final Context context;
    private ArrayList<Ticket> ticketsList;

    public TicketAdapter(Context context, ArrayList<Ticket> ticketsList) {
        this.context = context;
        this.ticketsList = ticketsList;
    }

    @NonNull
    @Override
    public TicketAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ticket_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.Holder holder, int position) {
        Ticket ticket = this.ticketsList.get(position);

        holder.ticket_price.setText(String.valueOf(ticket.getPrice()));
        holder.ticket_shortDesc.setText(ticket.getShortDesc());
        holder.ticket_image.setImageBitmap(ExternalStorage.loadBitmapFromSDCard(ticket.getImage()));

        holder.itemView.setOnLongClickListener((view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("¿Desea realmente borrar este ticket?")
                    .setPositiveButton("Sí", (dialogInterface, i) -> {
                        deleteTicket(holder, ticket.getId(), ticket.getImage());
                        dialogInterface.cancel();
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
            builder.show();
            return true;
        }));

        holder.itemView.setOnClickListener((view -> {
            updateTicket(ticket);
        }));
    }

    private void updateTicket(Ticket ticket) {
        Intent intent = new Intent(context, EditTicketActivity.class);
        intent.putExtra("TICKET_KEY", ticket);
        context.startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteTicket(TicketAdapter.Holder holder, int id, String imageName) {
        SqliteHelper dbHelper = new SqliteHelper(context);
        dbHelper.deleteTicket(String.valueOf(id));
        ticketsList.remove(holder.getAdapterPosition());
        ExternalStorage.deleteBitmapFromSDCard(imageName);
        notifyItemRemoved(holder.getAdapterPosition());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        protected TextView ticket_price, ticket_shortDesc;
        protected ImageView ticket_image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ticket_price = itemView.findViewById(R.id.ticket_price);
            ticket_shortDesc = itemView.findViewById(R.id.shortDesc);
            ticket_image = itemView.findViewById(R.id.ticket_image);
        }
    }

}