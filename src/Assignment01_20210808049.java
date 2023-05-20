//İbrahim Utku ADANUR //Student Number:20210808049 //Date: 22.03.2023
import java.util.ArrayList;

public class Assignment01_20210808049 {

}
class Product{
    //Attributes
    private String Id;
    private String Name;
    private int Quantity;
    private double Price;

    //Methods
    Product(String Id,String Name,int Quantity,double Price) {
        this.Id=Id;
        this.Name=Name;
        this.Quantity=Quantity;
        this.Price=Price;
    }

    public String getId(){return Id;}
    public void setId(String Id){this.Id=Id;}

    public String getName(){return Name;}
    public void setName(String Name){this.Name=Name;}

    public double getPrice(){return Price;}
    public void setPrice(double Price){this.Price=Price;}

    public int getQuantity(){return Quantity;}
    public void setQuantity(int Quantity){this.Quantity=Quantity;}

    public int remaining(){
        return getQuantity();
    }
    public int addToInventory(int amount) {
        if (amount < 0) {
            return getQuantity(); // If amount is negative do nothing
        } else {
            int newCount = getQuantity() + amount; // Increase count by amount
            setQuantity(newCount); // Update quantity
            return newCount;
        }
    }
    public double purchase(int amount) {
        if (amount < 0 || amount > getQuantity()) {
            return 0; //If amount is negative or asked amount is greater than the quantity do nothing and return 0
        } else {
            double totalPrice = amount * getPrice();
            setQuantity(getQuantity() - amount);
            return totalPrice;
        }
    }
    @Override
    public String toString() {
        return "Product " + getName() + " has " + getQuantity() + " remaining";
    }

    public boolean equals(Object o) {
        if (o instanceof Product) {
            Product other = (Product) o;
            return Math.abs(Price - other.Price) < 0.001;
        }
        return false;
    }

}
class FoodProduct extends Product{

    private int Calories;
    private boolean Dairy;
    private boolean Eggs;
    private boolean Peanuts;
    private boolean Gluten;

    FoodProduct(String id, String Name, int Quantity, double Price, int Calories,
                boolean Dairy, boolean Eggs, boolean Peanuts, boolean Gluten) {
        super(id, Name, Quantity, Price);
        this.Calories= Calories;
        this.Dairy=Dairy;
        this.Eggs=Eggs;
        this.Peanuts=Peanuts;
        this.Gluten=Gluten;

    }
    public int getCalories(){return Calories;}
    public void setCalories(int Calories){this.Calories=Calories;}

    public boolean containsDairy(){return Dairy;}
    public boolean containsEggs(){return Eggs;}
    public boolean containsPeanuts(){return Peanuts;}
    public boolean containsGluten(){return Gluten;}

}
class CleaningProduct extends Product{

    private boolean Liquid;
    private String WhereToUse;
    CleaningProduct(String id, String Name, int Quantity, double Price, boolean Liquid, String WhereToUse) {
        super(id, Name, Quantity, Price);
        this.Liquid=Liquid;
        this.WhereToUse=WhereToUse;
    }

    public String getWhereToUse(){return WhereToUse;}
    public void setWhereToUse(String WhereToUse){this.WhereToUse=WhereToUse;}
    public boolean isLiquid(){return Liquid;}
}
class Customer{
    private String Name;

    Customer(String Name){
        this.Name=Name;
    }
    public String getName(){return Name;}
    public void setName(String Name){this.Name=Name;}

    public String toString(){
        return getName();
    }
}
class ClubCustomer extends Customer{

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
    public String toString() {
        return super.toString() + " has " + Points + " points";
    }

}
class Store{
    private String Name;
    private String Website;
    private ArrayList<Product> inventory; //I found make sense using arraylist

    public Store(String Name, String Website){
        this.Name=Name;
        this.Website=Website;
        this.inventory = new ArrayList<>();
    }

    public String getName(){return Name;}
    public void setName(String Name){this.Name=Name;}

    public String getWebsite() {return Website;}
    public void setWebsite(String website) {this.Website = website;}

    public int getInventorySize() {return inventory.size();}

    public void addProduct(Product product, int index) {
        if (index < 0 || index > inventory.size()) {
            inventory.add(product);
        } else {
            inventory.add(index, product);
        }
    }
    public void addProduct(Product product) {
        inventory.add(product);
    }
    public Product getProduct(int index){
        if (index < 0 || index > inventory.size())
            return null;
        else
            return inventory.get(index);
    }
    public int getProductIndex(Product p) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).equals(p)) {
                return i;
            }
        }
        return -1;
    }
}
