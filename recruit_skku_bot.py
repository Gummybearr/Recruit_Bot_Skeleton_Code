'''
How it works
1. crawls recruitment data from skku and jobkorea
2. stores new recruitment not saved in database
3. stores new recruitment in array
4. crawls last chat sent to bot in last 24 hours
5. sends admin messages not in database
6. stores all users in array
7. send users recruitment data
8. alert admin how it went
'''

#!/usr/bin/env python
# -*- coding: utf-8 -*-

# import constants
from constants.db_directory import LOCAL_DB_DIRECTORY, CLOUD_DB_DIRECTORY
from constants.auth_data import LOGIN_DATA_SKKU, LOGIN_DATA_JOBKOREA
from constants.auth_data import TELEGRAM_BOT_TOKEN, ADMIN_TELEGRAM_ID
from constants.urls import LOGIN_URL_SKKU, LOGIN_URL_JOBKOREA
from constants.urls import RECRUITMENT_URL_SKKU, RECRUITMENT_URL_JOBKOREA

# import functions
from src.db_utils import parse_new_recruitment, parse_new_chat
from src.db_utils import query_all_user_in_db, parse_user
from src.web_utils import get_web_cookie, parse_web_recruitment_skku
from src.web_utils import get_web_html
from src.web_utils import parse_web_recruitment_jobkorea, get_telegram_chat_of_last_24_hours
from src.web_utils import send_telegram_message
from src.utils import make_bot_message_form

# set databavse directory
# DB_DIR = LOCAL_DB_DIRECTORY
DB_DIR = CLOUD_DB_DIRECTORY

# crawl and parse skku data
cookies_skku = get_web_cookie(LOGIN_URL_SKKU, LOGIN_DATA_SKKU)
parsed_recruitment_skku = []
for i in range(1, 3):
    FULL_RECRUITMENT_URL_SKKU = RECRUITMENT_URL_SKKU+str(i)
    html_skku = get_web_html(FULL_RECRUITMENT_URL_SKKU, cookies_skku)
    recruitment_json_skku = parse_web_recruitment_skku(html_skku)
    parsed_recruitment_skku+=parse_new_recruitment(recruitment_json_skku, DB_DIR)

# crawl and parse jobkorea data
cookies_jobkorea = get_web_cookie(LOGIN_URL_JOBKOREA, LOGIN_DATA_JOBKOREA)
html_jobkorea = get_web_html(RECRUITMENT_URL_JOBKOREA, cookies_jobkorea)
recruitment_json_jobkorea = parse_web_recruitment_jobkorea(html_jobkorea)
parsed_recruitment_jobkorea = parse_new_recruitment(recruitment_json_jobkorea, DB_DIR)

# unify parsed recruitment
parsed_recruitment = parsed_recruitment_skku + parsed_recruitment_jobkorea

# get last chat
chat_last_24_hours = get_telegram_chat_of_last_24_hours(TELEGRAM_BOT_TOKEN)
parsed_chat_last_24_hours = parse_new_chat(chat_last_24_hours, DB_DIR)

# send to admin
if len(parsed_chat_last_24_hours)>0:
    send_telegram_message(TELEGRAM_BOT_TOKEN, ADMIN_TELEGRAM_ID, "=== 메시지가 있습니다 ===")
    for chat in parsed_chat_last_24_hours:
        send_telegram_message(TELEGRAM_BOT_TOKEN, ADMIN_TELEGRAM_ID, chat['content'])

# parse users
users = query_all_user_in_db(DB_DIR)
for chat in parsed_chat_last_24_hours:
    users.append(chat['user_id'])
parsed_user = parse_user(users, DB_DIR)

# send messages
for i in parsed_user:
    i = str(i)
    try:
        for recruitment in parsed_recruitment:
            send_telegram_message(TELEGRAM_BOT_TOKEN, i, make_bot_message_form(recruitment))
    except Exception as error_case:
        send_telegram_message(TELEGRAM_BOT_TOKEN, ADMIN_TELEGRAM_ID, i+str(error_case))

# alert ADMIN execution result
send_telegram_message(TELEGRAM_BOT_TOKEN, ADMIN_TELEGRAM_ID, "프로그램이 정상적으로 작동을 완료하였습니다")
