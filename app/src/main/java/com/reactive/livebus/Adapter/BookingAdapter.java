package com.reactive.livebus.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reactive.livebus.Activities.BusLocationActivity;
import com.reactive.livebus.Activities.MapsActivity;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ItemBookingBinding;
import com.reactive.livebus.model.BookingClass;
import com.reactive.livebus.model.BusClass;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    final String TAG = BookingAdapter.class.getSimpleName();
    Context context;
    List<BookingClass> bookingClasses;

    public BookingAdapter(Context context, List<BookingClass> bookingClasses) {
        this.context = context;
        this.bookingClasses = bookingClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingClass bookingClass = bookingClasses.get(position);
        BusClass busClass = bookingClass.getBusClass();
        holder.binding.busNumber.setText(busClass.getBusNumber());
        holder.binding.destination.setText(bookingClass.getDestination());
        holder.binding.seats.setText(bookingClass.getSeats());
        holder.binding.startTime.setText(busClass.getStartTime());
        holder.binding.entTime.setText(busClass.getArrivalTime());
        holder.binding.map.setOnClickListener(v -> {
            openScreen(busClass);
        });
    }

    @Override
    public int getItemCount() {
        return bookingClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBookingBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookingBinding.bind(itemView);
        }
    }

    void openScreen(BusClass busClass){
        Intent intent = new Intent(context, BusLocationActivity.class);
        intent.putExtra(Constants.PARAMS,busClass);
        context.startActivity(intent);
    }
}
