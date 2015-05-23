package test.com.ibeacontest.callbacks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import test.com.ibeacontest.model.Beacon;

/**
 * Created by Egor on 22.05.2015.
 */

//класс - сallback для обработки принятой информации с маячка

public abstract  class LeScanCallback implements BluetoothAdapter.LeScanCallback {

    private final char[] hexArray = "0123456789ABCDEF".toCharArray();

@Override
public void onLeScan(final BluetoothDevice device, final int rssi,
        byte[] scanRecord) {

            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5) {
            int a = scanRecord[startByte+2];
            int b=a&0xff;
            int c = scanRecord[startByte+3];
            int d = c&0xff;
            if (    b == 0x02 && //Identifies an iBeacon//Преамбула
            d == 0x15) { //Identifies correct data length
            patternFound = true;
            Log.d("EGORRR", "Преамбула найдена");
            break;
            }
            startByte++;
            }

            if (patternFound) {
                //Convert to hex String
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                //Here is your UUID
                String uuid =  hexString.substring(0,8) + "-" +
                hexString.substring(8,12) + "-" +
                hexString.substring(12,16) + "-" +
                hexString.substring(16,20) + "-" +
                hexString.substring(20,32);
                Log.d("EGORRR", "UUID=" + uuid);
                //Here is your Major value
                int major = (scanRecord[startByte+20] & 0xff) * 0x100 + (scanRecord[startByte+21] & 0xff);
                Log.d("EGORRR", "major=" + major);
                //Here is your Minor value
                int minor = (scanRecord[startByte+22] & 0xff) * 0x100 + (scanRecord[startByte+23] & 0xff);

                int txpower = scanRecord[startByte+24];

                Beacon NewDevice = new Beacon(device, String.valueOf(minor), uuid, major, minor, txpower, rssi);

                //Добавляем устройство в список найденных в RecyclerView, используя абстрактный метод
                addDeviceToRecycleView(NewDevice);


                Log.d("EGORRR", "minor=" + minor);
            }


            }

        private String bytesToHex(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }

        public abstract void addDeviceToRecycleView(Beacon device);

}