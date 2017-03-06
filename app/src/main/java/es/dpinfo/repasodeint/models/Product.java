package es.dpinfo.repasodeint.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dprimenko on 5/03/17.
 */
public class Product implements Parcelable {

    private int mId;
    private String mName;
    private String mDateExpiry;
    private int mIdCategory;

    protected Product(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDateExpiry = in.readString();
        mIdCategory = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDateExpiry);
        dest.writeInt(mIdCategory);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDateExpiry() {
        return mDateExpiry;
    }

    public void setmDateExpiry(String mDateExpiry) {
        this.mDateExpiry = mDateExpiry;
    }

    public int getmIdCategory() {
        return mIdCategory;
    }

    public void setmIdCategory(int mIdCategory) {
        this.mIdCategory = mIdCategory;
    }

    public Product() {
    }

    public Product(int mIdCategory, int mId, String mName, String mDateExpiry) {
        this.mIdCategory = mIdCategory;
        this.mId = mId;
        this.mName = mName;
        this.mDateExpiry = mDateExpiry;
    }

    @Override
    public String toString() {
        return "{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDateExpiry='" + mDateExpiry + '\'' +
                ", mIdCategory=" + mIdCategory +
                '}';
    }
}
