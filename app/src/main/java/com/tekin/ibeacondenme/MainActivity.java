    package com.tekin.ibeacondenme;

    import android.app.NotificationManager;
    import android.app.PendingIntent;
    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;

    import com.estimote.sdk.Beacon;
    import com.estimote.sdk.BeaconManager;
    import com.estimote.sdk.Region;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;
    import java.util.UUID;

    public class MainActivity extends AppCompatActivity {

        private BeaconManager beaconManager;

        TextView tv, tv1, tv2, tv3, tv4;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tv = (TextView) findViewById(R.id.ogr);
            tv1 = (TextView) findViewById(R.id.syd);
            tv2 = (TextView) findViewById(R.id.trh);
            tv3 = (TextView) findViewById(R.id.gs);
            tv4 = (TextView) findViewById(R.id.cs);
            Button btn = (Button) findViewById(R.id.button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent git = new Intent(MainActivity.this, ikincisayfa.class);
                    startActivity(git);
                }
            });
            beaconManager = new BeaconManager(getApplicationContext());

            beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                @Override

                public void onEnteredRegion(Region region, List<Beacon> list) {

                    showNotification(

                            "Dershaneye giriş yapıldı",

                            "Giriş verileri kaydedildi");


                    tv.setText("Ömür");

                    tv1.setText("Tekin");

                    SimpleDateFormat tarih = new SimpleDateFormat("dd/MM/yyyy");

                    String currentDate = tarih.format(new Date());

                    tv2.setText(currentDate);

                    SimpleDateFormat saat = new SimpleDateFormat("HH:mm:ss");

                    String currentDateandTime = saat.format(new Date());

                    tv3.setText(currentDateandTime);

                }

                @Override

                public void onExitedRegion(Region region) {

                    showNotification("Dersten çıkış yapıldı", "Günlük veriler kaydedildi");


                    tv.setText("Ömür ");

                    tv1.setText("TEKİN");

                    SimpleDateFormat tarih = new SimpleDateFormat("dd/MM/yyyy");

                    String currentDate = tarih.format(new Date());

                    tv2.setText(currentDate);

                    SimpleDateFormat saat = new SimpleDateFormat("HH:mm:ss");

                    String currentDateandTime = saat.format(new Date());

                    tv4.setText(currentDateandTime);


                }


            });
            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {

                @Override

                public void onServiceReady() {

                    beaconManager.startMonitoring(new Region("monitored region",

                            UUID.fromString("Beacon-ID"), MinorID, MajorID));

                }

            });
        }
        public void showNotification(String title, String message) {

            Intent notifyIntent = new Intent(this, MainActivity.class);

            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,

                    new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new Notification.Builder(this)

                    .setSmallIcon(android.R.drawable.ic_dialog_info)

                    .setContentTitle(title)

                    .setAutoCancel(true)

                    .setContentIntent(pendingIntent)

                    .setStyle(new Notification.BigTextStyle().bigText(message))

                    .build();

            notification.defaults |= Notification.DEFAULT_SOUND;

            NotificationManager notificationManager =

                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1, notification);



        }


    }
