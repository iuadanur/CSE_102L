package Ex5;

import java.util.ArrayList;

public class Ex5_20210808049 {
    
}
class Account{
    private String accountNumber;
    private double balance;

    Account(String accountNumber, double balance){
        this.accountNumber=accountNumber;
        if(balance<0) //If first balance is below zero throw an exception
            throw new InsufficientFundsException(balance);
        else
            this.balance = balance;
    }
    //Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    //A method add which is given amount to the balance
    public void deposit(double amount) throws InvalidTransactionException {
        if(amount<0) //if amount negative throw exception
            throw new InvalidTransactionException(amount);
        else
            balance += amount;
    }
    //a method to take money which is given amount. If amount is negative or amount is greater than balance
    // throw an exception
    public void withdraw(double amount) throws InvalidTransactionException {
        if(amount<0)
            throw new InvalidTransactionException(amount);
        else if (balance<amount)
            throw new InsufficientFundsException(balance,amount);
        else
            balance -= amount;
    }
    public String toString(){
        return "Account: "+accountNumber+", Balance: "+ balance;
    }
}
class Customer{
    private String name;
    private ArrayList<Account> accounts;
    Customer(String name){
        this.name=name;
        this.accounts = new ArrayList<Account>();
    }
    //Private method to get an account with given number
    //If account number matches return the account number otherwise throw an exception
    private Account getAccount(String accountNumber){
        for(Account account: accounts){
            if(account.getAccountNumber().equals(accountNumber))
                return account;
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void removeProduct(Account accountNumber){
        accounts.remove(accountNumber);
    }
    public void addAccount(Account account){
        try {
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistsException(account.getAccountNumber());
        }catch(AccountNotFoundException e){
            accounts.add(account);
        }finally {
            System.out.println(this);
        }
        System.out.println("Added account: " + account.getAccountNumber() + " with " + account.getBalance());
    }
    //a method transfer money from one account to another one
    public void transfer(String fromAccount, String toAccount, double amount) throws InvalidTransactionException {
        Account from = getAccount(fromAccount);
        Account to = getAccount(toAccount);
        try {
            from.withdraw(amount);
            to.deposit(amount);
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException(e,"cannot transfer funds from account "+fromAccount+" to account "+toAccount);
        }
    }
    //I prefer stringbuilder in order to make Strings look more ordered
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer ").append(name).append(":\n");
        for (Account account : accounts) {
            sb.append("\t").append(account).append("\n");
        }
        return sb.toString();
    }
}


class InsufficientFundsException extends RuntimeException{
    InsufficientFundsException(double balance){
        super("Wrong balance: "+balance);
    }
    InsufficientFundsException(double balance,double amount){
        super("Required amount is "+amount+" but only "+balance+" remaining");
    }
}
class InvalidTransactionException extends Exception{

    InvalidTransactionException(double amount){
        super("Invalid amount: "+amount);
    }
    InvalidTransactionException(InvalidTransactionException e,String message){
        super(message + ":\n\t" + e.getMessage());
    }
}
class AccountNotFoundException extends RuntimeException{
    AccountNotFoundException(String accountNumber) {
        super("Account Number " + accountNumber+ " does not exists");
    }
}
class AccountAlreadyExistsException extends RuntimeException{
    AccountAlreadyExistsException(String accountNumber){
        super("Account number "+accountNumber+" already exists");
    }
}