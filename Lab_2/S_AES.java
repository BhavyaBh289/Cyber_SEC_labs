import java.util.Arrays;

public class S_AES {
    static int[][] Sub = {
            {0x9, 0x4, 0xA, 0xB},
            {0xD, 0x1, 0x8, 0x5},
            {0x6, 0x2, 0x0, 0x3},
            {0xC, 0xE, 0xF, 0x7}
    };
    static int[] Rcon_1 = {1,0,0,0,0,0,0,0};
    static int[] Rcon_2 = {0,0,1,1,0,0,0,0};
    public static int[][] key_gen(int[] key) {
        int[][] keys= new int[3][];
        int[] w0 = Arrays.copyOfRange(key,0,8);
        int[] w1 = Arrays.copyOfRange(key,8,16);
        int[] w2 = new int[8];
        int[] w3 = new int[8];
        int[] w4 = new int[8];
        int[] w5 = new int[8];

        for (int i = 0; i < 8; i++) {
            w2[i] = w0[i] ^ Rcon_1[i];
        }
        int[] arr1 = new int[8];
        arr1 = RotNib(w1);
        arr1 = SubNib(arr1);
        for (int i = 0; i < 8; i++) {
            w2[i] = w2[i] ^ arr1[i];
        }

        for (int i = 0; i < 8; i++) {
            w3[i] = w2[i] ^ w1[i];
        }

        for (int i = 0; i < 8; i++) {
            w4[i] = w2[i] ^ Rcon_2[i];
        }
        int[] arr2 = new int[8]; // Fix the variable name here from arr1 to arr2
        arr2 = RotNib(w3);
        arr2 = SubNib(arr2); // Fix the transformation operation here
        for (int i = 0; i < 8; i++) {
            w4[i] = w4[i] ^ arr2[i]; // Fix the variable name here from arr1 to arr2
        }

        for (int i = 0; i < 8; i++) {
            w5[i] = w4[i] ^ w3[i];
        }
         int[][] temp = new int[2][];
        temp[0]=w0;
        temp[1]=w1;
        keys[0] = merge(temp);
        temp[0]=w2;
        temp[1]=w3;
        keys[1] = merge(temp);
        temp[0]=w4;
        temp[1]=w5;
        keys[2] = merge(temp);

        return keys;
    }
    public static int[] RotNib (int [] array) {
        int[] swappedArray = new int[8];
        swappedArray[0] = array[4];
        swappedArray[1] = array[5];
        swappedArray[2] = array[6];
        swappedArray[3] = array[7];
        swappedArray[4] = array[0];
        swappedArray[5] = array[1];
        swappedArray[6] = array[2];
        swappedArray[7] = array[3];

        return swappedArray;
    }
    public static int [] SubNib (int [] arr) {

        int row = to_digit(arr[0], arr[1]);
        int col = to_digit(arr[2], arr[3]);
        int s0 = Sub[row][col];
        String s0_binary = Integer.toBinaryString(s0);
        String fourBitBinary = padLeft(s0_binary, 4, '0');
        int[] intArray = stringToIntArray(fourBitBinary);

        int row1 = to_digit(arr[4], arr[5]);
        int col1 = to_digit(arr[6], arr[7]);
        int s1 = Sub[row1][col1];
        String s1_binary = Integer.toBinaryString(s1);
        String fourBitBinary1 = padLeft(s1_binary, 4, '0');
        int[] intArray1 = stringToIntArray(fourBitBinary1);

        arr = concatenateArrays(intArray, intArray1);

        return arr;
    }
    static int[]nibbsub= new int[]{9,4,10,11,13,1,8,5,6,2,0,3,12,14,15,7};
    public static void main(String[] args) {

//
//        int []plaintext  = new int[]{1,1,0,1, 0,1,1,1, 0,0,1,0, 1,0,0,0};
//        int[] key = new int[]{0,1,0,0, 1,0,1,0, 1,1,1,1, 0,1,0,1};
//        int [] []res =split(key,4);
//        int []k=  nibble(res[0]);
//        for (int t:k){
//            System.out.print(t);
//        }
//        k = revnibble(k);
//        for (int t:k){
//            System.out.print(t);
//        }
    }
    static int to_digit(int a, int b) {
        int output = 0;
        if (a == 1 && b == 1)
            output = 3;

        else if (a == 0 && b == 1)
            output = 1;

        else if (a == 1 && b == 0)
            output = 2;

        else if (a == 0 && b == 0)
            output = 0;

        return output;
    }

    public static int[] concatenateArrays(int[] array1, int[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;
        int[] result = new int[length1 + length2];
        System.arraycopy(array1, 0, result, 0, length1);
        System.arraycopy(array2, 0, result, length1, length2);
        return result;
    }

    public static String padLeft(String input, int length, char paddingChar) {
        StringBuilder sb = new StringBuilder(input);
        while (sb.length() < length) {
            sb.insert(0, paddingChar);
        }
        return sb.toString();
    }

    public static int[] stringToIntArray(String intString) {
        int length = intString.length();
        int[] intArray = new int[length];
        for (int i = 0; i < length; i++) {
            intArray[i] = intString.charAt(i) - '0';
        }
        return intArray;
    }


    public static int[] xor (int []arr1 , int[] arr2){
        int [] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i]= (arr1[i]+arr2[i])%2;
        }
        return result;
    }

    public static void ls(int[]inp,int shift){
        int len = inp.length;
        int []ne = new int [len];
        for (int i=0;i<len;i++){
            ne[i]= inp[(i+shift)%len];
        }
        for (int i=0;i<len;i++){
            inp[i]= ne[i];
        }
    }

    public static  int[][] split(int[] input, int parts){
        int [][] res = new int[parts][];
        int k = input.length/parts;
        for (int i=0;i<parts;i++){
            res[i]= Arrays.copyOfRange(input,k*i,k*(i+1));
        }
        return res;
    }
    public static int[] merge (int[][]inp){
        int t =0;
        for (int[]k :inp){
            t+= k.length;
        }
        int[]res = new int[t];
        t = 0;
        for (int[]k :inp){
            System.arraycopy(k,0,res,t,k.length);
            t+=k.length;
        }
        return res;
    }
    public static void printdoublearray(int[][] res){
        for (int[]k:res){
            for (int i :k){
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

    public int[] mixRows(int[] state) {
        int[] mixedState = new int[4];
        mixedState[0] = state[0] ^ Mult(4, state[2]);
        mixedState[1] = state[1] ^ Mult(4, state[3]);
        mixedState[2] = state[2] ^ Mult(4, state[0]);
        mixedState[3] = state[3] ^ Mult(4, state[1]);

        return mixedState;
    }
    public int Mult(int a, int b) {
        int product = 0;
        a &= 0x0F;
        b &= 0x0F;

        while (b != 0) {
            if ((b & 1) != 0) {
                product ^= a;
            }
            boolean carry = (a & 0x08) != 0;
            a <<= 1;
            if (carry) {
                a ^= 0x0B;
            }
            b >>= 1;
        }
        return product & 0x0F;
    }
    public static int toint(int[]arr){
        int res = 0;
        for (int i:arr){
            res*=2;
            res+=i;
        }
        return res;
    }
    public static int[]toarr(int n){
        int[] res = new int[4];
        for (int i = res.length; i > 0; i--) {
            res[i-1]=n%2;
            n/=2;
        }
        return res;
    }
}
