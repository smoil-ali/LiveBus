package com.reactive.livebus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.reactive.livebus.Activities.BusActivity;
import com.reactive.livebus.Activities.MapsActivity;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ItemBusBinding;
import com.reactive.livebus.model.BusClass;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {
    final String TAG = BusAdapter.class.getSimpleName();
    Context context;
    List<BusClass> list;

    public BusAdapter(Context context, List<BusClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bus,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        BusClass busClass = list.get(position);
        holder.binding.setData(busClass);
        holder.binding.container.setOnClickListener(v -> {
            openScreen(busClass);
        });

        holder.binding.edit.setOnClickListener(v -> {
            openEditScreen(busClass);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBusBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBusBinding.bind(itemView);
        }
    }

    void openScreen(BusClass busClass){
        Log.i(TAG,"clicked");
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra(Constants.PARAMS,busClass);
        context.startActivity(intent);
    }

    void openEditScreen(BusClass busClass){
        Intent intent = new Intent(context, BusActivity.class);
        intent.putExtra(Constants.PARAMS,busClass);
        context.startActivity(intent);
    }
}
