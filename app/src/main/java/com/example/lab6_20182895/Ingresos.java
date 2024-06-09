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

import com.example.lab6_20182895.Bean.Ingreso;
import com.example.lab6_20182895.databinding.FragmentIngresosBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ingresos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ingresos extends Fragment {

    FragmentIngresosBinding fragmentIngresosBinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Ingresos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ingresos.
     */
    // TODO: Rename and change types and number of parameters
    public static Ingresos newInstance(String param1, String param2) {
        Ingresos fragment = new Ingresos();
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
        fragmentIngresosBinding = FragmentIngresosBinding.inflate(inflater, container, false);
        View view = fragmentIngresosBinding.getRoot();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        IngresoAdapter ingresoAdapter = new IngresoAdapter(new ArrayList<>(), new IngresoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ingreso ingreso) {
                Intent intent = new Intent(getActivity(), EditarIngreso.class);
                intent.putExtra("titulo", ingreso.getTitulo());
                intent.putExtra("monto", ingreso.getMonto());
                intent.putExtra("descripcion", ingreso.getDescripcion());
                intent.putExtra("fecha", ingreso.getFecha());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(ingresoAdapter);

        db.collection("usuarios")
                .document(userId)
                .collection("Ingresos")
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null) {
                        Log.w("msg-test", "Listen failed.", error);
                        return;
                    }

                    List<Ingreso> ingresosList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Ingreso ingreso = doc.toObject(Ingreso.class);
                        ingresosList.add(ingreso);
                    }

                    ingresoAdapter.setIngresosList(ingresosList);
                });

        fragmentIngresosBinding.agregarIngreso.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NuevoIngreso.class);
            startActivity(intent);
        });

        return view;
    }
}