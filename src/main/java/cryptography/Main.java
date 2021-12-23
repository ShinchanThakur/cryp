package cryptography;
import java.sql.SQLException;
import java.util.*;
public class Main 
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        System.out.println("Welcome dear user");
        boolean flag = true;
        do{
        System.out.println("What would you like to do?");
        System.out.println("1. Add Data");
        System.out.println("2. Retrieve Data");
        System.out.println("3. Exit");
        
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        
        dao dbManager = new dao();
        
        switch(choice){
            case 1: dbManager.addData();
                    break;
            case 2: dbManager.retrieveData();
                    break;
            default: flag = false;
        }
        }while(flag);
    }
    
}
