package Ex1;

public class Ex1_20210808049 {
    public static void main(String[] args) {
        Stock stock = new Stock("ORCL","Oracle Corporation");
        stock.previousClosingPrice =34.5;
        stock.currentPrice = 34.35;
        System.out.printf("The price change percentage for %s is %.2f%%.%n", stock.symbol, stock.getChangePercent());
    }
}
class Stock{
    //Data field//
    String symbol;
    String name;
    double previousClosingPrice;
    double currentPrice;

    //Constructor
    
    Stock(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
    }
    //Methods
    double getChangePercent(){
        return ((currentPrice-previousClosingPrice)/previousClosingPrice)*100;
    }

}
class Fan{
    final int SLOW = 1;
    final int MEDIUM = 2;
    final int FAST = 3;
    private int speed = SLOW;
    private boolean on = false;
    private double radius = 5;
    String color = "blue";

    Fan(){

    }
    Fan(double radius,String color){
        this.radius = radius;
        this.color = color;
    }

    public String toString() {
        if (on) {
            return String.format("Ex1_20210808049.Fan speed: %d, color: %s, radius: %.1f", speed, color, radius);
        } else {
            return String.format("Ex1_20210808049.Fan color: %s, radius: %.1f, fan is off", color, radius);
        }
    }

    int getSpeed() {
        return speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    boolean isOn() {
        return on;
    }

    void setOn(boolean on) {
        this.on = on;
    }
    double getRadius() {
        return radius;
    }

    void setRadius(double radius) {
        this.radius = radius;
    }

    String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }
}
