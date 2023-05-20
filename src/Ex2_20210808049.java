import java.util.Date;

public class Ex2_20210808049 {

}
class Ticket{
    private City from;
    private City to;
    private Date date;
    private int seat;

    Ticket(City from, City to, Date date,int seat){
        this.from=from;
        this.to=to;
        this.date=date;
        this.seat=seat;
    }
    public Ticket(City from, City to, int seat) {
        this.from = from;
        this.to = to;
        this.date = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        this.seat = seat;
    }
    public City getFrom() {return from;}

    public City getTo() {return to;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public int getSeat() {return seat;}

    public void setSeat(int seat) {this.seat = seat;}
        }
class City{
    private String postalCode;
    private String name;

    City(String postalCode,String name){
        this.postalCode=postalCode;
        this.name=name;
    }
    public String getPostalCode() {return postalCode;}

    public String getName() {return name;}
}
class Person{
    private String name;
    private String surname;
    private String phoneNumber;

    Person(String name,String surname,String phoneNumber){
        this.name=name;
        this.surname=surname;
        this.phoneNumber=phoneNumber;
    }
    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
}