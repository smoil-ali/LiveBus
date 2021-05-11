package com.reactive.livebus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reactive.livebus.R;
import com.reactive.livebus.Interfaces.StopListener;
import com.reactive.livebus.databinding.ItemBusFormBinding;
import com.reactive.livebus.model.StopClass;

import java.util.List;

public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.ViewHolder> {
    final String TAG = StopsAdapter.class.getSimpleName();
    Context context;
    List<StopClass> list;
    StopListener listener;

    public void setListener(StopListener listener) {
        this.listener = listener;
    }

    public StopsAdapter(Context context, List<StopClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bus_form,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StopClass stopClass = list.get(position);
        holder.binding.setData(stopClass);
        holder.binding.container.setOnClickListener(v -> {
            if (stopClass.isSelected()){
                stopClass.setSelected(false);
                listener.onStopRemoveClick(stopClass);
            }else {
                stopClass.setSelected(true);
                listener.onStopAddClick(stopClass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBusFormBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBusFormBinding.bind(itemView);
            binding.executePendingBindings();
        }
    }
}
