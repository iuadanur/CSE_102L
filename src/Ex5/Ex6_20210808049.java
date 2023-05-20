package Ex5;

import java.util.ArrayList;
import java.util.Date;

public class Ex6_20210808049 {
}
abstract class Product implements Comparable<Product>{
    private String name;
    private double price;


    Product(String name, double price){
        this.name=name;
        this.price=price;
    }
    //getters
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    //Compare method to order the products
    @Override
    public int compareTo(Product o) {
        return Double.compare(price, o.price);
    }
    //I couldn't understand what exactly you mean I hope it is right
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", price=" + price + "]";
    }
}
abstract class Book extends Product{
    private String author;
    private int pageCount;
    Book(String name, double price, String author,int pageCount){
        super(name,price);
        this.author = author;
        this.pageCount = pageCount;
    }
    //Getters
    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }
}
class ReadingBook extends Book {
    private String genre;

    public ReadingBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }
}

class ColoringBook extends Book implements Colorable {
    private String color;

    public ColoringBook(String name, double price, String author, int pageCount) {
        super(name, price, author, pageCount);
    }

    public String getColor() {
        return color;
    }
    //I couldn't understand but I think you meant that
    @Override
    public void paint(String color){
        this.color=color;
    }
}
class ToyHorse extends Product implements Rideable {
    public ToyHorse(String name, double price) {
        super(name, price);
    }

    @Override
    public void ride() {System.out.println("Riding a toy horse");}
}
class Bicycle extends Product implements Colorable, Rideable {
    private String color;

    public Bicycle(String name, double price) {
        super(name, price);
    }

    public String getColor() {return color;}
    @Override
    public void paint(String color) {this.color = color;}
    @Override
    public void ride() {System.out.println("Riding a bicycle");}
}
class User{
    //Attributes
    private String username;
    private String email;
    private PaymentMethod payment;
    private ArrayList<Product> cart;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.cart = new ArrayList<>();
    }
    public String getUsername() {return username;}
    public String getEmail() {
        return email;
    }
    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }
    public Product getProduct(int index) {
        return cart.get(index);
    }

    public void removeProduct(int index) {
        cart.remove(index);
    }
    public void addProduct(Product product) {
        cart.add(product);
    }

    public void purchase() {
        double total = 0.0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        if (payment != null && payment.pay(total)) {
            cart.clear();
            System.out.println("Purchase successful");
        } else {
            System.out.println("Purchase failed. Please check your payment information");
        }
    }
}
class CreditCard implements PaymentMethod{
    private long cardNumber;
    private String cardHolderName;
    private Date expirationDate;
    private int cvv;

    public CreditCard(long cardNumber, String cardHolderName, Date expirationDate, int cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }
    @Override
    public boolean pay(double amount){
        System.out.println("Paying " +amount+ " with credit card");
        return true;
    }
}
class PayPal implements PaymentMethod {
    private String username;
    private String password;

    public PayPal(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public boolean pay(double amount){
        System.out.println("Paying " + amount + " with PayPal.");
        return true;
    }
}
interface Colorable {
    void paint(String color);
}
interface Rideable {
    void ride();
}
interface PaymentMethod {
    boolean pay(double amount);
}
