package com.example.fitnext;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnext.mydatabase.MedicalDB;

import java.util.Calendar;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MedicineHolder> {
    private Cursor med_list;
    public Context context;
    public MedicalDB helper;
    public int user_id;

    public MedicineListAdapter(Context context, MedicalDB helper, int user_id){
        this.context = context;
        this.helper = helper;
        this.user_id = user_id;
    }

    public void setUserData(Cursor cursor){
        this.med_list = cursor;
        if(med_list!=null)
        {
            med_list.moveToFirst();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_card,parent,false);
        MedicineHolder vh = new MedicineHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        if(med_list!=null){
            holder.medName.setText(med_list.getString(1));
            holder.qty.setText("Qty: "+med_list.getInt(2));
            holder.id = med_list.getInt(0);
            holder.time.setText(med_list.getString(3));

            boolean isChecked = med_list.getInt(6)==1?true:false;
            holder.toggleSwitch.setChecked(isChecked);

            holder.toggleSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.toggleSwitch.isChecked()){
                        helper.setEnable(helper.getWritableDatabase(), holder.id, 1);
                    } else {
                        helper.setEnable(helper.getWritableDatabase(), holder.id, 0);
                    }

                    Cursor c = helper.getMedicine(helper.getWritableDatabase(), holder.id);
                    c.moveToFirst();
                    String[] raw_time = c.getString(3).split(":",2);
                    int hour = Integer.parseInt(raw_time[0]);
                    int min = Integer.parseInt(raw_time[1]);

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, min);
                    cal.set(Calendar.SECOND, 0);

                    Calendar now = Calendar.getInstance();
                    now.set(Calendar.SECOND, 0);
                    now.set(Calendar.MILLISECOND, 0);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                    Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
                    intent.putExtra("medName", c.getString(1));
                    intent.putExtra("medQty", c.getString(2));
                    intent.putExtra("medTime", c.getString(3));
                    intent.putExtra("userName", helper.getUserName(helper.getWritableDatabase(), user_id));

                    if (holder.toggleSwitch.isChecked()) {
                        // Set alarm
                        String days = c.getString(4);
                        if (days.equals("0000000")) {
                            if (cal.before(now)) {
                                cal.add(Calendar.HOUR_OF_DAY, 1); // Add 1 hour instead of 1 day
                            }
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(user_id + "" + holder.id), intent, PendingIntent.FLAG_IMMUTABLE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                            Toast.makeText(context, "Reminder set for " + c.getString(1) + " on " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                        } else {
                            int ct = 1;
                            for (char d : days.toCharArray()) {
                                if (d == '1') {
                                    cal.set(Calendar.DAY_OF_WEEK, ct);
                                    if (cal.before(now)) {
                                        cal.add(Calendar.DATE, 7);
                                    }

                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(user_id + "" + holder.id + "" + ct), intent, PendingIntent.FLAG_IMMUTABLE);
                                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                                }
                                ct++;
                            }
                            Toast.makeText(context, "Reminder set for " + c.getString(1) + " on " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ", " + cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String days = c.getString(4);
                        if (days.equals("0000000")) {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(user_id + "" + holder.id), intent, PendingIntent.FLAG_IMMUTABLE);
                            alarmManager.cancel(pendingIntent);
                        } else {
                            int ct = 1;
                            for (char d : days.toCharArray()) {
                                if (d == '1') {
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(user_id + "" + holder.id + "" + ct), intent, PendingIntent.FLAG_IMMUTABLE);
                                    alarmManager.cancel(pendingIntent);
                                }
                                ct++;
                            }
                        }
                    }
                }
            });

            holder.deleteMed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.deleteMedicine(helper.getWritableDatabase(), holder.id);
                    setUserData(helper.getMedicineListById(helper.getWritableDatabase(), user_id));
                }
            });
            med_list.moveToNext();
        }
    }

    @Override
    public int getItemCount() {
        return med_list != null ? med_list.getCount() : 0;
    }

    public class MedicineHolder extends RecyclerView.ViewHolder {
        TextView medName, time, qty;
        ImageButton deleteMed;
        int id;
        Switch toggleSwitch;

        public MedicineHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.med_name);
            time = itemView.findViewById(R.id.med_time);
            qty = itemView.findViewById(R.id.med_quantity);
            deleteMed = itemView.findViewById(R.id.delete_med);
            toggleSwitch = itemView.findViewById(R.id.toggle_switch);
        }
    }
}
