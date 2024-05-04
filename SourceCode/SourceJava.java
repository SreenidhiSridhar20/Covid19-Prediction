import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpSession;
public class Smart {
public static void main(String[] args) throws IOException {
SerialPort[] ports = SerialPort.getCommPorts();
System.out.println("Select a port:");
int i = 1;
for (SerialPort port : ports) {
System.out.println(i++ + ": " + port.getSystemPortName());
}
Scanner s = new Scanner(System.in);
int chosenPort = s.nextInt();
SerialPort serialPort = ports[chosenPort - 1];
if (serialPort.openPort()) {
System.out.println("Port opened successfully.");
} else {
System.out.println("Unable to open the port.");
return;
}
serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
Scanner data = new Scanner(serialPort.getInputStream());
String value;
FileWriter fw = new FileWriter("D:\\Port_Read\\covid.txt", true);
BufferedWriter bw = new BufferedWriter(fw);
PrintWriter out = new PrintWriter(bw);
while (data.hasNext()) {
value = data.next();
//System.out.println("Gas"+(String)data.nextLine()+""+"Temp "+(String)data.nextLine());
//out.println( "Gas "+(String)data.nextLine()+""+"Temp "+(String)data.nextLine());
System.out.println( (String) data.nextLine() + "" + (String) data.nextLine());
out.println( (String) data.nextLine() + "" + (String) data.nextLine());
String a[] = data.nextLine().split(",");
String temp = a[0];
System.out.println(temp);
// String gas = a[1];
// System.out.println(gas);
try {
System.out.println("test1");
Class.forName("com.mysql.jdbc.Driver");
Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/covid", "root", "root");
PreparedStatement ps = con.prepareStatement("update register set sound='"+(String) data.nextLine()+"' , temp='"+temp+"' where flag= 'Active'");
// ps.setString(1, temp);
// ps.setString(2, gas);
int x = ps.executeUpdate();
if (x != 0) {
}
// try{value = Integer.parseInt(data.nextLine());}catch(Exception e){}
// slider.setValue(value);
} catch (Exception e) {
System.out.println(e);
}
}
out.close();
data.close();
System.out.println("Done.");
}
}
