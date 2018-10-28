package com.example.sabreurn.roomreservations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ReservationListItemAdapter extends ArrayAdapter<Reservation> {
	private final int resource;

	public ReservationListItemAdapter(Context context, int resource, List<Reservation> reservations) {
		super(context, resource, reservations);
		this.resource = resource;
	}

	public ReservationListItemAdapter(Context context, int resource, Reservation[] reservations) {
		super(context, resource, reservations);
		this.resource = resource;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		Reservation reservation = getItem(position);
		String room = "Room " + reservation.getRoomId();
		String time = reservation.getFromTimeString() + " to " + reservation.getToTimeString();
		LinearLayout reservationView;

		if(convertView == null) {
			reservationView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);
			li.inflate(resource, reservationView, true);
		} else {
			reservationView = (LinearLayout)convertView;
		}

		TextView roomView = reservationView.findViewById(R.id.reservationlist_item_room);
		TextView timeView = reservationView.findViewById(R.id.reservationlist_item_time);
		roomView.setText(room);
		timeView.setText(" " + time);

		return reservationView;
	}
}
