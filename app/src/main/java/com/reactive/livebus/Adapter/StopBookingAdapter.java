package com.reactive.livebus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.reactive.livebus.Interfaces.StudentStopListener;
import com.reactive.livebus.R;
import com.reactive.livebus.databinding.ItemStopBookingBinding;
import com.reactive.livebus.model.StopClass;

import java.util.List;

public class StopBookingAdapter extends RecyclerView.Adapter<StopBookingAdapter.ViewHolder> {

    final String TAG = StopBookingAdapter.class.getSimpleName();
    Context context;
    List<StopClass> list;
    int temp = -1;
    StudentStopListener listener;

    public void setListener(StudentStopListener listener) {
        this.listener = listener;
    }

    public StopBookingAdapter(Context context, List<StopClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stop_booking,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        StopClass stopClass = list.get(position);
        holder.binding.setData(stopClass);

        holder.binding.container.setOnClickListener(v -> {
            if (temp != -1 && temp != position){
                if (list.get(temp).isSelected2()){
                    list.get(temp).setSelected2(false);
                }
            }
            if (stopClass.isSelected2()){
                stopClass.setSelected2(false);
            }else {
                stopClass.setSelected2(true);
                temp = position;
            }
            listener.onStopClick(stopClass);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemStopBookingBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStopBookingBinding.bind(itemView);
            binding.executePendingBindings();
        }
    }
}
