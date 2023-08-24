import java.util.Arrays;
import java.util.Scanner;
//Bh289
public class S_des{
    static int[] p10 = new int[]{ 3,5,2,7,4,10,1,9,8,6 };
    static int[] p8 = new int[]{6,3,7,4,8,5,10,9}; 
    static int[] ip = new int []{2,6,3,1,4,8,5,7};
    static int[] ip1 = new int []{4,1,3,5,7,2,8,6};
    static int[]  ep = new int []{4,1,2,3,2,3,4,1};
    static int[] p4 = new int []{2,4,3,1};
    static int[] []s0 = new int [][]{{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,0}};
    static int[] []s1 = new int [][]{{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("For encryption Enter key ");

        String[] splitArray = sc.nextLine().split("");
        int[] key = new int[splitArray.length];
        for (int i = 0; i < splitArray.length; i++) {
            key[i] = Integer.parseInt(splitArray[i]);
        }
        // System.out.print(Arrays.deepToString(keys));     
        System.out.print("Enter passphrase ");

        String[] splitArrayy = sc.nextLine().split("");
        int[] plaintext = new int[splitArrayy.length];
        for (int i = 0; i < splitArrayy.length; i++) {
            plaintext[i] = Integer.parseInt(splitArrayy[i]);
        }
        // int[]key = new int[]{0,0,1,0,0,1,0,1,1,1};
        // int[]plaintext = new int []{1,0,1,0,0,1,0,1};
        int [] cyphertext = encrypt(key, plaintext);
        System.out.print("Cyphertext is : ");
        for (int i :cyphertext){
            System.out.print(i);
        }System.out.println();

        System.out.println("For decryption  Enter key ");

        String[] ssplitArray = sc.nextLine().split("");
        int[] kkey = new int[ssplitArray.length];
        for (int i = 0; i < ssplitArray.length; i++) {
            kkey[i] = Integer.parseInt(ssplitArray[i]);
        }
        // System.out.print(Arrays.deepToString(keys));
        System.out.print("Enter passphrase ");

        String[] ssplitArrayy = sc.nextLine().split("");
        int[] cyphertextt = new int[splitArrayy.length];
        for (int i = 0; i < ssplitArrayy.length; i++) {
            cyphertextt[i] = Integer.parseInt(ssplitArrayy[i]);
        }

        int [] decoded = decrypt(kkey, cyphertextt);
        System.out.print("plaintext is : ");

        for (int i :decoded){
            System.out.print(i);
        }System.out.println();
        sc.close();
    }
    public static int[] decrypt (int[]key,int[]data){
        int[][] keys = keygen(key);
        int [] data1 = permutate(ip, data);
        data1 = rx(keys[1],data1);
        ls(4,data1);
        data1 = rx(keys[0],data1);
        data1 = permutate(ip1, data1);
        return data1;
    }
    public static int[] encrypt (int[]key,int[]data){
        int[][] keys = keygen(key);
        int [] data1 = permutate(ip, data);
        data1 = rx(keys[0],data1);
        ls(4,data1);
        data1 = rx(keys[1],data1);
        data1 = permutate(ip1, data1);
        return data1;
    }
    public static int[] rx (int[]key,int[]data){
        int [] n1 =Arrays.copyOfRange(data ,0,4);
        int [] n2 =Arrays.copyOfRange(data ,4,data.length);
        int [] sol = new int [8];
        System.arraycopy(n2, 0,sol,4 ,4);
        int[] w1 = permutate(ep, n2);
        w1 = xor(w1,key);
        int [] n3 =Arrays.copyOfRange(w1,0,4);
        int [] n4 =Arrays.copyOfRange(w1 ,4,data.length);
        System.arraycopy(substitute(s0, n3), 0,n2,0 ,2);
        System.arraycopy(substitute(s1, n4), 0,n2,2 ,2);
        
        n2 = permutate(p4,n2);
        n2 = xor(n1, n2);
        System.arraycopy(n2, 0,sol,0 ,4);
        
        return sol;
    }
    
    public static int[][] keygen (int[] key){
        int [] data = permutate(p10,key );
        int [][] finalkeys = new int[2][];
        int [] l = Arrays.copyOfRange(data ,0,5);
        int [] r = Arrays.copyOfRange(data ,5 ,data.length);
        ls(1,l);
        ls(1,r);
        int[] merg = new int[10];
        System.arraycopy(l, 0,merg,0 ,5);
        System.arraycopy(r, 0,merg,5 ,5);
        finalkeys[0]= permutate(p8,merg);
        ls(2,l);
        ls(2,r);
        System.arraycopy(l, 0,merg,0 ,5);
        System.arraycopy(r, 0,merg,5 ,5);
        finalkeys[1]= permutate(p8,merg);
        return finalkeys;
    }
    public static int[] permutate(int[]combination ,int[]data){
        int[]finall = new int[combination.length];
        for (int i=0;i<combination.length;i++){
            finall[i]= data[combination[i]-1];
        }
        return finall;
    }
    public static void ls(int shift,int[]inp){
        int len = inp.length;
        int []ne = new int [len];
        for (int i=0;i<len;i++){
            ne[i]= inp[(i+shift)%len];
        }
        for (int i=0;i<len;i++){
            inp[i]= ne[i];
        }
    }
    public static int[] xor (int []arr1 , int[] arr2){
        int [] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i]= (arr1[i]+arr2[i])%2;
        }
        return result;
    }
    public static int toint(int q, int t){
        int n = t;
        n+=q*2;
        return n ;
    }
    public static int []toarr(int t){
 
        int[] n = new int []{ t/2,t%2};
        return n;
    }
    public static int[] substitute(int[][]matrix,int[]value){
 
        int [] arr = toarr(matrix[toint(value[0], value[3])][toint(value[1], value[2])]) ;

 
        return arr;
    }


}
