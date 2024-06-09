package com.example.lab6_20182895;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab6_20182895.Bean.Egreso;
import com.example.lab6_20182895.databinding.FragmentEgresosBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Egresos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Egresos extends Fragment {

    FragmentEgresosBinding fragmentEgresosBinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Egresos() {
        // Required empty public constructor
    }

    public static Egresos newInstance(String param1, String param2) {
        Egresos fragment = new Egresos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEgresosBinding = FragmentEgresosBinding.inflate(inflater, container, false);
        View view = fragmentEgresosBinding.getRoot();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EgresoAdapter egresoAdapter = new EgresoAdapter(new ArrayList<>(), new EgresoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Egreso egreso) {
                Intent intent = new Intent(getActivity(), EditarEgreso.class);
                intent.putExtra("titulo", egreso.getTitulo());
                intent.putExtra("monto", egreso.getMonto());
                intent.putExtra("descripcion", egreso.getDescripcion());
                intent.putExtra("fecha", egreso.getFecha());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(egresoAdapter);

        db.collection("usuarios")
                .document(userId)
                .collection("Egresos")
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null) {
                        Log.w("msg-test", "Listen failed.", error);
                        return;
                    }

                    List<Egreso> egresosList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Egreso egreso = doc.toObject(Egreso.class);
                        egresosList.add(egreso);
                    }

                    egresoAdapter.setEgresosList(egresosList);
                });

        fragmentEgresosBinding.agregarEgreso.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NuevoEgreso.class);
            startActivity(intent);
        });

        return view;
    }
}
