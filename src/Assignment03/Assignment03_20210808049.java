package Assignment03;

//@author: İbrahim Utku ADANUR, @since: 25.05.2023
import java.util.HashMap;

public class Assignment03_20210808049 {

}
class Product{
    private Long id;
    private String name;
    private double price;
    public Product(Long id, String name, double price) throws InvalidPriceException {
        if (price < 0) {
            throw new InvalidPriceException(price);
        }
        this.id = id;
        this.name = name;
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
    public String toString() {
        return id+" - "+name+" @ "+price;
    }

    public boolean equals(Object object) {
        if (object instanceof Product) {
            Product other = (Product) object;
            return Math.abs(price - other.price) < 0.001;
        }
        return false;
    }

}
//FP class from assignment_02
class FoodProduct extends Product {

    private int Calories;
    private boolean Dairy;
    private boolean Eggs;
    private boolean Peanuts;
    private boolean Gluten;

    FoodProduct(Long id, String Name, double Price, int Calories,
                boolean Dairy, boolean Eggs, boolean Peanuts, boolean Gluten) {
        super(id, Name, Price);
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
//Cp class from assignment_02
class CleaningProduct extends Product {

    private boolean Liquid;
    private String WhereToUse;
    CleaningProduct(Long id, String Name, double Price, boolean Liquid, String WhereToUse) {
        super(id, Name, Price);
        this.Liquid=Liquid;
        this.WhereToUse=WhereToUse;
    }

    public String getWhereToUse(){return WhereToUse;}
    public void setWhereToUse(String WhereToUse){this.WhereToUse=WhereToUse;}
    public boolean isLiquid(){return Liquid;}
}
class Customer {
    private String name;
    private HashMap<Store, HashMap<Product, Integer>> cart;

    private HashMap<Store, Double> totalDue;

    public Customer(String name) {
        this.name = name;
        this.totalDue = new HashMap<>();
        this.cart = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addToCart(Store store, Product product, int count) {

        //Adds the passed Product and number to the customer cart for the passed Store
        if (!cart.containsKey(store)) {
            cart.put(store, new HashMap<>());
        }

        HashMap<Product, Integer> storeCart = cart.get(store);
        storeCart.put(product, storeCart.getOrDefault(product, 0) + count);

        //If the purchase is not successful (Product is not in the Store or not enough available),
        // this method displays a message to the screen beginning with “ERROR: ”
        try {
            double total = store.purchase(product, count);
            totalDue.put(store, totalDue.getOrDefault(store, 0.0) + total);
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: " + e);
        } catch(ProductNotFoundException e){
            System.out.println("ProductNotFoundException: ID - "+ product.getId()+" Name - "+ product.getName());
            storeCart.remove(product);
        }
    }

    public String receipt(Store store) throws StoreNotFoundException {
        //returns a header including the Store name then each Productin the cart on a separate line in the format below
        // {Product ID } -{Product Name} @{Product Price} X {count} ...
        // {total for Product}Total Due –{Total amount}
        if (cart.containsKey(store)) {
            StringBuilder result = new StringBuilder();
            result.append("Customer receipt for ").append(store.getName()).append("\n\n");

            HashMap<Product, Integer> storeCart = cart.get(store);
            for (HashMap.Entry<Product, Integer> entry : storeCart.entrySet()) {
                Product product = entry.getKey();
                int count = entry.getValue();
                double total = product.getPrice() * count;

                result.append(product.getId()).append(" - ").append(product.getName()).append(" @")
                        .append(product.getPrice()).append(" X ").append(count).append(" ... ").append(total).append("\n");
            }
            result.append("\n---------------------------------------------------------------\n\n");
            result.append("Total Due: ").append(totalDue.get(store)).append("\n");

            return result.toString();
        }
        else {//If the customer does not have a cart for the passed Store, raises a StoreNotFoundException
            throw new StoreNotFoundException(store.getName());
        }
    }

    public double getTotalDue(Store store) {
        //returns the total amount due for the passed Store
        if (cart.containsKey(store)) {
            return totalDue.get(store);
        } else {
            throw new StoreNotFoundException(store.getName());
        }
    }

    public int getPoints(Store store) {
        //returns the total customer points for the passed Store
        if (store.getCustomer(this)) { //I will look at
            return store.getCustomerPoints(this);
        }//If the customer is not in the Customer collection for the passed Store, raises a StoreNotFoundException
        else {
            throw new StoreNotFoundException(store.getName());
        }
    }

    public double pay(Store store, double amount, boolean usePoints) {
        //If the customer does not have a cart for the passed Store, raises a StoreNotFoundException
        if (!cart.containsKey(store)) {
            throw new StoreNotFoundException(store.getName());
        }
        double LocalTotalDue = getTotalDue(store);

        if(usePoints){
            if(store.getCustomer(this)){
                double CCTotalDue = LocalTotalDue;
                CCTotalDue -= store.getCustomerPoints(this) * 0.01;
                if(CCTotalDue <= 0){
                    int newPoints = (int)((store.getCustomerPoints(this)) - (LocalTotalDue *100));
                    store.setCustomerPoints(this,newPoints);
                    LocalTotalDue = 0;
                }else {
                    store.setCustomerPoints(this, 0);
                    LocalTotalDue = CCTotalDue;
                }
            }else{
                //If usePoints is true and the Customer is not in the Customer collection for the Store,
                // use 0 for the points value
                LocalTotalDue -=0;
            }

        }
        if (amount >= LocalTotalDue) {
            double change = amount - LocalTotalDue;
            System.out.println("Thank you for shopping with us. ");
            //If this customer is a club customer, points should be added as was done in Assignment 2
            if(store.getCustomer(this))
                store.setCustomerPoints(this, store.getCustomerPoints(this)+(int)(LocalTotalDue));
            cart.remove(store);
            totalDue.remove(store);

            return change;
        }//If amount is less than total due, raises a InsufficientFundsException
        else
            throw new InsufficientFundsException(LocalTotalDue,amount);
    }
    public String toString() {
        return getName();
    }
}
class Store {
    private String Name;
    private String Website;
    private HashMap<Product, Integer> inventory;
    private HashMap<Customer, Integer> customers;

    Store(String Name, String Website){
        this.Name=Name;
        this.Website=Website;
        this.inventory=new HashMap<>();
        this.customers=new HashMap<>();
    }
    public int getCount() {return inventory.size();}

    public int remaining(Product product){
        if(inventory.containsKey(product))
            return inventory.get(product);
        else
            throw new ProductNotFoundException(product);
    }
    public double purchase(Product product, int amount) throws ProductNotFoundException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        //reduces the count in the inventory and returns the total price based on (amount X price)
        if (inventory.containsKey(product)) {
            int availableQuantity = inventory.get(product);
            if (amount <= availableQuantity) {
                inventory.put(product, availableQuantity - amount);
                return product.getPrice() * amount;
            }else {//if amount is negative or greater than count, do not change count and raise InvalidAmountException
                throw new InvalidAmountException(amount, availableQuantity);
            }
        } else {//if the Product is not found in the Collection, do not change count and raises a ProductNotFoundException
            throw new ProductNotFoundException(product.getName());
        }
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        this.Website = website;
    }

    public void addCustomer(Customer customer) {
        customers.put(customer, 0);
    }

    public int getProductCount(Product product) {
        //returns the count of this Product in the store inventory
        for (HashMap.Entry<Product, Integer> entry : inventory.entrySet()) {
            if (entry.getKey().equals(product)) {
                return entry.getValue();
            }
        }
        //if the Product is not found in the collection, raises a ProductNotFoundException
        throw new ProductNotFoundException("Product not found: " + product.getName());
    }

    public int getCustomerPoints(Customer customer) {
        //returns the points for the customer passed
        if (customers.containsKey(customer)) {
            return customers.get(customer);
        }//if the customer is not found in the collection, raises aCustomerNotFoundException
        else {
            throw new CustomerNotFoundException("Customer not found: " + customer.getName());
        }
    }
    public void setCustomerPoints(Customer customer, int points){
        if (customers.containsKey(customer)) {
            customers.put(customer,points);
        } else {
            throw new CustomerNotFoundException("Customer not found: " + customer.getName());
        }
    }
    public boolean getCustomer(Customer customer) {
        //Helper method for myself
        return customers.containsKey(customer);
    }

    public void removeProduct(Product product) {
        //removes the passed Product from the Collection
        for (HashMap.Entry<Product, Integer> entry : inventory.entrySet()) {
            if (entry.getKey().equals(product)) {
                inventory.remove(product);
            }
        }
        //if the Product is not found in the Collection, raises a ProductNotFoundException
        throw new ProductNotFoundException("Product not found: " + product.getName());
    }

    public void addToInventory(Product product, int amount) throws InvalidAmountException {
        //raises InvalidAmountException if amount is negative
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }
        //If product already exists, adds amount to the inventory
        if (inventory.containsKey(product)) {
            int currentAmount = inventory.get(product);
            inventory.put(product, currentAmount + amount);
        } //If product is not in the Collection, adds it using the amount
        else {
            inventory.put(product, amount);
        }
    }

}

class StoreNotFoundException extends IllegalArgumentException {
    private String name;

    public StoreNotFoundException(String name) {
        this.name = name;
    }

    public String toString() {
        return "StoreNotFoundException: " + name;
    }
}
class CustomerNotFoundException extends IllegalArgumentException {
    private Customer customer;

    public CustomerNotFoundException(String message) {

    }

    public String toString() {
        return "CustomerNotFoundException: Name - " + customer.getName();
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
    private Product product;
    public ProductNotFoundException(Product product) {

    }
    public ProductNotFoundException(String message) {

    }
    public String toString() {
        return "ProductNotFoundException: ID - "+ product.getId()+" Name - "+ product.getName();
    }
}
