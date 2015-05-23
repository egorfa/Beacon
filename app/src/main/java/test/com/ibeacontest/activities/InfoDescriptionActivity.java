package test.com.ibeacontest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import test.com.ibeacontest.R;
import test.com.ibeacontest.SQLDatabaseManagers.CabinetDBManager;
import test.com.ibeacontest.SQLDatabaseManagers.LessonDBManager;
import test.com.ibeacontest.SQLDatabaseManagers.MainDBManager;
import test.com.ibeacontest.SQLDatabaseManagers.TeacherDBManager;
import test.com.ibeacontest.model.Beacon;
import test.com.ibeacontest.model.Subject;

/**
 * Created by Egor on 23.05.2015.
 */
public class InfoDescriptionActivity extends BaseActivity {

    private TextView LessonTV;
    private TextView TimeTV;
    private TextView TeacherTV;
    private TextView RoomTV;
    private TextView DistanceTV;
    private ImageView TeacherIMG;

    private CabinetDBManager cabinetDBManager;
    private LessonDBManager lessonDBManager;
    private MainDBManager mainDBManager;
    private TeacherDBManager teacherDBManager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_info_description;
    }

    @Override
    protected String getTitleToolBar() {
        return "Info";
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        LessonTV =      (TextView)  findViewById(R.id.tv_lesson);
        TimeTV =        (TextView)  findViewById(R.id.tv_time);
        TeacherTV =     (TextView)  findViewById(R.id.tv_teacher);
        RoomTV =        (TextView)  findViewById(R.id.tv_room);
        DistanceTV =    (TextView)  findViewById(R.id.tv_distance);
        TeacherIMG =    (ImageView) findViewById(R.id.img_teacher);

        cabinetDBManager = new CabinetDBManager(mContext);
        lessonDBManager = new LessonDBManager(mContext);
        mainDBManager  = new MainDBManager(mContext);
        teacherDBManager = new TeacherDBManager(mContext);

        Intent getIntent = getIntent();
        Beacon beacon = getIntent.getParcelableExtra("beacon");
        Subject subject = getIntent.getParcelableExtra("subject");

        TimeTV.setText(subject.getTime());
        RoomTV.setText(cabinetDBManager.findNumberByUuidMajorMinor(beacon.getUUID(), String.valueOf(beacon.getMajor()), String.valueOf(beacon.getMinor())));
        LessonTV.setText(subject.getName());
        TeacherTV.setText(teacherDBManager.findNameByTeacherID(subject.getTeacherID()));

        int imageID = mContext.getResources().getIdentifier(teacherDBManager.findImageByTeacherID(subject.getTeacherID()), "drawable", mContext.getPackageName());
        TeacherIMG.setImageResource(imageID);

        DistanceTV.setText(new DecimalFormat("##.##").format((calculateDistance(beacon))) + "m");
    }

    private double calculateDistance(Beacon beacon){
        int txPower = beacon.getTxPower();
        double rssi = beacon.getRssi();
        double result;
        if (rssi == 0) {
            result =  -1.0; // if we cannot determine accuracy, return -1.
        }
        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            result = Math.pow(ratio,10);
        }
        else {
            result =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;

        }
        return result;
    }


}
