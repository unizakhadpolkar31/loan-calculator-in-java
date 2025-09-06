public class main
{
public static void main(String[] args)
{
int num=421, rev=0,digit;

System.out.println("original number:"+num);
while(num!=0)
{
digit=num%10;
rev=rev*10+digit;
num=num/10;
}
System.out.println("rev:"+rev);
}
}
