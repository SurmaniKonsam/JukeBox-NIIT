import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Project_7 {
    static long key1; //saving account key
    static long key2; //payment account key
    public static void main(String[] args) {
        int countAttributes = 0;
        SavingAccount save = new SavingAccount();
        PaymentAccount pay = new PaymentAccount();
        Scanner take = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/surmanidb";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            Statement stmt = con.createStatement();//used for one query execution where prepareStatement is used for multiple.
            System.out.println("Welcome to the IDBC bank!!!");
            System.out.println("Are you an existing customer? yes/no");
            String ans = take.next();
            if(ans.equalsIgnoreCase("yes"))
            {
                System.out.println("Which account you want to avail service of?");
                System.out.println("1. Saving account");
                System.out.println("2. Payment account");
                int service = take.nextInt();
                switch (service)
                {
                    case 1:
                        System.out.println("Enter your saving account number");
                        long acc = take.nextLong();
                        long key = 0;
                        int totalBalance = 0;
                        int lastEntry = 0;
                        int balanceLeft = 0;
                        String query = "Select * from SavingAccount";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                            while(rs.next())
                            {
                                key = rs.getLong(2);
                                totalBalance = rs.getInt(3);
                                lastEntry = rs.getInt(1);
                                balanceLeft = totalBalance;
                            }
                            System.out.println("last entry index::"+lastEntry);
                            //System.out.println("key ::"+key);
                            System.out.println("Total balance in the  saving account:: "+totalBalance);
                            if(acc == key) {
                                System.out.println("Yes you are our existing customer");
                                //System.out.println("key value ::" +key);
                                System.out.println();
                                System.out.println("Your saving account details::");
                                String showCol = "Select * from SavingAccount";
                                Statement show = con.createStatement();
                                ResultSet showSave = show.executeQuery(showCol);
                                System.out.format("%s %s %s %s\n","SavingAccountId","Saving_Account_Number",
                                        "Available_Balance","Transaction_Details");
                                while(showSave.next())
                                {
                                    System.out.println(showSave.getInt(1)+"\t\t\t\t"+
                                            showSave.getLong(2)+"\t\t\t"
                                        +showSave.getInt(3)+"\t\t\t"+showSave.getString(4));
                                }
                                System.out.println();
                                System.out.println("This are the list of services availed to you::");
                                String opQuery = "Select * from Operation";
                                Statement ope = con.createStatement();
                                ResultSet showOpe = ope.executeQuery(opQuery);
                                while(showOpe.next())
                                {
                                    System.out.println(showOpe.getInt(1)+" "+showOpe.getString(2));
                                }
                                System.out.println("Choose an operation mentioned above::");
                                int choice = take.nextInt();
                                switch(choice) {
                                    case 1:
                                        System.out.println("Enter the amount you want to transfer");
                                        int fund = take.nextInt();
                                        if (fund > totalBalance) {
                                            System.out.println("Sorry you dont have enough balance in your account");
                                        } else {
                                            balanceLeft = balanceLeft - fund;//these values must be put in the saving
                                            //account tables.
                                            System.out.println("balance left ::" + balanceLeft);
                                            String updateTransaction = "Insert into SavingAccountTransactionDetails" +
                                                    "(availableBalance,TransactionDate,Debited)" +
                                                    " values(?,?,?)";
                                            PreparedStatement transaction = con.prepareStatement(updateTransaction);
                                            LocalDateTime myObj2 = LocalDateTime.now();
                                            String time = ""+myObj2;
                                            transaction.setInt(1,balanceLeft);
                                            transaction.setString(2,time);
                                            transaction.setInt(3,fund);
                                            int checkTransaction = transaction.executeUpdate();
                                            if(checkTransaction == 1)
                                            {
                                                System.out.println("Transaction detail has been successfully updated");
                                            }
                                            else
                                            {
                                                System.out.println("not updated error in the query");
                                            }


                                            String queryUpdate = "Update SavingAccount set availableBalance = ? " +
                                                    "where savingId = ?";
                                            PreparedStatement updateTable = con.prepareStatement(queryUpdate);
                                            updateTable.setInt(1, balanceLeft);
                                            updateTable.setInt(2, 3);
                                            int check = updateTable.executeUpdate();
                                            //System.out.println("check value :: " + check);
                                            if (check == 1) {
                                                System.out.println("saving account table updated ");
                                            } else {
                                                System.out.println("not updated wrong with the query");
                                            }
                                        }
                                        break;

                                    case 2:
                                        System.out.println("balance in the account now ::" + balanceLeft);
                                        System.out.println("Enter the amount you want to deposit");
                                        int deposit = take.nextInt();
                                        balanceLeft = balanceLeft + deposit;
                                        System.out.println("balance after deposit ::" + balanceLeft);
                                        String updateTransaction = "Insert into SavingAccountTransactionDetails" +
                                                "(availableBalance,TransactionDate,credited)" +
                                                " values(?,?,?)";
                                        PreparedStatement creditUpdate = con.prepareStatement(updateTransaction);
                                        LocalDateTime myObj2 = LocalDateTime.now();
                                        String time = ""+myObj2;
                                        creditUpdate.setInt(1,balanceLeft);
                                        creditUpdate.setString(2,time);
                                        creditUpdate.setInt(3,deposit);
                                        int check = creditUpdate.executeUpdate();
                                        if(check == 1)
                                        {
                                            System.out.println("saving account transaction table successfully  updated");
                                        }
                                        else
                                        {
                                            System.out.println("Error not updated");
                                        }


                                        String depositUpdate = "Update SavingAccount set availableBalance = ? where savingId = ?";
                                        PreparedStatement depositTable = con.prepareStatement(depositUpdate);
                                        depositTable.setInt(1, balanceLeft);
                                        depositTable.setInt(2, 3);
                                        int depoCheck = depositTable.executeUpdate();
                                        if (depoCheck == 1) {
                                            System.out.println("your money has been successfully deposited");
                                        } else {
                                            System.out.println("Not deposited check the query");
                                        }
                                        break;

                                    case 4:
                                        System.out.println("balance in the account now ::"+balanceLeft);
                                        System.out.println("Enter the amount you want to withdraw");
                                        int withdraw = take.nextInt();
                                        balanceLeft = balanceLeft-withdraw;
                                        System.out.println("available balance after withdrawal ::"+balanceLeft);
                                        String withdrawCash = "Insert into SavingAccountTransactionDetails(availableBalance," +
                                                "transactionDate,debited) values(?,?,?)";
                                        PreparedStatement draw = con.prepareStatement(withdrawCash);
                                        LocalDateTime myObj3 = LocalDateTime.now();
                                        String time2 = ""+myObj3;
                                        draw.setInt(1,balanceLeft);
                                        draw.setString(2,time2);
                                        draw.setInt(3,withdraw);
                                        int checkDraw = draw.executeUpdate();
                                        if(checkDraw == 1)
                                        {
                                        System.out.println("saving account transaction table successfully  updated");
                                        }
                                        else
                                        {
                                        System.out.println("Error not updated");
                                        }


                                        String withdrawUpdate = "Update SavingAccount set availableBalance = ? where savingId = ?";
                                        PreparedStatement withdrawTable = con.prepareStatement(withdrawUpdate);
                                        withdrawTable.setInt(1,balanceLeft);
                                        withdrawTable.setInt(2,3);
                                        int withdrawCheck = withdrawTable.executeUpdate();
                                        if(withdrawCheck == 1)
                                        {
                                            System.out.println("amount has been successfully withdrawn");
                                        }
                                        else
                                        {
                                            System.out.println("not withdrawn check query");
                                        }
                                        break;

                                    case 3:
                                        System.out.println("balance in the account now ::"+balanceLeft);
                                        System.out.println("Enter the amount for online payment");
                                        int payOnline = take.nextInt();
                                        balanceLeft = balanceLeft-payOnline;
                                        System.out.println("available balance after withdrawal ::"+balanceLeft);
                                        String online = "Insert into SavingAccountTransactionDetails(availableBalance," +
                                                "transactionDate,debited) values(?,?,?)";
                                        PreparedStatement onlineUpdate = con.prepareStatement(online);
                                        LocalDateTime myObj4 = LocalDateTime.now();
                                        String time4 = ""+myObj4;
                                        onlineUpdate.setInt(1,balanceLeft);
                                        onlineUpdate.setString(2,time4);
                                        onlineUpdate.setInt(3,payOnline);
                                        int check2 = onlineUpdate.executeUpdate();
                                        if(check2 == 1)
                                        {
                                            System.out.println("saving account transaction table successfully  updated");
                                        }
                                        else
                                        {
                                            System.out.println("Error not updated");
                                        }



                                        String paymentUpdate = "Update SavingAccount set availableBalance = ? where savingId = ?";
                                        PreparedStatement paymentTable = con.prepareStatement(paymentUpdate);
                                        paymentTable.setInt(1,balanceLeft);
                                        paymentTable.setInt(2,3);
                                        int payCheck = paymentTable.executeUpdate();
                                        if(payCheck == 1)
                                        {
                                            System.out.println("amount has been successfully paid to the merchant");
                                        }
                                        else
                                        {
                                            System.out.println("no payment check query");
                                        }
                                        break;

                                    case 5:
                                        System.out.println();
                                        System.out.println("Your current balance ::"+balanceLeft);
                                        System.out.println("after how many year you want to have your money retrieved?");
                                        System.out.println("Enter your mature year");
                                        int year = take.nextInt();
                                        LocalDate obj = LocalDate.now();
                                        System.out.println("Calculating interest");
                                        System.out.println("Your interest rate is 2.5%");
                                        double matured = balanceLeft*2.5*year/100;
                                        System.out.println(year+" years from date::"+obj+" your available amount will get" +
                                                " matured to::"+(balanceLeft+matured));
                                        break;

                                    case 6:
                                        System.out.println("Saving account transaction details");
                                        System.out.format("%s\t%s\t\t\t\t\t\t %s %s %s\n","savingTransactionId",
                                                "TransactionDate","availableBalance","Debited","credited");
                                        String showAccountTransaction = "Select * from SavingAccountTransactionDetails";
                                        Statement showTransaction = con.createStatement();
                                        ResultSet showAccT = showTransaction.executeQuery(showAccountTransaction);
                                        showAccT.next();
                                        while(showAccT.next())
                                        {
                                            System.out.println(showAccT.getInt(1)+"\t\t\t\t\t"+
                                                    showAccT.getString(2)+"\t\t\t "
                                                    +showAccT.getInt(3)+"\t\t\t"+showAccT.getString(4)
                                            +"\t\t"+showAccT.getInt(5));
                                        }
                                }
                            }
                            else
                            {
                                System.out.println("\"Sorry you might have entered wrong key or" +
                                        " you haven't opened account with IDBC");
                            }
                        break;

                    case 2:
                        System.out.println("Enter your payment account number");
                        long payKey = take.nextLong();
                        long key2 = 0;
                        String queryP = "Select * from PaymentAccount";
                        Statement stmP = con.createStatement();
                        ResultSet rsP = stmP.executeQuery(queryP);
                        while(rsP.next())
                        {
//                                System.out.println(rsP.getInt(1)+" "+rsP.getLong(2)+" "
//                                        +rsP.getInt(3)+" "+rsP.getString(4));
                            key2 = rsP.getLong(2);
                            //countAttributes++;

                        }
                        if(payKey == key2) {
                            System.out.println("Yes you are our existing customer");
                            System.out.println("key value ::" +key2);
                        }
                        else
                        {
                            System.out.println("\"Sorry you might have entered wrong key or" +
                                    " you haven't opened account with IDBC");
                        }
                        break;
                }
            }
            else
            {
                System.out.println("Whether do you want to open an account with the IDBC account - yes/no");
                String answer = take.next();
                if(answer.equalsIgnoreCase("no"))
                {
                    System.out.println("thank you for visiting the idbc site we hope we could be of help in future");
                }
                else
                {
                    System.out.println("To open account you need to provide your relevant information::");
                    System.out.println("Enter your name please::");
                    String name = take.next();
                    System.out.println("Enter your adhaar id");
                    String adhaar = take.next();
                    System.out.println("Enter your phone number");
                    String phoneNumber = take.next();
                    System.out.println("Enter your email id");
                    String emailId = take.next();
                    System.out.println("Enter your age");
                    int age = take.nextInt();
                    if(age<=18)
                    {
                        System.out.println("Sorry we can't avail our service you are underage the user must be least" +
                                "above 18 years old");
                        //till line 145 it must execute properly.
                        //yes till line 145 everything is ok.
                    }
                    else
                    {
                        System.out.println("We can avail you to open the provided account type");
                        System.out.println("1. Saving Account");
                        System.out.println("2. Payment Account");
                        System.out.println("Please choose whether which account you want to open::");
                        int choice = take.nextInt();
                        switch(choice)
                        {

                            case 1:
                                System.out.println("You will be provided 12 digit number for your saving account verification");
                                System.out.println("Kindly please keep the account number securely to avail our services");
                                key1 = save.accountNumberGen();// i think need to use temp variable here.
                                System.out.println("Congratulation your unique identification key has been generated");
                                System.out.println("SaveAccount key is ::"+key1);
                                String query = "Insert into SavingAccount values(?,?,?,?)";
                                String getSave = "Select * from SavingAccount";
                                Statement executeSave = con.createStatement();
                                ResultSet traverseSave = executeSave.executeQuery(getSave);
                                int compareKey = 0;
                                while(traverseSave.next())
                                {
                                    compareKey = traverseSave.getInt(1);
                                }
                                System.out.println("last "+compareKey+" entry key in the table");
                                System.out.println("Please enter your values");
                                System.out.println("Enter your entry key");
                                int entry = take.nextInt();
                                if(entry == compareKey)
                                {
                                    System.out.println("Your entry key is:: "+entry);
                                    System.out.println("The entry key is duplicate the table is primary key please enter your " +
                                            "next entry that is "+(entry+1));
                                }
                                else {
                                    PreparedStatement ps = con.prepareStatement(query);
                                    //Statement stm = con.createStatement();
                                    //stm.executeQuery(query);
                                    ps.setInt(1, entry);
                                    ps.setLong(2, key1);
                                    System.out.println("Please insert an initial amount to begin with your account");
                                    //first an initial 23456 value was taken lets now ask value from the user.
                                    int amount = take.nextInt();
                                    if (amount < 1000) {
                                        System.out.println("Sorry the amount should be greater than or equal to 1000");
                                    } else {
                                        ps.setInt(3, amount);
                                    }
                                    ps.setString(4, "transaction_Detail");
                                    int check = ps.executeUpdate();
                                    if (check == 1) {
                                        System.out.println("updated successfully");
                                    } else {
                                        System.out.println("An error is there");
                                    }
                                }
                                break;

                            case 2:
                                System.out.println("You will be provided 12 digit number for you payment account" +
                                        " verification");
                                System.out.println("kindly please keep the account number securely to avail" +
                                        " our services");
                                key2 = pay.accountNumberGen();
                                System.out.println("Congratulation your unique identification key has been generated");
                                System.out.println("PayAccount key is ::"+key2);
                                String query1 = "Insert into PaymentAccount values(?,?,?,?)";
                                String getPay = "Select * from PaymentAccount";
                                Statement executePay = con.createStatement();
                                ResultSet traversePay = executePay.executeQuery(getPay);
                                int comparePayKey = 0;
                                while(traversePay.next())
                                {
                                    comparePayKey = traversePay.getInt(1);
                                }
                                System.out.println("Last "+comparePayKey+" entry key in the table");
                                System.out.println("Please enter your values");
                                System.out.println("Enter your entry key");
                                int entryPayKey = take.nextInt();
                                if(comparePayKey == entryPayKey)
                                {
                                    System.out.println("Your entry key is:: "+entryPayKey);
                                    System.out.println("The entry key is duplicate the table is primary key please enter your " +
                                            "next entry that is "+(entryPayKey+1));
                                }
                                else {
                                    PreparedStatement ps1 = con.prepareStatement(query1);
                                    //Statement stm = con.createStatement();
                                    //stm.executeQuery(query);
                                    ps1.setInt(1, entryPayKey);
                                    ps1.setLong(2, key2);
                                    System.out.println("Please insert an initial amount to begin with your account");
                                    //first an initial 23456 value was taken lets now ask value from the user.
                                    int amountPay = take.nextInt();
                                    if (amountPay < 1000) {
                                        System.out.println("Sorry the amount should be greater than or equal to 1000");
                                    } else {
                                        ps1.setInt(3, amountPay);
                                    }
                                    ps1.setString(4, "transaction_Detail");
                                    int check1 = ps1.executeUpdate();
                                    if (check1 == 1) {
                                        System.out.println("update successfully");
                                    } else {
                                        System.out.println("An error is there");
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
