package hr.java.vjezbe.entiteti;

public enum locationTypes {
    PP("park prirode"),
    NP("nacionalni park"),
    PS("park suma"),
    PV("privatno podrucje"),
    OS("ostalo");

    private String locType;

    locationTypes(String locType) {
        this.locType = locType;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }
}
