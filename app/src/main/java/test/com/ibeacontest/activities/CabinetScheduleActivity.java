package test.com.ibeacontest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import test.com.ibeacontest.R;
import test.com.ibeacontest.SQLDatabaseManagers.CabinetDBManager;
import test.com.ibeacontest.SQLDatabaseManagers.LessonDBManager;
import test.com.ibeacontest.SQLDatabaseManagers.MainDBManager;
import test.com.ibeacontest.adapters.DividerItemDecoration;
import test.com.ibeacontest.adapters.NewScheduleListAdapter;
import test.com.ibeacontest.model.Beacon;
import test.com.ibeacontest.model.DayWithSubjects;
import test.com.ibeacontest.model.Subject;

/**
 * Created by Egor on 23.05.2015.
 */
public class CabinetScheduleActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView mSubjectsList;

    private Beacon mBeacon;
    ArrayList<Subject> mSubjects;

    private MainDBManager mainDBManager;
    private LessonDBManager lessonDBManager;
    private CabinetDBManager cabinetDBManager;

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
        return R.layout.activity_schedule;
    }

    @Override
    protected String getTitleToolBar() {
        return null;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainDBManager = new MainDBManager(mContext);
        lessonDBManager = new LessonDBManager(mContext);
        cabinetDBManager = new CabinetDBManager(mContext);

        //Получаем Extra-данные из предыдущего Activity
        Intent intent = getIntent();
        mBeacon = intent.getParcelableExtra("beacon");

        TextView tv = (TextView)findViewById(R.id.toolBarCenterText);
        tv.setVisibility(View.VISIBLE);
        tv.setText(mBeacon.getName() + " Аудитория");

        //Находим RecyclerView, в который будут выводится все найденные маячки
        mSubjectsList = (RecyclerView) findViewById(R.id.recyclerview);
        mSubjectsList.setHasFixedSize(true);
        mSubjectsList.setItemAnimator(new DefaultItemAnimator());
        mSubjectsList.setLayoutManager(new LinearLayoutManager(mContext));
        mSubjectsList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        //Находим View для анимации
        final View mColoredView = findViewById(R.id.colored_background_view);

        //Устанавливаем анимацию
        mSubjectsList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollColoredViewParallax(dy);
                if (dy > 0) {
                    hideToolbarBy(dy);
                } else {
                    showToolbarBy(dy);
                }
            }

            private void scrollColoredViewParallax(int dy) {
                if(cannotShowMoreColoredView(dy)){
                    mColoredView.setTranslationY(0);
                }else {
                    mColoredView.setTranslationY(mColoredView.getTranslationY() - dy / 2);
                }
            }

            private boolean cannotShowMoreColoredView(int dy){
                return mColoredView.getTranslationY() - dy > 0;
            }

            private void hideToolbarBy(int dy) {
                if (cannotHideMore(dy)) {
                    mToolBar.setTranslationY(-mToolBar.getBottom());
                } else {
                    mToolBar.setTranslationY(mToolBar.getTranslationY() - dy);
                }
            }
            private boolean cannotHideMore(int dy) {
                return Math.abs(mToolBar.getTranslationY()-dy)>mToolBar.getBottom();
                //return Math.abs(toolbarContainer.getTranslationY() - dy) > tabBar.getBottom();
            }
            private void showToolbarBy(int dy) {
                boolean flag = false;
                if (cannotShowMore(dy)) {
                    //toolbarContainer.setTranslationY(0);
                    mToolBar.setTranslationY(0);

                } else {
                    mToolBar.setTranslationY(mToolBar.getTranslationY() - dy);
                    //toolbarContainer.setTranslationY(toolbarContainer.getTranslationY() - dy);

                }
            }
            private boolean cannotShowMore(int dy) {
                return mToolBar.getTranslationY() - dy > 0;
            }

        });

        String LessonID;

        mSubjects = new ArrayList<>();

        //ArrayList<ArrayList<Object>> objects = mainDBManager.findAllLessonsByCabinetID(cabinetDBManager.findNumberByUuidMajorMinor(beacon.getUUID(), String.valueOf(beacon.getMajor()), String.valueOf(beacon.getMinor())));//TODO byCAbinet
        ArrayList<ArrayList<Object>> objects = mainDBManager.getAllRowsAsArrays();
        for(int i=0;i<objects.size();i++)
        {
            Subject subject = new Subject();
            subject.setAudience(mBeacon.getName());
            subject.setDay((String) objects.get(i).get(1));
            subject.setTime((String) objects.get(i).get(2));
            LessonID = (String) objects.get(i).get(3);
            subject.setName(lessonDBManager.findNameLessonByLessonID(LessonID));
            subject.setTeacherID(lessonDBManager.findTeacherIDByLessonID(LessonID));

            mSubjects.add(subject);
        }

        final ArrayList<DayWithSubjects> days = new ArrayList<DayWithSubjects>();

        for(int i=0;i< mSubjects.size();i++)
        {
            boolean flag = false;
            for(int j = 0;j<days.size();j++){
                if(mSubjects.get(i).getDay().equals(days.get(j).getDay())){
                    days.get(j).getSubjects().add(mSubjects.get(i));
                    flag = true;
                }
            }
            if(flag==false)
            {
                ArrayList<Subject> subjects = new ArrayList<Subject>();
                subjects.add(mSubjects.get(i));
                DayWithSubjects day = new DayWithSubjects(mSubjects.get(i).getDay(), subjects);
                days.add(day);
            }
        }


        mSubjectsList.setAdapter(new NewScheduleListAdapter(mContext, days, this) {
            @Override
            protected void click(int position, int listPosition) {

                Subject subject = days.get(position).getSubjects().get(listPosition);
                Intent intent = new Intent(CabinetScheduleActivity.this, InfoDescriptionActivity.class);
                intent.putExtra("beacon", mBeacon);
                intent.putExtra("subject", mSubjects.get(position));
                startActivity(intent);
            }
        });
        //mSubjectsList.setAdapter(new ScheduleListAdapter(mContext, mSubjects, this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.item_cell:
                int position = mSubjectsList.getChildLayoutPosition(v);
                Intent intent = new Intent(CabinetScheduleActivity.this, InfoDescriptionActivity.class);
                intent.putExtra("beacon", mBeacon);
                intent.putExtra("subject", mSubjects.get(position));
                startActivity(intent);
                break;*/
        }
    }
}
