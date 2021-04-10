'''
Functions related to database.
This file contains functions to search, insert recruitments and user data to database
'''
import sqlite3

def insert_recruitment_to_db(recruitment, db_dir):
    '''
    insert recruitment data to database
    '''
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    insert_query = 'INSERT INTO rec_db (company, name, deadline) VALUES (?, ?, ?)'
    db_cursor.execute(insert_query,
        (recruitment['company'], recruitment['content'], recruitment['deadline']))
    conn.commit()
    conn.close()

def search_recruitment_duplicate(recruitment, db_dir):
    '''
    searches recruitment data in database
    returns true if there exists same recruitment
    '''
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    search_query = "SELECT * FROM rec_db WHERE company= ? AND name= ?"
    duplicate = db_cursor.execute(search_query,
        (str(recruitment['company']), str(recruitment['content']),))
    conn.commit()
    flag = len(duplicate.fetchall())>0
    conn.close()
    return flag

def parse_new_recruitment(recruitments, db_dir):
    '''
    it takes recruitments and returns new recruitments that does not exist in database
    '''
    refined_recruitments = []
    for recruitment in recruitments:
        if search_recruitment_duplicate(recruitment, db_dir) is True:
            continue
        insert_recruitment_to_db(recruitment, db_dir)
        refined_recruitments.append(recruitment)
    return refined_recruitments


def insert_chat_to_db(chat, db_dir):
    '''
    insert chat data to database
    '''
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    insert_query = 'INSERT INTO chat_db (pk, userid, msg) VALUES (?, ?, ?)'
    db_cursor.execute(insert_query,
        (str(chat['chat_id']), str(chat['user_id']), str(chat['content'])))
    conn.commit()
    conn.close()

def search_chat_duplicate(chat, db_dir):
    '''
    searches chat data in database
    returns true if there exists same chat
    '''
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    search_query = "SELECT pk FROM chat_db WHERE pk=(?)"
    duplicate = db_cursor.execute(search_query, (chat['chat_id'],))
    conn.commit()
    flag = len(duplicate.fetchall())>0
    conn.close()
    return flag

def parse_new_chat(chats, db_dir):
    '''
    it takes chats and returns new chats that does not exist in database
    '''
    refined_chats = []
    for chat in chats:
        if search_chat_duplicate(chat, db_dir) is True:
            continue
        insert_chat_to_db(chat, db_dir)
        refined_chats.append(chat)
    return refined_chats

def query_all_user_in_db(db_dir):
    '''
    query all users stored in current database
    '''
    users = []
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    search_query = "SELECT * FROM user_db"
    search_data = db_cursor.execute(search_query)
    conn.commit()
    for i in search_data.fetchall():
        i = list(i)
        users.append(i[0])
    conn.close()
    return users

def insert_user_to_db(user, db_dir):
    '''
    insert user data to database
    '''
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    insert_query = 'INSERT INTO user_db (userid) VALUES (?)'
    db_cursor.execute(insert_query, (user,))
    conn.commit()
    conn.close()

def search_user_duplicate(user, db_dir):
    '''
    searches user data in database
    returns true if there exists same user
    '''
    conn = sqlite3.connect(db_dir)
    db_cursor = conn.cursor()
    search_query = "SELECT userid FROM user_db WHERE userid=(?)"
    duplicate = db_cursor.execute(search_query, (user,))
    conn.commit()
    flag = len(duplicate.fetchall())>0
    conn.close()
    return flag

def parse_user(users, db_dir):
    '''
    it takes users and returns new chats that does not exist in database
    '''
    for user in users:
        user = str(user)
        if search_user_duplicate(user, db_dir) is True:
            continue
        insert_user_to_db(user, db_dir)
    return list(set(users))
