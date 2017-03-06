package es.dpinfo.repasodeint.models;

/**
 * Created by dprimenko on 5/03/17.
 */
public class Category {

    private int mId;
    private String mName;

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

    public Category() {
    }

    public Category(int mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }
}
