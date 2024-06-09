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

    public IngresoAdapter(List<Ingreso> ingresosList) {
        this.ingresosList = ingresosList;
    }

    @NonNull
    @Override
    public IngresoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IngresosRecyclerItemBinding ingresosRecyclerItemBinding = IngresosRecyclerItemBinding.inflate(inflater, parent, false);
        return new IngresoViewHolder(ingresosRecyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngresoViewHolder holder, int position) {
        Ingreso ingreso = ingresosList.get(position);
        holder.bind(ingreso);
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

        public void bind(Ingreso ingreso) {
            binding.recTitulo.setText(ingreso.getTitulo());
            binding.recFecha.setText(ingreso.getFecha());
            binding.recMonto.setText("S/."+String.valueOf(ingreso.getMonto()));
            binding.recDescripcion.setText(ingreso.getDescripcion());
        }
    }
}
