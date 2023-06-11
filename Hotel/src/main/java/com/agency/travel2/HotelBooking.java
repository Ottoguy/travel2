package com.agency.travel2;

public class HotelBooking {
    public enum Cities {
        PRAGUE(0),
        PODEBRAD(1),
        BRATISLAVA(2),
        BERLIN(3),
        STOCKHOLM(4),
        LUND(5),
        TBILISI(6),
        USHGULI(7);

        private final int value;

        Cities(final int newValue) {
            value = newValue;
        }

        public int getValue(){return value;}

    }
    String[] cities = {
            "Prague", "Podebrad", "Bratislava", "Berlin",
            "Stockholm", "Lund", "Tbilisi", "Ushguli"
    };
//------------------------------------------
    private int Id;
    private String checkInDate;
    private  String checkOutDate;
    private int numberOfPersons;
    private String city;
    private  String promoCodeForTaxi;
//------------------------------------------
    public int getId() {return this.Id;}
    public void setId(int id) {this.Id = id;} // every booking has a unique ID

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getCity() {
        return city;
    }

//    public void setCity(Cities city) {
//        this.city = city.ordinal();
//    }
    public void setCity(int city)
    {
        this.city = cities[city];
    }

    public String getPromoCodeForTaxi() {
        return promoCodeForTaxi;
    }

    public void setPromoCodeForTaxi(String promoCodeForTaxi) {
        this.promoCodeForTaxi = promoCodeForTaxi;
    }

    public HotelBooking(){

    }


}
