package com.dictionary;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.Manifest;
import android.content.pm.PackageManager;
import com.dictionary.activity.HistoryActivity;
import com.dictionary.activity.ReminderBroadcastReceiver;
import com.dictionary.activity.Setting;
import com.dictionary.activity.TextTrans;
import com.dictionary.activity.WordTrans;
import com.dictionary.activity.WordTrans_VietAnh;
import com.dictionary.activity.YourWordActivity;
import com.dictionary.db.MyDB;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ALARM_PERMISSION = 300;
    private Button btnVietAnh,btnTextTrans,btnYourword,btnHistory,btnSetting;
    private ImageButton btnSearch;
    private EditText txtSearch;
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSearch = findViewById(R.id.backButtontext);
        btnVietAnh = findViewById(R.id.btnVietAnh);
        btnTextTrans = findViewById(R.id.btnTextTrans);
        btnYourword = findViewById(R.id.btnYourword);
        btnHistory = findViewById(R.id.btnHistory);
        btnSetting = findViewById(R.id.btnSetting);
        txtSearch = findViewById(R.id.txtSearch);
        toolbar = findViewById(R.id.toolbar3);
        MyDB db = MyDB.getInstance(this);
        setSupportActionBar(toolbar);
        setNotify();
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa được cấp, yêu cầu quyền từ người dùng
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},REQUEST_ALARM_PERMISSION);
        }

        //Intent điều hướng
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WordTrans.class);
                Bundle b = new Bundle();
                b.putString("word",txtSearch.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Setting.class);
                startActivity(i);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
        btnYourword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, YourWordActivity.class);
                startActivity(i);
            }
        });
        btnTextTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TextTrans.class);
                startActivity(i);
            }
        });
        btnVietAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WordTrans_VietAnh.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ALARM_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setNotify();
            }
        }
    }

    private void setNotify(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 3);
        calendar.set(Calendar.SECOND, 0);
        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        Intent intent = new Intent(MainActivity.this, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent, PendingIntent.FLAG_MUTABLE);
        System.out.println("this is " + calendar.getTimeInMillis());
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

        }
    }
    private void createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name =  "DictionaryChannel";
            String description = "Dictionary app remind you time to learn";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
