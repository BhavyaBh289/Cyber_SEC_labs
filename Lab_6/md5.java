package Lab_6;

public class md5 {
    int A = 0X01234567,B=0X89abcdef,C=0xfedcba98,D=0X76543210;
    int[] m;
    int[] k = new int[]{0x10000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000};
    public md5(int[]m){
        this.m= m;
    }
    public static void main(String[] args ){
        int[] message = {0x10000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
                ,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000};
        md5 t = new md5(message);
        for(int i =0;i<16;i++){
            if (i%4==0)
                t.r1();
            if (i%4==1)
                t.r2();
            if (i%4==2)
                t.r3();
            if (i%4==3)
                t.r4();
            t.round(i);
        }
        System.out.println(t.A+""+t.B+""+t.C+""+t.D);

    }
    public void round(int n){
        A=A^m[n];
        A=A^k[n];
        A=A<<2;
        A=A^B;
        int t =A;
        A=D;D=C;C=B;B=t;

    }
    public void r1(){
        A = (B & C)|(~B & D)^ A;
    }public void r2(){
        A = (B & D)|(C & ~D)^ A;
    }public void r3(){
        A = B ^ C ^ D ^ A;
    }public void r4(){
        A = C ^(B | ~D);
    }
}
