import java.util.Random;

public abstract class Account {
    public Account()
    {

    }
    public long accountNumberGen()
    {
        int i = 0;
        Random r = new Random();
        String pass = "";

        while(i<12)
        {
            long password = r.nextLong(1,10);
            pass = pass+password;
            i++;
        }
        long generate = Long.parseLong(pass);
        //System.out.println(generate);
        return generate;
    }
}
