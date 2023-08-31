package Lab_5;

public class modulos {
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

}
