# add user to db and send message to maintainer

from bs4 import BeautifulSoup
import requests
import telegram
import sqlite3

#send new messages to administrator

class chat:
    def __init__(self, pk, userid, msg):
        self.pk = pk
        self.userid = userid
        self.msg = msg

bot = telegram.Bot(token='1098512227:AAFXjpC8rPNmyJlG1NmM-_B80Oed99Yu63s')
chat_list = []

for i in bot.getUpdates():
    tmp = chat(str(i['message']['message_id']), str(i['message']['chat']['id']), i['message']['text'])
    chat_list.append(tmp)

create_chat_db_query = """ CREATE TABLE IF NOT EXISTS chat_db (
                                        pk integer PRIMARY KEY,
                                        userid text,
                                        msg text
                                    ); """

search_chat_db_query = "SELECT pk FROM chat_db WHERE pk=(?)"
insert_chat_db_query = 'INSERT INTO chat_db (pk, userid, msg) VALUES (?, ?, ?)'

chat_new_list = []

conn = sqlite3.connect('./RecruitSKKU_bot/user.db')
db = conn.cursor()
conn.commit()

for i in chat_list:
    test = db.execute(search_chat_db_query, (i.pk,))
    conn.commit()
    if len(test.fetchall())== 0:
        chat_new_list.append(i.msg)
        db.execute(insert_chat_db_query, (str(i.pk), str(i.userid), str(i.msg) ))
        conn.commit()

conn.close()
    
if len(chat_new_list)!=0:
    bot.send_message(chat_id="1015743608", text="=== 메시지가 있습니다 ===")
    for i in chat_new_list:
        bot.send_message(chat_id="1015743608", text=i)
