package test.com.ibeacontest.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import test.com.ibeacontest.R;
import test.com.ibeacontest.adapters.BeaconsListAdapter;
import test.com.ibeacontest.adapters.DividerItemDecoration;
import test.com.ibeacontest.callbacks.LeScanCallback;
import test.com.ibeacontest.model.Beacon;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class DeviceScanActivity extends BaseActivity implements View.OnClickListener {

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private boolean mTurnOnBluetooth;
    private Handler mHandler;

    private RecyclerView mBeaconsList;
    private BeaconsListAdapter mBeaconListAdapter;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final long SCAN_PERIOD = 30000;//Период сканирования



    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activtiy_device_scan;
    }

    @Override
    protected String getTitleToolBar() {
        return "Scan";
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mTurnOnBluetooth) {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_turnon).setVisible(true);
        } else {
            menu.findItem(R.id.menu_turnon).setVisible(false);
            if (!mScanning) {
                menu.findItem(R.id.menu_stop).setVisible(false);
                menu.findItem(R.id.menu_scan).setVisible(true);
            } else {
                menu.findItem(R.id.menu_stop).setVisible(true);
                menu.findItem(R.id.menu_scan).setVisible(false);
            }
        }//TODO
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_turnon:
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                break;
            case R.id.menu_scan:
                mBeaconListAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Находим RecyclerView, в который будут выводится все найденные маячки
        mBeaconsList = (RecyclerView) findViewById(R.id.recyclerview);
        mBeaconsList.setHasFixedSize(true);
        mBeaconsList.setItemAnimator(new DefaultItemAnimator());
        mBeaconsList.setLayoutManager(new LinearLayoutManager(mContext));
        mBeaconsList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        //Находим View для анимации
        final View mColoredView = findViewById(R.id.colored_background_view);

        //Устанавливаем анимацию
        mBeaconsList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

        //Устанавливаем адаптер для RecyclerView
        //TODO remove hardcode
        mBeaconListAdapter = new BeaconsListAdapter(mContext, new ArrayList<Beacon>(), this);
        mBeaconsList.setAdapter(mBeaconListAdapter);


        mTurnOnBluetooth = false;
        mHandler = new Handler();

        //Проверяем поддержку Bluetooth Low Energy, в отсутствии таковой сообщаем об этом в Toast
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
                    .show();
            finish();
        }

        //Проверяем поддержку Bluetooth, в отсутствии таковой сообщаем об этом в Toast
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported,
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(mBluetoothAdapter.getState()!= mBluetoothAdapter.STATE_OFF){
            mTurnOnBluetooth = true;
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        mBeaconListAdapter.clear();
    }


    //Переписанный метод у класса Activity(), чтобы Bluetooth выключался при закрытии Activity
    @Override
    public void finish() {
        super.finish();
        if (mBluetoothAdapter.getState() != mBluetoothAdapter.STATE_OFF)
            mBluetoothAdapter.disable();
    }



    //Метод для вызова Activity() с запросом пользователя на разрешение на включение сервиса Bluetooth
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT
                && resultCode == Activity.RESULT_CANCELED) {
            //finish();
            return;
        } else {
            mTurnOnBluetooth = true;
            invalidateOptionsMenu();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable) {
        final LeScanCallback callback = new LeScanCallback() {
            @Override
            public void addDeviceToRecycleView(Beacon beacon) {
                mBeaconListAdapter.add(beacon);
            }
        };
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;

                    mBluetoothAdapter.stopLeScan(callback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(callback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(callback);
        }
        invalidateOptionsMenu();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_cell:
                final int position = mBeaconsList.getChildLayoutPosition(v);
                Beacon beacon = mBeaconListAdapter.getItem(position);
                Intent intent = new Intent(DeviceScanActivity.this, CabinetScheduleActivity.class);
                intent.putExtra("beacon", beacon);
                startActivity(intent);
                break;
        }
    }


}