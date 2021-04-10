#Query list of users

from bs4 import BeautifulSoup
import requests
import telegram
import sqlite3

bot = telegram.Bot(token='1098512227:AAFXjpC8rPNmyJlG1NmM-_B80Oed99Yu63s')

conn = sqlite3.connect('./RecruitSKKU_bot/user.db')
db = conn.cursor()
conn.commit()
search_user_db_query = "SELECT userid FROM user_db WHERE userid=(?)"
insert_user_db_query = 'INSERT INTO user_db (userid) VALUES (?)'

user_list = []
test = db.execute("SELECT * FROM user_db")
conn.commit()
for i in test.fetchall():
    i = list(i)
    user_list.append(i[0])

for i in bot.getUpdates():
    userid = str(i['message']['chat']['id'])
    test = db.execute(search_user_db_query, (userid, ))
    if len(test.fetchall())== 0:
        user_list.append(userid)
        db.execute(insert_user_db_query, (userid, ))
        conn.commit()
    
user_list = list(set(user_list))
print("user count: "+str(len(user_list)))
print(user_list)

conn.close()
