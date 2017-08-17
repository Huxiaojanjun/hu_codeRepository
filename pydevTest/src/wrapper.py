#-*- coding: utf-8 -*-
# 对抗12306多次查询后,会出现服务器繁忙问题

from auto_book import login_proc,search_proc,book_proc
import ConfigParser 

config=ConfigParser.ConfigParser()
config.read('user.cfg')

result = 'gogogo'
username=config.get('user','username')
password=config.get('user','password')
begin_time = '18:58:00'
refresh_interval = 1
timer=False
types='D'

for i in range(1,5):
    if result == 'gogogo':
        try:
            sel = login_proc(username,password)
            search_proc(sel,types,timer)
            book_proc(sel,refresh_interval)
        except:
            continue
    else:
        print result
        break
