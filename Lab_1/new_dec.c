#include<stdio.h>
#include<string.h>
int main(){
    char str[100];
    int shift;
    int temp ;
    // int t;
    printf("enter key ");
    scanf("%d",&shift);
    printf("enter text ");
    scanf("%s",&str);
    int i = 0;
    while (str[i] != '\0') {
        if (i ==0){
            if (str[i] >= 'a' && str[i]<= 'z') {
                char c = str[i] - 'a';
                c -= shift;
                shift = (int)(temp)+1;
                c = c % 26;
                str[i] = c + 'a';
            }
        }else if (str[i] >= 'a' && str[i]<= 'z') {
            char c = str[i] - 'a';
            temp = (int)(c);
            // printf("%d",temp);
            c -= shift;
            shift = temp;
            c = (c+26) % 26;
            str[i] = c + 'a';
            // printf("%s",str);
            // printf("%d \n",shifts);
        }
        i++;
    }
    printf("%s",str);
}


