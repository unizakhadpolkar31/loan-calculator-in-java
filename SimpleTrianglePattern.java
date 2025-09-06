import java.util.Scanner;
public class SimpleTrianglePattern
{
public static void main(String[]args)
{
int rows = 5; // Number of rows for the triangle

for(int i =1 ;i<=rows;i++)
{
for(int j=rows;j>i;j--)
{
System.out.print("");
}
for (int k =1;k<=i;k++)
{
System.out.print("*");
}
System.out.println();
}
}
}

