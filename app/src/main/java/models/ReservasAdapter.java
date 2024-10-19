package models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.josethcodedev.agenda_pro.R;

import java.util.ArrayList;

public class ReservasAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Reserva> reservasList;
    private LayoutInflater inflater;

    // Constructor corregido
    public ReservasAdapter(Context context, ArrayList<Reserva> reservasList) {
        this.context = context;
        this.reservasList = reservasList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return reservasList.size();

    }

    @Override
    public Object getItem(int position) {
        return reservasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_reservas, parent, false);
        }

        // Obtener los datos de la reserva
        Reserva reserva = reservasList.get(position);
        Log.d("Reservas", "Número de elementos en la lista: " + reservasList.size());


        // Configurar los elementos del layout
        ImageView imageView = convertView.findViewById(R.id.reserva_image);
        TextView direccionInicio = convertView.findViewById(R.id.direccion_inicio);
        TextView direccionDestino = convertView.findViewById(R.id.direccion_destino);
        TextView fecha = convertView.findViewById(R.id.fecha);
        TextView horario = convertView.findViewById(R.id.horario);
        TextView metodoPago = convertView.findViewById(R.id.metodo_pago);

        // Setear los valores
        imageView.setImageResource(R.drawable.logo_sf_nuevo);
        direccionInicio.setText(reserva.getDireccionInicio());
        direccionDestino.setText(reserva.getDireccionDestino());
        fecha.setText(reserva.getFecha());
        horario.setText(reserva.getHorario());
        metodoPago.setText(reserva.getMetodoPago());

        return convertView;
    }
}


