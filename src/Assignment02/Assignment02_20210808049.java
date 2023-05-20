package Assignment02;

//Author: İbrahim Utku ADANUR Date: 14.04.2023
import java.util.ArrayList;
import java.util.HashMap;

public class Assignment02_20210808049 {
    
}
class Product {
    private Long id;
    private String name;
    private int quantity;
    private double price;

    public Product(Long id, String name, int quantity, double price) throws InvalidPriceException, InvalidAmountException {
        if (price < 0) {
            throw new InvalidPriceException(price);
        }
        else if (quantity < 0) {
            throw new InvalidAmountException(quantity);
        }
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public double getPrice(){return price;}
    public void setPrice(double price) throws InvalidPriceException {
        if (price < 0) {
            throw new InvalidPriceException(price);
        }
        this.price = price;
    }
    public int remaining(){
        return quantity;
    }
    public int addToInventory(int amount) throws InvalidAmountException {
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }else{
            quantity += amount;
            return quantity;
        }
    }
    public double purchase(int amount) throws InvalidAmountException {
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }
        if (amount > quantity) {
            throw new InvalidAmountException(amount, quantity);
        }
        quantity -= amount;
        return amount * price;
    }
    public String toString() {
        return "Product " + getName() + " has " + quantity + " remaining";
    }

    public boolean equals(Object object) {
        if (object instanceof Product) {
            Product other = (Product) object;
            return Math.abs(price - other.price) < 0.001;
        }
        return false;
    }

}
class FoodProduct extends Product {

    private int Calories;
    private boolean Dairy;
    private boolean Eggs;
    private boolean Peanuts;
    private boolean Gluten;

    FoodProduct(Long id, String Name, int Quantity, double Price, int Calories,
                boolean Dairy, boolean Eggs, boolean Peanuts, boolean Gluten) {
        super(id, Name, Quantity, Price);
        if (Calories < 0) {
            throw new InvalidAmountException(Calories);
        }
        this.Calories= Calories;
        this.Dairy=Dairy;
        this.Eggs=Eggs;
        this.Peanuts=Peanuts;
        this.Gluten=Gluten;
    }
    public int getCalories(){return Calories;}
    public void setCalories(int Calories){
        if (Calories < 0) {
            throw new InvalidAmountException(Calories);
        }
        this.Calories = Calories;
    }

    public boolean containsDairy(){return Dairy;}
    public boolean containsEggs(){return Eggs;}
    public boolean containsPeanuts(){return Peanuts;}
    public boolean containsGluten(){return Gluten;}

}
class CleaningProduct extends Product {

    private boolean Liquid;
    private String WhereToUse;
    CleaningProduct(Long id, String Name, int Quantity, double Price, boolean Liquid, String WhereToUse) {
        super(id, Name, Quantity, Price);
        this.Liquid=Liquid;
        this.WhereToUse=WhereToUse;
    }

    public String getWhereToUse(){return WhereToUse;}
    public void setWhereToUse(String WhereToUse){this.WhereToUse=WhereToUse;}
    public boolean isLiquid(){return Liquid;}
}
class Customer {
    private String name;
    private HashMap<Product, Integer> cart;
    private double totalDue;

    public Customer(String name) {
        this.name = name;
        this.cart = new HashMap<>();
        this.totalDue = 0.0;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public void addToCart(Product product, int count) {
        try {
            double total = product.purchase(count);
            if (cart.containsKey(product)) {
                int existingCount = cart.get(product);
                cart.put(product, existingCount + count); //Eğer birden fazla aynı ürün girilirse toplamı cart'a yazıyp
            } else {
                cart.put(product, count);
            }
            totalDue += total;
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: " + e);
        }
    }

    public String receipt() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();
            double total = product.getPrice() * count;
            result.append(product.getName()).append(" - ").append(product.getPrice()).
                    append(" X ").append(count).append(" = ").append(total).append("\n");
        }
        result.append("\n--------------------------------------");
        result.append("\n\n");
        result.append("Total Due - ").append(totalDue);
        return result.toString();
        //I made it with String object but Intellij offered me using StringBuilder
    }

    public double getTotalDue() {return totalDue;}

    public double pay(double amount) {
        if (amount >= totalDue) {
            double change = amount - totalDue;
            System.out.println("Thank you for shopping with us.");
            cart.clear();
            totalDue = 0.0;
            return change;
        } else {
            throw new InsufficientFundsException(totalDue, amount);
        }
    }
    public String toString(){
        return getName();
    }
}
class ClubCustomer extends Customer {

