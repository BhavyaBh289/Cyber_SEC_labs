import java.util.Arrays;
import java.util.Scanner;

public class S_des_encrypt{
    static int[] p10 = new int[]{ 3,5,2,7,4,10,1,9,8,6 };
    static int[] p8 = new int[]{6,3,7,4,8,5,10,9}; 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] splitArray = sc.nextLine().split(" ");
        int[] key = new int[splitArray.length];
        for (int i = 0; i < splitArray.length; i++) {
            key[i] = Integer.parseInt(splitArray[i]);
        }
        int[][] keys = keygen(key);
        System.out.print(Arrays.deepToString(keys));     
        String[] splitArrayy = sc.nextLine().split(" ");
        int[] plaintext = new int[splitArrayy.length];
        for (int i = 0; i < splitArrayy.length; i++) {
            plaintext[i] = Integer.parseInt(splitArrayy[i]);
        }
        // for (int i=0;i<array.length;i++){
        //     System.out.print(array[i]);
        // }
        // int [] data = permutate(array, arrayy);
        // for (int i=0;i<data.length;i++){
        //     System.out.print(data[i]);
        // }
        // ls(1,input);
        // for (int i=0;i<len;i++){
        //     System.out.print(input[i]);
        // }
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
}
