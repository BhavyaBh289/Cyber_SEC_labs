import java.util.Arrays;

public class S_Aes {

    static int[][] Sub = {
            {0x9, 0x4, 0xA, 0xB},
            {0xD, 0x1, 0x8, 0x5},
            {0x6, 0x2, 0x0, 0x3},
            {0xC, 0xE, 0xF, 0x7}
    };
    static int[] Rcon_1 = {1,0,0,0,0,0,0,0};
    static int[] Rcon_2 = {0,0,1,1,0,0,0,0};
    static int[][] InvSub = {
            {0xA, 0x5, 0x9, 0xB},
            {0x1, 0x7, 0x8, 0xF},
            {0x6, 0x0, 0x2, 0x3},
            {0xC, 0xE, 0x4, 0xD}
    };

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

        int[] k0 = concatenateArrays(w0, w1);
        System.out.printf("K0: ");
        for (int i = 0; i < 16; i++) {
            System.out.printf("%d", k0[i]);
        }
        System.out.println();
        System.out.printf("K1: ");
        int[] k1 = concatenateArrays(w2, w3);
        for (int i = 0; i < 16; i++) {
            System.out.printf("%d", k1[i]);
        }
        System.out.println();
        System.out.printf("K2: ");
        int[] k2 = concatenateArrays(w4, w5);
        for (int i = 0; i < 16; i++) {
            System.out.printf("%d", k2[i]);
        }
        System.out.println();
        keys[0]=k0;
        keys[1]=k1;
        keys[2]=k2;
        return keys;
    }

    public static int[] MixNib(int[] arr) {
        int[] mixedArray = new int[8];

        int[] mult2 = new int[8];
        int[] mult3 = new int[8];

        // Precompute the GF(2^8) multiplication by 2 and 3
        for (int i = 0; i < 8; i++) {
            mult2[i] = multiply(0x2, arr[i]);
            mult3[i] = multiply(0x3, arr[i]);
        }

        mixedArray[0] = mult2[0] ^ mult3[1] ^ arr[2] ^ arr[3];
        mixedArray[1] = arr[0] ^ mult2[1] ^ mult3[2] ^ arr[3];
        mixedArray[2] = arr[0] ^ arr[1] ^ mult2[2] ^ mult3[3];
        mixedArray[3] = mult3[0] ^ arr[1] ^ arr[2] ^ mult2[3];
        mixedArray[4] = mult2[4] ^ mult3[5] ^ arr[6] ^ arr[7];
        mixedArray[5] = arr[4] ^ mult2[5] ^ mult3[6] ^ arr[7];
        mixedArray[6] = arr[4] ^ arr[5] ^ mult2[6] ^ mult3[7];
        mixedArray[7] = mult3[4] ^ arr[5] ^ arr[6] ^ mult2[7];

        return mixedArray;
    }

    public static int multiply(int a, int b) {
        int result = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                result ^= a;
            }
            a <<= 1;
            if ((a & 0x100) > 0) {
                a ^= 0x11B;
            }
            b >>= 1;
        }
        return result;
    }
    public static int F(int data, int key) {
        return data ^ key;
    }
    public static int[] InvMixNib(int[] arr) {
        int[] mixedArray = new int[8];

        mixedArray[0] = arr[5] ^ arr[7] ^ arr[0] ^ arr[2];
        mixedArray[1] = arr[4] ^ arr[6] ^ arr[1] ^ arr[3];
        mixedArray[2] = arr[5] ^ arr[7] ^ arr[2] ^ arr[0];
        mixedArray[3] = arr[4] ^ arr[6] ^ arr[3] ^ arr[1];
        mixedArray[4] = arr[5] ^ arr[7] ^ arr[4] ^ arr[6];
        mixedArray[5] = arr[4] ^ arr[6] ^ arr[5] ^ arr[7];
        mixedArray[6] = arr[5] ^ arr[7] ^ arr[6] ^ arr[4];
        mixedArray[7] = arr[4] ^ arr[6] ^ arr[7] ^ arr[5];

        return mixedArray;
    }

    public static int[] decrypt(int[] input, int[][] key) {
        int[] state = new int[16];

        // Round 2 (Inverse)
//        int[] roundKey = Arrays.copyOfRange(key, 16, 24);
        for (int i = 0; i < 16; i++) {
            state[i] = input[i] ^ key[2][i];
//            System.out.println(state[i]);
        }
        state = InvRotNib(state);
//        for (int i:state){System.out.print(i);}
//        System.out.println();
        state = InvSubNib(state);
        for (int i:state){System.out.print(i);}
        System.out.println();
        // Round 1 (Inverse)
//        roundKey = Arrays.copyOfRange(key, 8, 16);
        for (int i = 0; i < 8; i++) {
            state[i] = state[i] ^ key[1][i];
        }
        state = InvMixNib(state);
        state = InvRotNib(state);
        state = InvSubNib(state);

        // AddRoundKey (Inverse)
        for (int i = 0; i < 8; i++) {
            state[i] = state[i] ^ key[2][i];
        }

        return state;
    }

    // Add these two new functions for the Inverse RotNib and Inverse SubNib transformations
    public static int[] InvRotNib(int[] array) {
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

    public static int[] InvSubNib(int[] arr) {
        int row = (arr[0] << 1) + arr[1];
        int col = (arr[2] << 1) + arr[3];
        int s0 = InvSub[row][col];
        String s0_binary = Integer.toBinaryString(s0);
        String fourBitBinary = padLeft(s0_binary, 8, '0');
        return stringToIntArray(fourBitBinary);
    }

    public static void main(String[] args) {
         int [] key = {0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,1};
         int[] plain = {1,1,0,1,0,1,1,1,0,0,1,0,1,0,0,0};
         int [][]keyss= key_gen(key);

         int cypher[]= {0,0,1,0,0,1,0,0,1,1,1,0,1,1,0,0};
         plain=decrypt(cypher,keyss);
         for (int i : plain){
             System.out.print(i);
         }
//         String encrypted = encrypt(plain, key);
//         System.out.println("Encrypted: " + encrypted);
    }
}
