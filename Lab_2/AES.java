public class AES {
    private static final int[] sBox = {0x9, 0x4, 0xA, 0xB, 0xD, 0x1, 0x8, 0x5, 0x6, 0x2, 0x0, 0x3, 0xC, 0xE, 0xF, 0x7};
    private static final int[] sBoxI = {0xA, 0x5, 0x9, 0xB, 0x1, 0x7, 0x8, 0xF, 0x6, 0x0, 0x2, 0x3, 0xC, 0x4, 0xD, 0xE};
    private int[] preRoundKey;
    private int[] round1Key;
    private int[] round2Key;


    private int subWord(int word) {
        return (sBox[(word >> 4)] << 4) + sBox[word & 0x0F];
    }

    private int rotWord(int word) {
        return ((word & 0x0F) << 4) + ((word & 0xF0) >> 4);
    }

    public void keygen(int key) {
        int Rcon1 = 0x80;
        int Rcon2 = 0x30;

        int[] w = new int[6];
        w[0] = (key & 0xFF00) >> 8;
        w[1] = key & 0x00FF;
        w[2] = w[0] ^ (subWord(rotWord(w[1])) ^ Rcon1);
        w[3] = w[2] ^ w[1];
        w[4] = w[2] ^ (subWord(rotWord(w[3])) ^ Rcon2);
        w[5] = w[4] ^ w[3];

//        int[][] keys = new int[3][4];
        preRoundKey = intToState((w[0] << 8) + w[1]);
        round1Key = intToState((w[2] << 8) + w[3]);
        round2Key = intToState((w[4] << 8) + w[5]);

        // return keys;
    }

    private int gfMult(int a, int b) {
        int product = 0;

        a = a & 0x0F;
        b = b & 0x0F;

        while (a != 0 && b != 0) {
            if ((b & 1) == 1) {
                product ^= a;
            }
            a <<= 1;
            if ((a & (1 << 4)) != 0) {
                a ^= 0b10011;
            }
            b >>= 1;
        }
        return product;
    }

    private int[] intToState(int n) {
        int[] state = new int[4];
        state[0] = (n >> 12) & 0xF;
        state[1] = (n >> 4) & 0xF;
        state[2] = (n >> 8) & 0xF;
        state[3] = n & 0xF;
        return state;
    }

    private int stateToInt(int[] m) {
        return (m[0] << 12) + (m[1] << 4) + (m[2] << 8) + m[3];
    }

    private int[] addRoundKey(int[] s1, int[] s2) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = s1[i] ^ s2[i];
        }
        return result;
    }

    private int[] subNibbles(int[] sbox, int[] state) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = sbox[state[i]];
        }
        return result;
    }

    private int[] shiftRows(int[] state) {
        return new int[]{state[0], state[1], state[3], state[2]};
    }

    private int[] mixColumns(int[] state) {
        return new int[]{
                state[0] ^ gfMult(4, state[2]),
                state[1] ^ gfMult(4, state[3]),
                state[2] ^ gfMult(4, state[0]),
                state[3] ^ gfMult(4, state[1])
        };
    }

    private int[] inverseMixColumns(int[] state) {
        return new int[]{
                gfMult(9, state[0]) ^ gfMult(2, state[2]),
                gfMult(9, state[1]) ^ gfMult(2, state[3]),
                gfMult(9, state[2]) ^ gfMult(2, state[0]),
                gfMult(9, state[3]) ^ gfMult(2, state[1])
        };
    }

    public static void ToBinary(int num) {
        if(num>=1) {
            ToBinary(num / 2);
            System.out.print(num % 2);
        }
    }
    public int encrypt(int plaintext) {
        int[] state = addRoundKey(preRoundKey, intToState(plaintext));
        state = mixColumns(shiftRows(subNibbles(sBox, state)));
        state = addRoundKey(round1Key, state);
        state = shiftRows(subNibbles(sBox, state));
        state = addRoundKey(round2Key, state);
        return stateToInt(state);
    }

    public int decrypt(int ciphertext) {
        int[] state = addRoundKey(round2Key, intToState(ciphertext));
        state = subNibbles(sBoxI, shiftRows(state));
        state = inverseMixColumns(addRoundKey(round1Key, state));
        state = subNibbles(sBoxI, shiftRows(state));
        state = addRoundKey(preRoundKey, state);
        return stateToInt(state);
    }
    public static void main(String [] args){
    int key = 0b0100101011110101;
    int plaintext= 0b1101011100101000;
    AES test = new AES();
    test.keygen(key);
    int ciphertext = test.encrypt(plaintext);
    ToBinary(ciphertext);
//        ToBinary(19);
    System.out.println();
    plaintext=test.decrypt(ciphertext);
    ToBinary(plaintext);
    }
}

