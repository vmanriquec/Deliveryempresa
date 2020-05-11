package com.empresadelivery.deliveryempresa.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.deliveryempresa.Mapa;
import com.empresadelivery.deliveryempresa.R;
import com.empresadelivery.deliveryempresa.modelos.Usuario;

import java.util.List;

import io.realm.Realm;


public class Adaptadorclientes extends RecyclerView.Adapter<Adaptadorclientes.AdaptadorViewHolder>{
    public Context mainContext;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<Usuario> items;


    Realm realm = Realm.getDefaultInstance();

    public Adaptadorclientes(List<Usuario> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;
        prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        String idalmacenactiv = prefs.getString("idalmacenactivo", "");


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView nombreu;
        protected TextView direccionu;
        protected TextView telefonou;
        protected TextView referenciau,correou;

        protected Button wasa,mapau;
        ;

        public AdaptadorViewHolder(View v){
            super(v);
            this.nombreu=(TextView) v.findViewById(R.id.nombreu);
            this.direccionu=(TextView) v.findViewById(R.id.direccionu);
            this.telefonou=(TextView) v.findViewById(R.id.telefonou);
            this.referenciau=(TextView) v.findViewById(R.id.referenciau);
            this.correou=(TextView) v.findViewById(R.id.correou);

            this.wasa=(Button)v.findViewById(R.id.wasa);
            this.mapau=(Button)v.findViewById(R.id.mapau);

        }
    }

    @Override
    public Adaptadorclientes.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetausuario,viewGroup,false);
        return new Adaptadorclientes.AdaptadorViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final Adaptadorclientes.AdaptadorViewHolder viewHolder, final int position) {
        final Usuario item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.nombreu.setText(item.getNombreusuario());
        viewHolder.direccionu.setText(item.getDireccion());
        viewHolder.telefonou.setText(item.getTelefono());
        viewHolder.correou.setText(item.getCorreo());
        viewHolder.referenciau.setText(item.getReferencia());
        viewHolder.direccionu.setText(item.getDireccion());
viewHolder.mapau.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent().setClass(mainContext, Mapa.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        i.putExtra("longitud", item.getLongitud().toString());
        i.putExtra("latitud", item.getLatitud().toString());
        i.putExtra("nombre", item.getNombreusuario().toString());
        i.putExtra("direccion", item.getDireccion().toString());

// Launch the new activity and add the additional flags to the intent
        mainContext.startActivity(i);



    }
});

        viewHolder.wasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+viewHolder.telefonou.getText().toString()+"&text="+"Hola..."));
                    mainContext.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }




            }
        });


    }
    @Override
    public int getItemCount() {
        return items.size();
    }

}



