package Lab_4;
//Bh289

import java.util.Scanner;

public class rsa {
    public static void main(String[]args) {
//        int p=17,q=11,m=88;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter Primes ");
        int p = sc.nextInt();
        int q = sc.nextInt();
        System.out.println("Enter message ");
        int m = sc.nextInt();
        int[][] keys= keygen(p,q);
//        printdoublearray(keys);
//        System.out.println(m);
        int c = encrypt(keys[0],m);
        System.out.println(c);
        System.out.println(decrypt(keys[1],c));

    }
    public static int encrypt(int[]key,int m){
        return pow(m,key[0],key[1]);
    }
    public static int decrypt(int[] key,int c){
        int k = (key[1]*key[2]);
        return  pow(c,key[0],k);
    }
    public static int pow(int a,int b,int p){       //a^b%p
        int res=1;
        while (b>0){
            if (b%2==1){
                res=res*a%p;
            }
            b/=2;
            a=a*a;
            a=a%p;
        }
        return res%p;
    }
    static int[][]keygen(int p,int q) {
        int[][] k = new int[2][];
        k[0] = new int[2];      //private
        k[1]=new int[3];
        k[1][1]=p;
        k[1][2]=q;
        k[0][1] = p * q;
        int pn =(p-1)*(q-1);
        for(int i=2; i<=pn;i++){
            if(coprime(pn,i)==1){
                k[0][0]=i;
                break;
            }
        }
        int a = pn;
        int t = k[0][0];
        for (int X = 1; X < a; X++)
            if (((t % a) * (X % a)) % a == 1)
                k[1][0]=X;
        return k;
    }
    public static int coprime(int a,int b){
        try{
            if(a<b){
                int t = b;
                b=a;
                a=t;
            }
            int n=a%b;
            while (n>1){
                a=b;
                b=n;
                n=a%b;
            }
            return n;
        } catch (Exception e) {
            System.out.println(1);
            return 1;
        }
    }
    public static void printdoublearray(int[][] res){
        for (int[]k:res){
            for (int i :k){
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

}
