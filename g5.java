import java.io.*;
class g5{
public static void main(String args[])
throws Exception{

InputStreamReader r=new InputStreamReader(System.in);

BufferedReader br=new BufferedReader(r);
System.out.println("enter your name");
String name=br.readLine();
System.out.println("welcome"+name);
}
}