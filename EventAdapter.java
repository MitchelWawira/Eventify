package com.example.eventify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.name.setText(event.getName());
        holder.date.setText(event.getDate());
        holder.theme.setText(event.getTheme());
        holder.price.setText(event.getPrice());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, theme, price;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.eventDate);
            theme = itemView.findViewById(R.id.eventTheme);
            price = itemView.findViewById(R.id.eventPrice);
        }
    }
}
