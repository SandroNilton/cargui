package com.app.cargui.ui.imei;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorKt;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cargui.databinding.FragmentImeiBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ImeiFragment extends Fragment {

    EditText etIdDriver;
    Button btnRestar;
    String IdDriver;
    String NameDriver;

    public Connection connection(){
        Connection cnn= null;
        try{
            StrictMode.ThreadPolicy politic = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politic);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://cargui.nexusvirtual.net;databaseName=Nexus;user=Cargui_login;password=pass2022;");
        }catch (Exception e){
           Toast.makeText(binding.getRoot().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cnn;
    }

    private ImeiViewModel mViewModel;

    public static ImeiFragment newInstance() { return new ImeiFragment(); }

    private FragmentImeiBinding binding;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Declare variables Fragment
        IdDriver = "";
        NameDriver = "";

        ImeiViewModel imeiViewModel = new ViewModelProvider(this).get(ImeiViewModel.class);
        binding = FragmentImeiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textImei;

        binding.etImeiDriver.requestFocus();
        imeiViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.btnRestar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(binding.etImeiDriver.getText().toString().isEmpty()){
                    Toast.makeText(binding.getRoot().getContext(), "El campo codigo conductor no puede ir vacío.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SearchDriver();
                }
            }
        });

        return root;
    }

    public void SearchDriver(){

        try {
            Statement stmt = connection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IdConductor, NombreCompleto FROM Conductor WHERE IdConductor = '"+ binding.etImeiDriver.getText().toString() +"'");

            if(rs.next()){

                IdDriver = (rs.getString(1));
                NameDriver = (rs.getString(2));

                AlertDialog alertDialog = new AlertDialog.Builder(binding.getRoot().getContext()).create();
                alertDialog.setTitle("REINICIAR IMEI CONDUCTOR");
                alertDialog.setMessage("Esta seguro de reiniciar el IMEI del conductor: "+ IdDriver +" - " + NameDriver + "");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Reiniciar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RestarImei();
                        dialog.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

            }else{
                Toast.makeText(binding.getRoot().getContext(), "Driver no encontrado.", Toast.LENGTH_SHORT).show();
                binding.etImeiDriver.setText("");
            }
        }catch (Exception e){
            Toast.makeText(binding.getRoot().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void RestarImei(){
        try{
            Statement stmt  = connection().createStatement();
            stmt.executeUpdate("UPDATE ImeiOneSignalConductor SET activo = 0 WHERE IdConductor = '"+ IdDriver +"'");
            Toast.makeText(binding.getRoot().getContext(), IdDriver + " - " + NameDriver + " reiniciado correctamente.", Toast.LENGTH_SHORT) .show();
        }catch (Exception e){
            Toast.makeText(binding.getRoot().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}