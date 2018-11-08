package com.example.sabreurn.roomreservations;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
	OnTimeSetListener onTimeSetListener;

	public void setCallback(OnTimeSetListener listener) {
		onTimeSetListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, true);
	}
}
