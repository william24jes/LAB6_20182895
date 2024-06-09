package com.example.lab6_20182895;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6_20182895.Bean.Ingreso;
import com.example.lab6_20182895.databinding.IngresosRecyclerItemBinding;

import java.util.List;

public class IngresoAdapter extends RecyclerView.Adapter<IngresoAdapter.IngresoViewHolder> {

    private List<Ingreso> ingresosList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ingreso ingreso);
    }

    public IngresoAdapter(List<Ingreso> ingresosList, OnItemClickListener listener) {
        this.ingresosList = ingresosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngresoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IngresosRecyclerItemBinding binding = IngresosRecyclerItemBinding.inflate(inflater, parent, false);
        return new IngresoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngresoViewHolder holder, int position) {
        holder.bind(ingresosList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return ingresosList.size();
    }

    public void setIngresosList(List<Ingreso> ingresosList) {
        this.ingresosList = ingresosList;
        notifyDataSetChanged();
    }

    static class IngresoViewHolder extends RecyclerView.ViewHolder {
        private IngresosRecyclerItemBinding binding;

        public IngresoViewHolder(IngresosRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ingreso ingreso, OnItemClickListener listener) {
            binding.recTitulo.setText(ingreso.getTitulo());
            binding.recFecha.setText(ingreso.getFecha());
            binding.recMonto.setText("+S/." + String.valueOf(ingreso.getMonto()));
            binding.recDescripcion.setText(ingreso.getDescripcion());

            binding.getRoot().setOnClickListener(v -> {
                listener.onItemClick(ingreso);
            });
        }
    }
}
