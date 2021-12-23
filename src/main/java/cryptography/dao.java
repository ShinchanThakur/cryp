package cryptography;
import java.sql.* ;
import java.util.*;
public class dao {
        String url = "jdbc:mysql://localhost:3306/crypto";
        String uname = "root";
        String pass = "howareyou";
        
        Connection con;
    public dao() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.con = DriverManager.getConnection(url, uname, pass);
    }
    protected void finalize() throws SQLException{
           con.close();
    }
    public String encryptData(String key, String data){
        KeyGenerator kGen = new KeyGenerator(key);
        String nK = kGen.getNumericKey();

        ArmstrongManager aMgr = new ArmstrongManager(nK);
        ColorManager cMgr = new ColorManager(nK);


        String encData ="";
        int temp;
        int i;
        for(i =0 ; i < data.length(); i++)
        {
            temp = aMgr.encrypt(data.charAt(i));
            temp = cMgr.encrypt(temp);
            encData = encData  + (char)temp;
        }
        return encData;
    }
    public String decryptData(String key, String encData){
        KeyGenerator kGen = new KeyGenerator(key);
        String nK = kGen.getNumericKey();

        ArmstrongManager aMgr = new ArmstrongManager(nK);
        ColorManager cMgr = new ColorManager(nK);

        String decData= "";
        int temp;
        for(int i =0 ; i < encData.length(); i++)
        {
            temp = cMgr.decrypt(encData.charAt(i));
            temp = aMgr.decrypt(temp);

            decData = decData  + (char)temp;
        }
        return decData;
    }
    public int insertDataIntoDb(String name, String encData) throws SQLException{

        // STORING ENCRYPTED DATA INTO THE DATABASE
        String query = "insert into info (name, data) values (?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, encData);
        int count = pst.executeUpdate();
//        System.out.println(count + " row/s affected");

        query = "select id from info where name=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        rs.next();
        int id = rs.getInt("id");

        st.close();
        pst.close();

        return id;
    }
    public String[] retrieveDataFromDb(int id) throws SQLException {
        String data[] = new String[2];
        String query = "select * from info where id=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        String encData = rs.getString("data");
        String name = rs.getString("name");
        data[0] = encData;
        data[1] = name;
        st.close();
        return data;
    }

    public void addData() throws SQLException {
        Scanner input = new Scanner(System.in);
        String data, key,name;
        
        System.out.println("Input your cool name");
        name = input.nextLine();
        System.out.println("Input your precious data");
        data = input.nextLine();
        System.out.println("Input the key to lock your precious data");
        key = input.nextLine();

        String encData = encryptData(key, data);
        int id = insertDataIntoDb(name, encData);
        System.out.println("Please remember this id to retrieve your data : " + id);
        System.out.println("And without your key, your data is useless, even for us, so don't forget it");

    }


    public void retrieveData() throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your id");
        int id = input.nextInt();
        
        String data[] = retrieveDataFromDb(id);
        String encData = data[0];
        String name = data[1];
        
        System.out.println("Dear " + name + ", please enter your key");
        String key = input.nextLine();
        key = input.nextLine();

        String decData = decryptData(key, encData);

        System.out.println("Here is your precious data: ");
        System.out.println(decData);
    }
}
