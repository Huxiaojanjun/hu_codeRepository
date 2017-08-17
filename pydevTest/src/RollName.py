#coding=utf-8
'''
花名册点名
'''
def f():
    names = []
    while True:
        print("="*50)
        print("           Welcome to xiaohu System")
        print("请选择要操作的模块：")
        print("1:添加一个学生姓名")
        print("2:删除一个学生姓名")
        print("3:修改一个学生姓名")
        print("4:查找一个学生姓名")
        print("5:查看所有学生姓名")
        print("0:退出系统")
        print("="*50)
        
        keyNum = raw_input("请输入你要操作的系统模块代号：")
        
        
        if  keyNum == "1":
            insertName = raw_input("请输入你要添加的学生姓名：")
            names.append(insertName)
        elif keyNum == "2":
            DelName = raw_input("请输入你要删除的学生姓名：")
            names.remove(DelName)
        elif keyNum == "3":
            UpName1 = raw_input("请输入你要修改的学生姓名：")
            index = names.index(UpName1)
            UpName2 = raw_input("请输入你修改后的学生姓名：")
            names[index] = UpName2
        elif keyNum == "4":
            SelName = raw_input("请输入你要查找的学生姓名：")
            for name in names:
                if(name == SelName):
        elif keyNum == "5":
            print("所有学生的姓名如下：")
            for name in names:
                print(name)
        elif keyNum == "0":
            print("感谢你的使用，你已退出该系统！")
            exit()
                
        else:
            print("你输入的数字不正确！！！")
            
            
f()