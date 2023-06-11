package Ex9;

public class Ex9_20210808049 {

}

interface Sellable{
    String getName();
    double getPrice();
}
interface Package <T>{
    T extract();
    boolean pack(T item);
    boolean isEmpty();
    double getPriority();
}
interface Wrappable extends Sellable {

}
interface Common <T>{
    boolean isEmpty();
    T peek();
    int size();
}
interface Stack<T> extends Common<T> {
    boolean push(T item);
    T pop();
}
interface Node<T> {
    int DEFAULT_CAPACITY=2;
    void setNext(T item);
    T getNext();
    double getPriority();
}

interface PriorityQueue<T> extends Common<T> {
    int FLEET_CAPACITY= 3;
    boolean enqueue(T item);
    T dequeue();
}
abstract class Product implements Sellable {
    private String name;
    private double price;

    Product(String name, double price){
        this.name=name;
        this.price=price;
    }
    public String toString(){
        return getClass().getSimpleName()+"("+name+","+price+")";
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}
class Mirror extends Product {
    private int width;
    private int height;

    Mirror(int width,int height){
        super("mirror",2);
        this.width=width;
        this.height=height;
    }
    public int getArea(){
        return width * height;
    }
    public <T> T reflect(T item){
        System.out.println(item);
        return item;
    }
}
class Paper extends Product implements Wrappable {
    private String note;

    Paper(String note){
        super("A4",3);
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
class Matroschka <T extends Wrappable> extends Product implements Wrappable, Package<T> {
    private T item;

    public Matroschka(T item) {
        super("Doll", 5 + item.getPrice());
        this.item = item;
    }
    @Override
    public T extract() {
        T extractedItem = item;
        item = null;
        return extractedItem;
    }
    @Override
    public boolean pack(T item){
        if(isEmpty()){
            this.item= item;
            return true;
        }
        return false;
    }
    @Override
    public boolean isEmpty() {
        return item == null;
    }

    public String toString() { 
        return super.toString() + "{" + item + "]";
    }
    public double getPriority(){
        throw new UnsupportedOperationException();
    }
}
class Box <T extends Sellable> implements Package<T> {
    private T item;
    private boolean seal;
    private int distanceToAddress;

    Box() {
        this.item = null;
        this.seal = false;
    }

    Box(T item,int distanceToAddress) {
        this.item = item;
        this.seal = true;
        this.distanceToAddress=distanceToAddress;
    }

    @Override
    public T extract() {
        T extractedItem = item;
        seal = false;
        if (!isEmpty()) {
            item = null;
            return extractedItem;
        }else
            return null;
    }

    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }
        return false;
    }
    public double getPriority(){
        return distanceToAddress/item.getPrice();
    }

    public boolean isEmpty() {
        return item == null;
    }

    public String toString() {
        return getClass().getSimpleName() + "{" + item + "} Seal: " + seal;
    }
}
class Container implements Stack<Box>, Node<Container>, Comparable<Container>{
    private Box[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    Container() {
        boxes = new Box[DEFAULT_CAPACITY];
        top = -1;
        next = null;
        priority = 0;
    }
    public boolean push(Box item){
        if (top == boxes.length - 1)
            return false;

        boxes[++top] = item;
        size++;
        priority += item.getPriority();
        return true;
    }
    public Box peek() {

        return boxes[top];
    }
    public void setNext(Container container) {
        this.next = container;
    }

    public Container getNext() {
        return next;
    }

    public Box pop() {

        Box poppedBox = boxes[top];
        boxes[top--] = null;
        size--;
        priority -= poppedBox.getPriority();
        return poppedBox;
    }
    public boolean isEmpty() {
        return top == -1;
    }
    public int size() {
        return size;
    }
    public int compareTo(Container other) {
        if(this.priority < other.getPriority())
            return 1;
        else if(this.priority > other.getPriority())
            return -1;
        else
            return 0;
    }

    public double getPriority() {
        return priority;
    }
    public String toString() {
        return "Container with priority: " + priority;
    }
}
class CargoFleet implements PriorityQueue<Container>{
    private Container head;
    private int size;

    CargoFleet() {
        head = null;
        size = 0;
    }
    public boolean enqueue(Container item){
        if(FLEET_CAPACITY==size())
            return false;
        if (isEmpty()) {
            head = item;
            size++;
            return true;
        }
        if (head.compareTo(item) < 0) {
            item.setNext(head);
            head = item;
            size++;
            return true;
        }
        if(head.getNext()==null){
            head.setNext(item);
            size++;
            return true;
        }
        if(head.getNext().compareTo(item)<0){
            item.setNext(head.getNext());
            head.setNext(item);
            size++;
            return true;
        }
        head.getNext().setNext(item);
        size++;
        return true;

    }
    public Container dequeue() {

        Container removedContainer = head;
        head = head.getNext();
        size--;

        return removedContainer;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public Container peek() {
        return head;
    }

    public int size() {
        return size;
    }
}
class CargoCompany{
    private Container stack;
    private CargoFleet queue;

    public CargoCompany() {
        stack = new Container();
        queue = new CargoFleet();
    }
    public <T extends Box>void add(T box){
        if (!stack.push(box)) {
            if(queue.enqueue(stack)){
                stack = new Container();
                add(box);
            }else{
                ship(queue);
            }
        }
    }
    private void ship(CargoFleet fleet) {
        while (!fleet.isEmpty()) {
            Container container = fleet.dequeue();
            empty(container);
        }
    }
    private void empty(Container container) {
        while (!container.isEmpty()) {
            Box box = container.pop();
            Sellable content = deliver(box);
            System.out.println("Delivered: " + content);
        }
    }
    private <T extends Box>Sellable deliver(T box) {
        return box.extract();
    }
}