    private String Phone;
    private int Points;


    ClubCustomer(String Name, String Phone) {
        super(Name);
        this.Phone = Phone;
        this.Points = 0;
    }
    public String getPhone() {return Phone;}
    public void setPhone(String phone) {this.Phone = phone;}

    public int getPoints() {return Points;}
    public void addPoints(int Points) {
        if (Points > 0) {
            this.Points += Points;
        }
    }
    public double pay(double amount, boolean usePoints){
        double total = getTotalDue();
        if(usePoints && Points > 0) {
            total -= Points * 0.01;
            if (total <= 0) {
                Points -= (int) (getTotalDue() * 100);
                total = 0;
            }else
                Points=0;
        }
        double change = super.pay(amount);
        change = amount - total;
        if(total > 0)
            Points += (int)(total);
        return change;
    }
    public String toString() {
        return super.toString() + " has " + Points + " points";
    }

}
class Store{
    private String Name;
    private String Website;
    private ArrayList<Product> inventory;
    private ArrayList<ClubCustomer> customers;

    public Store(String Name, String Website){
        this.Name=Name;
        this.Website=Website;
        this.inventory = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public String getName(){return Name;}
    public void setName(String Name){this.Name=Name;}

    public String getWebsite() {return Website;}
    public void setWebsite(String website) {this.Website = website;}
    public int getInventorySize() {return inventory.size();}

    public void addProduct(Product product){
        inventory.add(product);
    }
    public Product getProduct(Long ID) throws ProductNotFoundException{
        for(Product p: inventory){
            if(p.getId().equals(ID))
                return p;
        }
        throw new ProductNotFoundException(ID);
    }
    public Product getProduct(String name) throws ProductNotFoundException{
        String newName = name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
        for(Product p: inventory){
            if(p.getName().equals(newName))
                return p;
        }
        throw new ProductNotFoundException(0L);
    }
    public void addCustomer(ClubCustomer customer){
        customers.add(customer);
    }
    public ClubCustomer getCustomer(String phone){
        for(ClubCustomer cc: customers){
            if(cc.getPhone().equals(phone))
                return cc;
        }
        throw new CustomerNotFoundException(phone);
    }
    public void removeProduct(Long ID){
        for(Product p: inventory){
            if(p.getId().equals(ID))
                inventory.remove(p);
                return;
        }
        throw new ProductNotFoundException(ID);
    }
    public void removeProduct(String name){
        for(Product p: inventory){
            if(p.getName().equals(name))
                inventory.remove(p);
                return;
        }
        throw new ProductNotFoundException(name);
    }
    public void removeCustomer(String phone){
        for(ClubCustomer cc: customers){
            if(cc.getPhone().equals(phone))
                customers.remove(cc);
                return;
        }
        throw new CustomerNotFoundException(phone);
    }
}
class CustomerNotFoundException extends IllegalArgumentException {
    private final String phone;

    public CustomerNotFoundException(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "CustomerNotFoundException: " + phone;
    }
}

class InsufficientFundsException extends RuntimeException {
    private final double total;
    private final double payment;

    public InsufficientFundsException(double total, double payment) {
        this.total = total;
        this.payment = payment;
    }

    public String toString() {
        return "InsufficientFundsException: " + total + " due, but only " + payment + " given";
    }
}

class InvalidAmountException extends RuntimeException {
    private final int amount;
    private final int quantity;

    public InvalidAmountException(int amount) {
        this.amount = amount;
        this.quantity = 0;
    }

    public InvalidAmountException(int amount, int quantity) {
        this.amount = amount;
        this.quantity = quantity;
    }

    public String toString() {
        if (quantity == 0) {
            return "InvalidAmountException: " + amount;
        } else {
            return "InvalidAmountException: " + amount + " was requested, but only " + quantity + " remaining";
        }
    }
}

class InvalidPriceException extends RuntimeException {
    private final double price;

    public InvalidPriceException(double price) {
        this.price = price;
    }

    public String toString() {
        return "InvalidPriceException: " + price;
    }
}

class ProductNotFoundException extends IllegalArgumentException {
    private final Long id;
    private final String name;

    public ProductNotFoundException(Long id) {
        this.id = id;
        this.name = null;
    }

    public ProductNotFoundException(String name) {
        this.id = null;
        this.name = name;
    }

    public String toString() {
        if (name == null) {
            return "ProductNotFoundException: ID - " + id;
        } else {
            return "ProductNotFoundException: Name - " + name;
        }
    }
}
