import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ex4_20210808049 {
    
}
class Computer {
    protected CPU cpu;
    protected RAM ram;

    Computer(CPU cpu, RAM ram){
        this.cpu=cpu;
        this.ram=ram;
    }
    public void run() {
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            sum += ram.getValue(i, i);
        }
        int result = cpu.compute(sum,0);
        ram.setValue(0, 0, result);
    }
    public String toString() {
        return "Computer: " + cpu + " " + ram;
    }
}
class Laptop extends Computer {
    private int milliAmp;
    private int battery;

    public Laptop(CPU cpu, RAM ram, int milliAmp) {
        super(cpu, ram);
        this.milliAmp = milliAmp;
        this.battery = (int) (0.3 * milliAmp);
    }

    public int batteryPercentage() {
        return (battery * 100) / milliAmp;
    }

    public void charge() {
        while (batteryPercentage() < 90) {
            battery += (int) (0.02 * milliAmp);
        }
    }

    @Override
    public void run() {
        if (batteryPercentage() <= 5) {
            charge();
        }
        else if (batteryPercentage() > 5) {
            battery -= (int) (0.03 * milliAmp);
            //super.run();
        }
    }

    @Override
    public String toString() {return super.toString() + " " + battery;}
}
class Desktop extends Computer {
    private ArrayList<String> peripherals;

    Desktop(CPU cpu,RAM ram, String... peripherals){
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }
    public void run(){
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            for (int j = 0; j < ram.getCapacity(); j++) {
                sum += cpu.compute(0, ram.getValue(i,j));
            }
        }
        ram.setValue(0, 0, sum);
    }
    public void plugIn(String peripheral) {
        peripherals.add(peripheral);
    }
    public String plugOut() {
        if (peripherals.size() > 0) {
            return peripherals.remove(peripherals.size() - 1);
        } else {
            return null;
        }
    }

    public String plugOut(int index) {
        if (index >= 0 && index < peripherals.size()) {
            return peripherals.remove(index);
        } else {
            return null;
        }
    }
    public String toString() {
        return super.toString() + " " + String.join(" ", peripherals);
    }
}
class CPU {
    private String name;
    private double clock;

    CPU(String name, double clock){
        this.name = name;
        this.clock = clock;
    }

    public String getName() {return name;}

    public double getClock() {return clock;}

    public int compute(int a, int b){
        return a+b;
    }
    public String toString(){
        return "CPU: "+getName()+" "+getClock()+"Ghz";
    }
}
class RAM {
    private String type;
    private int capacity;
    private int[][] memory;

    RAM(String type, int capacity){
        this.type = type;
        this.capacity = capacity;
        initMemory();
    }

    public String getType() {return type;}
    public int getCapacity() {return capacity;}

    private void initMemory() {
        memory = new int[capacity][capacity];
        Random rand = new Random();
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                memory[i][j] = rand.nextInt(11);
            }
        }
    }
    private boolean check(int i, int j) {
        return i >= 0 && i < capacity && j >= 0 && j < capacity;
    }
    public int getValue(int i, int j) {
        if (!check(i, j)) {
            return -1;
        }
        return memory[i][j];
    }
    public void setValue(int i, int j, int value) {
        if (check(i, j)) {
            memory[i][j] = value;
        }
    }
    public String toString() {
        return "RAM: " + type + " " + capacity + "GB";
    }
}