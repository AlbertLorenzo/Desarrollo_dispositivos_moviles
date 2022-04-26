package es.umh.dadm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.umh.dadm.EditTicketActivity;
import es.umh.dadm.R;
import es.umh.dadm.storage.SqliteHelper;
import es.umh.dadm.ticket.Ticket;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.Holder> {

    private final Context context;
    private ArrayList<Ticket> ticketsList;
    private SqliteHelper dbHelper;

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

        holder.ticket_id.setText(String.valueOf(ticket.getId()));
        holder.ticket_price.setText(String.valueOf(ticket.getPrice()));
        holder.ticket_shortDesc.setText(ticket.getShortDesc());

        holder.itemView.setOnLongClickListener((view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("¿Desea realmente borrar este ticket?")
                    .setPositiveButton("Sí", (dialogInterface, i) -> {
                        deleteTicket(holder, ticket.getId());
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
    private void deleteTicket(TicketAdapter.Holder holder, int id) {
        dbHelper = new SqliteHelper(context);
        dbHelper.deleteTicket(String.valueOf(id));
        ticketsList.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        protected TextView ticket_id, ticket_price, ticket_shortDesc;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ticket_id = itemView.findViewById(R.id.ticket_id);
            ticket_price = itemView.findViewById(R.id.ticket_price);
            ticket_shortDesc = itemView.findViewById(R.id.shortDesc);
        }
    }

}