package com.brim.Pojo;

/**
 * Created by ritam on 18/09/17.
 */

public class AddressDetailsSetGet {


    String AddressId,StreetAddress,City,Province,Country,PostalCode,Lat,Long;

    public AddressDetailsSetGet(String addressId, String streetAddress, String city, String province, String country, String postalCode, String lat, String aLong) {
        AddressId = addressId;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        Country = country;
        PostalCode = postalCode;
        Lat = lat;
        Long = aLong;
    }

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }
}
