public class sdd {
    public static void main(String[] args) {

    }

}

/*
public String receipt() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();
            double total = product.getPrice() * count;
            sb.append(product.getName()).append(" - ")
                    .append(product.getPrice()).append(" X ")
                    .append(count).append(" = ")
                    .append(total).append("\n");
        }
        sb.append("Total Due - ").append(totalDue);
        return sb.toString();
    } */
/*
 public static void main(String[] args) {
        Store s = new Store("Migros","www.migros.com.tr");
        Customer c = new Customer("CSE 102");

        ClubCustomer cc = new ClubCustomer("Club CSE 102","05551234567");
        //s.addCustomer(c);
        s.addCustomer(cc);

        Product p = new Product(123456L,"Computer",20,1000.00);
        FoodProduct fp = new FoodProduct(45678L,"Snickers",100,2,250,true,true,true,false);
        CleaningProduct cp = new CleaningProduct(31654L,"Mop",28,99,false,"Multi-room");

        s.addProduct(p);
        s.addProduct(fp);
        s.addProduct(cp);

        System.out.println(s.getInventorySize());
        //System.out.println(s.getProduct("shoes"));
        System.out.println(cp.purchase(2));
        s.getProduct("Computer").addToInventory(3);
        //System.out.println(fp.purchase(200));

        c.addToCart(p,2);
        c.addToCart(s.getProduct("snickers"),-2);
        c.addToCart(s.getProduct("snickers"),1);
        System.out.println("Total due - "+ c.getTotalDue());
        System.out.println("\n\nReceipt:\n"+c.receipt());

        //System.out.println("After paying: "+ c.pay(2000));
        System.out.println("After paying: "+ c.pay(2020));

        System.out.println("Total due - "+c.getTotalDue());
        System.out.println("\n\nReceipt 1:\n"+c.receipt());

        //Customer c2 = s.getCustomer("05551234568");
        cc.addToCart(s.getProduct("snickers"),2);
        cc.addToCart(s.getProduct("snickers"),1);
        System.out.println("\n\nReceipt 2:\n"+ cc.receipt());

        Customer c3 = s.getCustomer("05551234567");
        c3.addToCart(s.getProduct("snickers"),10);
        System.out.println("\n\nReceipt 3:\n"+ cc.receipt());

        System.out.println(((ClubCustomer)c3).pay(26,false));

        c3.addToCart(s.getProduct(31654L),3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3.receipt());
        System.out.println(cc.pay(3*99,false));

        c3.addToCart(s.getProduct(31654L),3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3.receipt());
        //System.out.println(cc.getPoints());
        System.out.println(cc.pay(3*99,true));
    }
 */