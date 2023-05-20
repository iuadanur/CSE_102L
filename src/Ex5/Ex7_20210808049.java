package Ex5;

import java.util.*;

public class Ex7_20210808049 {
}
interface Damageable{
    void takeDamage(int damage);
    void takeHealing(int healing);
    boolean isAlive();
}
interface Caster{
    void castSpell(Damageable target);
    void learnSpell(Spell spell);
}
interface Useable{
    int use();
}
interface Combat extends Damageable{
    public void attack(Damageable target);
    public void lootWeapon(Weapon weapon);
}
class Spell implements Useable{
    //attributes
    private int minHeal;
    private int maxHeal;
    private String name;

    Spell(String name,int minHeal,int maxHeal){
        this.minHeal=minHeal;
        this.maxHeal=maxHeal;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //a method give us a random number between minHeal and maxHeal
    private int heal(){
        return (int) (Math.random() * (maxHeal - minHeal + 1)) + minHeal;
    }

    @Override
    public int use() {
        return heal();
    }
}
class Weapon implements Useable{
    //attributes
    private int minDamage;
    private int maxDamage;
    private String name;

    public Weapon(String name, int minDamage, int maxDamage) {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //a method give us a random number between max and min values
    private int attack() {
        return (int) (Math.random() * (maxDamage - minDamage + 1)) + minDamage;
    }

    @Override
    public int use() {
        return attack();
    }
}
class Attributes {
    private int strength;
    private int intelligence;

    //no-arg constructor sets strength and intelligence 3
    public Attributes() {
        this.strength = 3;
        this.intelligence = 3;
    }

    public Attributes(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }

    public void increaseStrength(int amount) {
        strength += amount;
    }

    public void increaseIntelligence(int amount) {
        intelligence += amount;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }
    @Override
    public String toString() {
        return "Attributes [Strength= " + strength + ", intelligence= " + intelligence + "]";
    }
}
abstract class Character implements Comparable<Character>{
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    public Character(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
    public abstract void levelUp();

    public int compareTo(Character o){
        return Integer.compare(this.level,o.level);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + " LvL: " + level + " - " + attributes.toString();
    }
}
abstract class PlayableCharacter extends Character implements Damageable {
    private boolean inParty;
    private Party party;

    public PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
    public boolean isInParty() {
        return inParty;
    }

    //if in a partydoes nothing. Otherwise, triesto add thisto party.
    // If successfulsets the inPartyto true and sets partyto party. Otherwise, displays the error message
    public void joinParty(Party party){
        if(inParty){}
        else{
            try {
                party.addCharacter(this);
                inParty = true;
                this.party = party;
            } catch (PartyLimitReachedException e) {
                System.out.println("Cannot join party! Party limit reached");
            } catch (AlreadyInPartyException e) {
                System.out.println(getName() + " is already in the party.");
            }
        }
    }
    //if is in a party, triesto remove thisfrom party. If successful sets inParty to false and partyto null.
    // If there is an error,displays the error message.
    public void quitParty(){
            try{
                if(inParty) {
                    party.removeCharacter(this);
                    inParty = false;
                    this.party = null;
                }else
                    throw new CharacterIsNotInPartyException("Character is not in the party!");
            }catch (CharacterIsNotInPartyException e){
                System.out.println(e.getMessage());
            }
    }

}
abstract class NonPlayableCharacter extends Character {

    public NonPlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
}
class Merchant extends NonPlayableCharacter{
    //Constructor that takes nameand creates an Attributes object with 0 strength and intelligence
    public Merchant(String name) {
        super(name, new Attributes(0, 0));
    }
    public void levelUp(){}
}
class Skeleton extends NonPlayableCharacter implements Combat{
    public Skeleton(String name, Attributes attributes) {
        super(name, attributes);
    }

    //overrides super’s method to increase level, strength and intelligence by 1
    public void levelUp(){
        this.level++;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(1);
    }
    public void lootWeapon(Weapon weapon) {}
    public boolean isAlive(){
        return health > 0;
    }
    public void takeDamage(int damage){
        health-=damage;
    }
    public void attack(Damageable target){
        int damage = attributes.getStrength() + attributes.getIntelligence();
        target.takeDamage(damage);
    }
    //instead of increasing health, healing received decreases health
    public void takeHealing(int healing) {
        int decreasedHealth = health - healing;
        if (decreasedHealth < 0) {
            health = 0;
        } else {
            health = decreasedHealth;
        }
    }
}
class Warrior extends PlayableCharacter implements Combat{
    private Useable weapon;
    //Constructor that only take name and sets health to 35.
    // Creates an attribute instance with 4 strength and 2 intelligence and sends it to super’s constructor
    public Warrior(String name) {
        super(name, new Attributes(4, 2));
        this.health = 35;
    }
    //overrides super’s method to increase strength by 2 and intelligence and level by 1.
    public void levelUp(){
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
        this.level++;
    }
    public void attack(Damageable target){
        int damage = attributes.getStrength()+ weapon.use();
        target.takeDamage(damage);
    }
    public void lootWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public void takeDamage(int damage){
        health-=damage;
    }
    public boolean isAlive(){
        return health > 0;
    }
    public void takeHealing(int healing){
        health+=healing;
    }
}
class Cleric extends PlayableCharacter implements Caster{
    private Useable spell;

    //Constructor that only take name and sets health to 25.
    // Creates an attribute instance with 2 strength and 4 intelligence and
    // sends it to super’s constructor.
    public Cleric(String name) {
        super(name, new Attributes(2, 4));
        this.health = 25;
    }
    //overrides super’s method to increase
    // strength by 1 and intelligence by2 and level by 1.
    public void levelUp() {
        this.level++;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(2);
    }
    public void takeDamage(int damage){
        health-=damage;
    }
    public void takeHealing(int healing){
        health+=healing;
    }
    public boolean isAlive(){
        return health > 0;
    }
    public void castSpell(Damageable target) {
        target.takeHealing(attributes.getIntelligence() + spell.use());
    }
    public void learnSpell(Spell spell) {
        this.spell = spell;
    }
}
class Paladin extends PlayableCharacter implements Combat,Caster{
    private Useable weapon;
    private Useable spell;

