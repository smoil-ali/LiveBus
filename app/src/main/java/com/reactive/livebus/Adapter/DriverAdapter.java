package com.reactive.livebus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.reactive.livebus.Activities.BusActivity;
import com.reactive.livebus.Activities.DriverActivity;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ItemDriverBinding;
import com.reactive.livebus.model.BusClass;
import com.reactive.livebus.model.DriverClass;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    final String TAG = DriverAdapter.class.getSimpleName();
    Context context;
    List<DriverClass> list;

    public DriverAdapter(Context context, List<DriverClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        DriverClass driverClass = list.get(position);
        holder.binding.setData(driverClass);
        holder.binding.edit.setOnClickListener(v -> {
            openEditScreen(driverClass);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemDriverBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDriverBinding.bind(itemView);
        }
    }

    void openEditScreen(DriverClass driverClass){
        Intent intent = new Intent(context, DriverActivity.class);
        intent.putExtra(Constants.PARAMS,driverClass);
        context.startActivity(intent);
    }
}
