package com.example.lab6_20182895;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6_20182895.Bean.Egreso;
import com.example.lab6_20182895.databinding.EgresosRecyclerItemBinding;

import java.util.List;

public class EgresoAdapter extends RecyclerView.Adapter<EgresoAdapter.EgresoViewHolder> {

    private List<Egreso> egresosList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Egreso egreso);
    }

    public EgresoAdapter(List<Egreso> egresosList, OnItemClickListener listener) {
        this.egresosList = egresosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EgresoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EgresosRecyclerItemBinding binding = EgresosRecyclerItemBinding.inflate(inflater, parent, false);
        return new EgresoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EgresoViewHolder holder, int position) {
        holder.bind(egresosList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return egresosList.size();
    }

    public void setEgresosList(List<Egreso> egresosList) {
        this.egresosList = egresosList;
        notifyDataSetChanged();
    }

    static class EgresoViewHolder extends RecyclerView.ViewHolder {
        private EgresosRecyclerItemBinding binding;

        public EgresoViewHolder(EgresosRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Egreso egreso, OnItemClickListener listener) {
            binding.recTitulo.setText(egreso.getTitulo());
            binding.recFecha.setText(egreso.getFecha());
            binding.recMonto.setText("-S/." + String.valueOf(egreso.getMonto()));
            binding.recDescripcion.setText(egreso.getDescripcion());

            binding.getRoot().setOnClickListener(v -> {
                listener.onItemClick(egreso);
            });
        }
    }
}