    public Paladin(String name) {
        super(name, new Attributes());
        this.health = 30;
    }
    //overrides super’s method to increase strength by 2 and intelligence
    // by 1 and level by 1 when level is odd. Vice versa when odd.
    public void levelUp(){
        if(getLevel() % 2 == 1){
            attributes.increaseStrength(2);
            attributes.increaseIntelligence(1);
        }else{
            attributes.increaseStrength(1);
            attributes.increaseIntelligence(2);
        }
        this.level++;
    }
    public void takeDamage(int damage){
        health-=damage;
    }
    public void takeHealing(int healing){
        health+=healing;
    }
    public boolean isAlive(){
        return health > 0;
    }
    public void attack(Damageable target){
        int damage = attributes.getStrength()+ weapon.use();
        target.takeDamage(damage);
    }
    public void lootWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public void castSpell(Damageable target) {
        target.takeHealing(attributes.getIntelligence() + spell.use());
    }
    public void learnSpell(Spell spell) {
        this.spell = spell;
    }
}
class Party{
    private final int PARTY_LIMIT = 8;
    private ArrayList<Combat> fighters;
    private ArrayList<Caster> healers;
    private int mixedCount;

    Party(){
        this.fighters = new ArrayList<>();
        this.healers = new ArrayList<>();
        this.mixedCount = 0;
    }
    //addCharacter: can throw PartyLimitReachedException and AlreadyInpartyException.
    // Adds the character to suitable collection
    public void addCharacter(PlayableCharacter character) throws PartyLimitReachedException, AlreadyInPartyException {
        if (fighters.contains(character) || healers.contains(character))
            throw new AlreadyInPartyException("You are already in the party");
        if (fighters.size() + healers.size() - mixedCount >= PARTY_LIMIT)
            throw new PartyLimitReachedException("The party is full. You can not join");
        if(character instanceof Paladin) {
            fighters.add((Combat) character);
            healers.add((Caster) character);
            mixedCount++;
        }if(character instanceof Cleric)
            healers.add((Caster) character);
        if(character instanceof Warrior)
            fighters.add((Combat) character);
    }
    //removeCharacter: can throw CharacterIsNotInPartyException.
    // Removes the character from suitable collection
    public void removeCharacter(PlayableCharacter character) throws CharacterIsNotInPartyException {
        if (!fighters.contains(character) && !healers.contains(character))
            throw new CharacterIsNotInPartyException("The character is not in the party");
        if (fighters.contains(character))
            fighters.remove(character);
        else if(healers.contains(character))
            healers.remove(character);
    }
    public void partyLevelUp() {
        for (Combat fighter : fighters) {
            if (fighter instanceof Paladin)
                ((PlayableCharacter)fighter).levelUp();
            else
                ((PlayableCharacter)fighter).levelUp();
        }
        for (Caster healer : healers) {
            if (healer instanceof Paladin) {}
            else
                ((PlayableCharacter)healer).levelUp();
        }
    }
    public String toString(){

        //I created a stringBuilder object and created an arrayList
        //In that arraylist I merged fighters and healers arrayLists

        StringBuilder sb = new StringBuilder();
        ArrayList<Object> combinedList = new ArrayList<>();
        combinedList.addAll(fighters);
        combinedList.addAll(healers);

        //I compared them each other according to by their levels

        Comparator<Object> levelComparator = new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                int level1 = ((PlayableCharacter) o1).getLevel();
                int level2 = ((PlayableCharacter) o2).getLevel();
                return Integer.compare(level1, level2);
            }
        };

        // Sort combinedList by level
        Collections.sort(combinedList, levelComparator);

        ArrayList<String> paladinNames = new ArrayList<>();
        //I created an arrayList in order to avoid display Paladins two times
        for (Object o : combinedList) {
            if (o instanceof Paladin) {
                String paladinName = ((PlayableCharacter) o).getName();
                if (!paladinNames.contains(paladinName)) {
                    sb.append(((PlayableCharacter) o).getName() + " (Level " + ((PlayableCharacter) o).getLevel() + ")\n");
                    paladinNames.add(paladinName);
                }
            } else {
                sb.append(((PlayableCharacter) o).getName() + " (Level " + ((PlayableCharacter) o).getLevel() + ")\n");
            }
        }

        return sb.toString();
    }
}
class Barrel implements Damageable{
    private int health = 30;
    private int capacity = 10;

    public void explode(){
        System.out.println("Explodes");
    }
    public void repair(){
        System.out.println("Repairing");
    }
    public void takeDamage(int damage){
        health-=damage;
        if(health<=0)
            explode();
    }
    public void takeHealing(int healing){
        health+=healing;
        repair();
    }
    public boolean isAlive(){
        return health > 0;
    }
}
class TrainingDummy implements Damageable{
    private int health = 25;

    public void takeDamage(int damage){
        health-=damage;
    }
    public void takeHealing(int healing){
        health+=healing;
    }
    public boolean isAlive(){
        return health > 0;
    }
}
class PartyLimitReachedException extends Exception {
    public PartyLimitReachedException(String message) {
        super(message);
    }
}
class AlreadyInPartyException extends Exception {
    public AlreadyInPartyException(String message) {
        super(message);
    }
}
class CharacterIsNotInPartyException extends Exception {
    public CharacterIsNotInPartyException(String message) {
        super(message);
    }
}