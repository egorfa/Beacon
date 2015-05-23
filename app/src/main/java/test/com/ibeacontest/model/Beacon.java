package test.com.ibeacontest.model;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Egor on 12.05.2015.
 */
public class Beacon implements Parcelable {
    BluetoothDevice BLdevice;
    private String Name;
    private String UUID;
    private int Major;
    private int Minor;
    private int TxPower;
    private int Rssi;

    public Beacon(BluetoothDevice BLdevice, String name, String UUID, int major, int minor, int txPower, int rssi) {
        this.BLdevice = BLdevice;
        Name = name;
        this.UUID = UUID.replaceAll("-", "");
        Major = major;
        Minor = minor;
        TxPower = txPower;
        this.Rssi = rssi;
    }

    public Beacon() {
    }


    public BluetoothDevice getBLdevice() {
        return BLdevice;
    }

    public void setBLdevice(BluetoothDevice BLdevice) {
        this.BLdevice = BLdevice;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getMajor() {
        return Major;
    }

    public void setMajor(int major) {
        Major = major;
    }

    public int getMinor() {
        return Minor;
    }

    public void setMinor(int minor) {
        Minor = minor;
    }

    public int getTxPower() {
        return TxPower;
    }

    public void setTxPower(int txPower) {
        TxPower = txPower;
    }

    public int getRssi() {
        return Rssi;
    }

    public void setRssi(int rssi) {
        this.Rssi = rssi;
    }

    //  Parcelable методы

    public Beacon(Parcel in){
        Name = in.readString();
        UUID = in.readString();
        Major = in.readInt();
        Minor = in.readInt();
        TxPower = in.readInt();
        Rssi = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Name);
        parcel.writeString(UUID);
        parcel.writeInt(Major);
        parcel.writeInt(Minor);
        parcel.writeInt(TxPower);
        parcel.writeInt(Rssi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Beacon createFromParcel(Parcel in) {
            return new Beacon(in);
        }

        @Override
        public Beacon[] newArray(int size) {
            return new Beacon[size];
        }
    };
}
