#coding=UTF-8
'''
遍历指定目录下所有文件
'''
import os
def findAllDirPath(path):
    for fileName,d,fileList in os.walk(path):
        for filePath in fileList:
            print(os.path.join(fileName,filePath))
            
findAllDirPath("C:\Users\lenovo\Desktop")