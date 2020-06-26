package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johncordeiro on 10/10/15.
 */
public class ProtectionNetwork implements Parcelable {

    private Integer id;

    private String name;

    private NetworkType type;

    private Position position;

    private String zipcode;

    private String neighborhood;

    private String address;

    private String city;

    private State state;

    private String contact;

    private String phone1;

    private String phone2;

    private String email;

    private String schedule;

    private List<Theme> themes;

    private List<OperatingDay> operatingdays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NetworkType getType() {
        return type;
    }

    public void setType(NetworkType type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public List<OperatingDay> getOperatingdays() {
        return operatingdays;
    }

    public void setOperatingdays(List<OperatingDay> operatingdays) {
        this.operatingdays = operatingdays;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.type, 0);
        dest.writeParcelable(this.position, 0);
        dest.writeString(this.zipcode);
        dest.writeString(this.neighborhood);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeParcelable(this.state, 0);
        dest.writeString(this.contact);
        dest.writeString(this.phone1);
        dest.writeString(this.phone2);
        dest.writeString(this.email);
        dest.writeString(this.schedule);
        dest.writeTypedList(themes);
        dest.writeList(this.operatingdays);
    }

    public ProtectionNetwork() {
    }

    protected ProtectionNetwork(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.type = in.readParcelable(NetworkType.class.getClassLoader());
        this.position = in.readParcelable(Position.class.getClassLoader());
        this.zipcode = in.readString();
        this.neighborhood = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.state = in.readParcelable(State.class.getClassLoader());
        this.contact = in.readString();
        this.phone1 = in.readString();
        this.phone2 = in.readString();
        this.email = in.readString();
        this.schedule = in.readString();
        this.themes = in.createTypedArrayList(Theme.CREATOR);
        this.operatingdays = new ArrayList<OperatingDay>();
        in.readList(this.operatingdays, List.class.getClassLoader());
    }

    public static final Creator<ProtectionNetwork> CREATOR = new Creator<ProtectionNetwork>() {
        public ProtectionNetwork createFromParcel(Parcel source) {
            return new ProtectionNetwork(source);
        }

        public ProtectionNetwork[] newArray(int size) {
            return new ProtectionNetwork[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProtectionNetwork that = (ProtectionNetwork) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
