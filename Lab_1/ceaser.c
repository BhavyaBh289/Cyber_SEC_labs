#include<stdio.h>
#include<string.h>
int main(){
    char str[100];
    int shift;
    printf("enter key ");
    scanf("%d",&shift);
    printf("enter text ");
    scanf("%s",&str);

    int i = 0;
    while (str[i] != '\0') {
        if (str[i] >= 'a' && str[i]<= 'z') {
            char c = str[i] - 'a';
            c += shift;
            c = c % 26;
            str[i] = c + 'a';
        }
        i++;
    }
    printf("%s",str);
}


