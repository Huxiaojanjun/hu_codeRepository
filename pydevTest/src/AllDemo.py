#coding=utf-8
'''
有1、2、3、4个数字，能组成多少个互不相同且无重复数字的三位数？都是多少？
'''
from pip._vendor.requests.packages.chardet.latin1prober import OTH
from random import random
from _random import Random
def f1():
    count=0
    for i in range(1,5):
        for j in range(1,5):
            for k in range(1,5):
                if i!=j and i!=k and j!=k:
                    print(i*100+j*10+k)
                    count+=1
                    
    print("1-5可以组成不同的三位数的个数是：%d"%count)

# f1()

'''
企业发放的奖金根据利润提成。利润(I)低于或等于10万元时，奖金可提10%；
利润高于10万元，低于20万元时，低于10万元的部分按10%提成，高于10万元的部分，
可可提成7.5%；20万到40万之间时，高于20万元的部分，可提成5%；40万到60万之间时高于40万元的部分，
可提成3%；60万到100万之间时，高于60万元的部分，可提成1.5%，高于100万元时，超过100万元的部分按1%提成，
从键盘输入当月利润I，求应发放奖金总数？
'''
def f2():
    num1 = raw_input("请输入你的销售额")
    num = int(num1)
    arr = [1000000,600000,400000,200000,100000,0]
    rat = [0.1,0.075,0.05,0.03,0.015,0.001]
    r = 0
    for idx in range(0,6):
        if num > arr[idx]:
            r+= (num-arr[idx])*rat[idx]
            print((num-arr[idx])*rat[idx])
            num = arr[idx]
    print(r)        
    
#f2()

'''
一个整数，它加上100后是一个完全平方数，再加上168又是一个完全平方数，请问该数是多少
'''
import math
def f3():
    num = 1
    while True:
        if math.sqrt(num+100)-int(math.sqrt(num+100)) == 0 and math.sqrt(num+268)-int(math.sqrt(num+268)) == 0:
            print(num)
            break
        num+=1
    
# f3()

'''
输入某年某月某日，判断这一天是这一年的第几天？ 
'''
def f4(year,month,day):
    msp = [31,28,31,30,31,30,31,31,30,31,30,31]
    msr = [31,29,31,30,31,30,31,31,30,31,30,31]
    if year%400==0 or (year%100!=0 and year %4==0):
        ms=msr
    else:
        ms=msp
    d=0
    for i in range(month-1):
        d+=ms[i]
    d+=day
    return d
# print('2017年1月1日是2017年第%s天'%f4(2017,3,1))    
    

'''
输入三个整数x,y,z，请把这三个数由小到大输出。 
'''
def f5(a, b, c): 
    L = [a, b, c] 
    L.sort() 
    return L 

x, y, z = f5(100, 10, 1) 
# print(x, y, z)
    
'''
输出9*9口诀。
'''
def f6():
    for i in range(1,10):
        for j in range(1,i+1):
            print("%d*%d=%d"%(i,j,i*j)),
        print
# f6()

def f7():
    a = 1 
    b = 1 
    for i in range(1,21,2): 
        print('%d %d'%(a,b)), 
        a += b 
        b += a
        
# f7()

'''
判断101-200之间有多少个素数，并输出所有素数。
'''
from math import sqrt       
def f8():
    for i in range(101,201):
        flag = 1
        k = int(sqrt(i))
        for j in range(2,k+1):
            if i%j == 0:
                flag = 0
                break
        if flag == 1:
                print('%5d'%(i))
        
# f8() 

'''
打印出所有的“水仙花数”，所谓“水仙花数”是指一个三位数，
其各位数字立方和等于该数本身。例如：153是一个“水仙花数”，因为153=1的三次方＋5的三次方＋3的三次方。
'''
def f9():
    for i in range(100,1000):
        a = i/100
        b = i/10%10
        c = i%10
        if(a*a*a+b*b*b+c*c*c==i):
            print(i)
            
# f9()

'''
将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5
'''
def f10():
    number = int(raw_input("请输入一个整数："))
    print("%d = "%number),
    while number != 1:
        for i in range(1, number + 1):
            if (number % i) == 0 and i != 1:
                number = number / i
                if number == 1:
                    print("%d"%i)
                else:
                    print("%d * "%i),
                break  
            
# f10()

'''
利用条件运算符的嵌套来完成此题：学习成绩>=90分的同学用A表示，
60-89分之间的用B表示，60分以下的用C表示。
'''
def f11():
    score = int(raw_input("请输入你的成绩：(需要是整数)"))
    if(score>=90):
        print("A")
    elif(score>=60):
        print("B")
    else:
        print("C")

# f11()

'''
输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数。
'''
import string
def f12():
    st = raw_input("请输入一行字符：")
    ch = 0
    blank = 0
    num = 0
    other = 0
    for c in st:
        if c.isalpha():
            ch+=1
        elif c.isspace():
            blank+=1
        elif c.isdigit():
            num+=1
        else:
            other+=1
    print("英文字母、空格、数字和其它字符的个数分别为：%d、%d、%d、%d"%(ch,blank,num,other))
    
# f12()

'''
一个数如果恰好等于它的因子之和，这个数就称为“完数”。例如6=1＋2＋3.编程找出1000以内的所有完数。
'''
def f13():
    for number in range(1,1001):
        print("%d="%number),
        while number != 1:
            for i in range(1, number + 1):
                if (number % i) == 0 and i != 1:
                    number = number / i
                    if number == 1:
                        print("%d"%i)
                    else:
                        print("%d + "%i),
                    break  
    
# f13()


'''
打印*
'''
def f14():
    i=1
    while i<=5:
        j=1
        while j<=i:
            print("*"),
            j+=1
        print("")
        i+=1
            
# f14() 

'''
打印九九乘法表
print("%d*%d=%-2d"%(j,i,j*i)),
%2d====>左对齐
%-2d====>右对齐
'''
def f15():
    i=1
    while i<=9:
        j=1
        while j<=i:
            print("%d*%d=%-2d"%(j,i,j*i)),
            j+=1
        print("")
        i+=1
            
# f15() 

'''
将8个人随机分配到三个房间
'''
import random
def f16():
    rooms = [[],[],[]]
    peoples = ["A","B","C","D","E","F","G","H"]
    for people in peoples:
        i = random.randint(0,2)
        rooms[i].append(people)
    for room in rooms:
        print("第%d个房间的人数为："%(rooms.index(room)+1)),
        print(room)

f16()

                    
            

        
        
        
        
        
        
        
        
        
        
        
       
        
        
        
        
        
        
        
        
        