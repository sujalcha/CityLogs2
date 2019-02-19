package app.cl.CityLogs.helper;


public class CityLogs {


    String cityname, time, contact, invoice, destination, logType, createdDate, updatedDate;

    public CityLogs() {
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String name) {
        this.cityname = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String r) {
        this.time = r;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String s) {
        this.contact = s;
    }

    public String getInvoice() {
        return this.invoice;
    }

    public void setInvoice(String f) {
        this.invoice = f;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String sb) {
        this.destination = sb;
    }


    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString(){
        return this.cityname+"  "+this.time+"  "+this.contact+"  "+this.invoice+"  "+this.destination;
    }

}
