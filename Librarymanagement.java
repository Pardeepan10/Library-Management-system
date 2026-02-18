import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Librarymanagement {
    static Scanner in=new Scanner(System.in);
    static void main(String[] args) {
        while(true){
            System.out.println("1:Addbook:"+" "+"2:Viewbook"+" "+"3:SearchBook:"+" "+"4:issueBook"+" "+"5:Returnbook");
            int choice=in.nextInt();
            if(choice==1){
                INSERT();
            } else if (choice==2) {
                SELECT();
            } else if (choice==3) {
                WHERE();
            } else if (choice==4) {
                UPDATE();
            } else if (choice==5) {
                returnbook();
            } else if (choice==6) {
                System.out.println("Exiting");
                break;
            }
        }
    }
    static void INSERT(){
        try {
            System.out.println("Bookid");
            int bookid = in.nextInt();
            in.nextLine();

            System.out.println("Title");
            String Title = in.nextLine();
            //in.nextLine();

            System.out.println("Author");
            String author = in.nextLine();
            // in.nextLine();

            System.out.println("genre");
            String genre = in.nextLine();

            System.out.println("BookQty");
            int bookqty = in.nextInt();

            Connection con=DBconnection.getConnection();
            String sql="INSERT INTO books1 VALUES(?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,bookid);
            ps.setString(2,Title);
            ps.setString(3,author);
            ps.setString(4,genre);
            ps.setInt(5,bookqty);
            ps.executeUpdate();
            System.out.println("Book added Successfully");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void SELECT(){
        try {
            Connection con = DBconnection.getConnection();
            String sql = "SELECT * FROM books1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " " + rs.getString("title") + " " + rs.getString("author") + " " + rs.getString("genre") + " " + rs.getInt("quantity")
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void WHERE(){
        try {
            System.out.println("Enter Book id:");
            int id = in.nextInt();
            Connection con = DBconnection.getConnection();
            String sql = "SELECT * FROM books1 WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " " + rs.getString("title") + " " + rs.getString("author") + " " + rs.getString("genre") + " " + rs.getInt("quantity")
                );
            } else {
                System.out.println("Book not found");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void UPDATE(){
        try {
            System.out.println("Enter Book id:");
            int id = in.nextInt();

            Connection con = DBconnection.getConnection();
            String sql = "UPDATE books1 SET quantity = quantity - 1 WHERE book_id=? AND quantity > 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Book issued successfully");
            else
                System.out.println("Book not available");
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void returnbook(){
        try {
            System.out.println("Enter return Book id:");
            int id = in.nextInt();
            Connection con = DBconnection.getConnection();
            String sql = "UPDATE books1 SET quantity = quantity + 1 WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Book returned successfully");
            else
                System.out.println("Book not found");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

