package main.ecommerce;

public class Platform {

    private int platid;
    private String platname;
    private String platurl;
    private double platsalesfee;

    public int getPlatid() {
        return platid;
    }

    public void setPlatid(int platid) {
        this.platid = platid;
    }

    public String getPlatname() {
        return platname;
    }

    public void setPlatname(String platname) {
        this.platname = platname;
    }

    public String getPlaturl() {
        return platurl;
    }

    public void setPlaturl(String platurl) {
        this.platurl = platurl;
    }

    public double getPlatsalesfee() {
        return platsalesfee;
    }

    public void setPlatsalesfee(double platsalesfee) {
        this.platsalesfee = platsalesfee;
    }


    @Override
    public String toString() {
        return "Platform{" +
                "platid=" + platid +
                ", platname='" + platname + '\'' +
                ", platurl='" + platurl + '\'' +
                ", platsalesfee=" + platsalesfee +
                '}';
    }



}
