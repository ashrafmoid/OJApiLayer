#include <stdio.h>
#include <unistd.h>
int main() {
   // printf() displays the string inside quotation
    printf("Hello World\n");
    int x,y;
    scanf("%d%d", &x,&y);
    int sum = x+y;
    printf("sum is %d\n", sum);
    // int a,b;
    // scanf("%d%d", &a,&b);
    // printf("sum is %d\n", (a+b));
    // int c,d;
    // scanf("%d%d", &c,&d);
    // printf("sum is %d\n", (c+d));
    // if (sum == 22)
    // sleep(10);
   return 0;
}
