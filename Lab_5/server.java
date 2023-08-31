package Lab_5;

import java.util.Scanner;

public class server {
    public static void main(String []args){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter base");
        int g = sc.nextInt();
        System.out.println("enter prime");
        int p = sc.nextInt();
        System.out.println("enter private key");
        int a = sc.nextInt();
        System.out.println("Public key is :"+ modulos.pow(g,a,p));
        System.out.println("enter public key of other");
        int B = sc.nextInt();
        System.out.println("Shared key is "+modulos.pow(B,a,p));
    }
}
