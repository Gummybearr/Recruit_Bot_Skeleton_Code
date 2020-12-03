'''
Functions related to web crawling and parsing
'''
from bs4 import BeautifulSoup
import requests
import telegram

def get_web_cookie(url, data):
    '''
    returns cookies from the url using data given as parameter
    '''
    cookies = ''
    with requests.Session() as web_session:
        web_session.post(url, data=data)
        cookies = dict(web_session.cookies)
    return cookies

def get_web_html(url, cookie):
    '''
    returns html text from the url using cookie given as parameter
    '''
    html = ''
    with requests.Session() as web_session:
        response = web_session.get(url, cookies = cookie)
        html = response.text
    return html

def make_web_recruitment_data_json(company, content, deadline):
    '''
    returns json that has company, content and deadline as keys
    '''
    return {
        'company': company,
        'content': content,
        'deadline': deadline
    }

def parse_web_recruitment_skku(html):
    '''
    returns recruitment data of SKKU
    '''
    recruitment_json = []
    soup = BeautifulSoup(html, "html.parser")
    rough_data = soup.findAll('table', {"class": "listTable1"})[0].findAll('tr')
    for i in range(1, len(rough_data)):
        company_and_content = rough_data[i].findAll('td', {"class": "tal"})
        company = company_and_content[0].text
        content = company_and_content[1].text
        deadline = rough_data[i].findAll('td', {"class": "fs_sm"})[0].text
        recruitment_json.append(make_web_recruitment_data_json(company, content, deadline))
    return recruitment_json

def parse_web_recruitment_jobkorea(html):
    '''
    returns recruitment data of JOBKOREA
    '''
    recruitment_json = []
    soup = BeautifulSoup(html, "html.parser")
    rough_data = soup.select('ol', {"class": "rankList"})[0].select('li')
    for i in rough_data:
        company = i.findAll('a', {"class": "coLink"})[0].select('b')[0].text
        content = i.findAll('a', {"class": "link"})[0].text
        deadline = i.findAll('span', {"class": "day"})[0].text
        recruitment_json.append(make_web_recruitment_data_json(company, content, deadline))
    return recruitment_json

def make_web_telegram_chat_data_json(chat_id, user_id, content):
    '''
    returns json list that has telegram chat
    '''
    return {
        'chat_id': chat_id,
        'user_id': user_id,
        'content': content
    }

def get_telegram_chat_of_last_24_hours(telegram_token):
    '''
    returns message sent to bot via telegram in last 24 hours
    '''
    bot = telegram.Bot(token=telegram_token)
    chat_json = []
    for i in bot.getUpdates(timeout=30):
        chat_id = str(i['message']['message_id'])
        user_id = str(i['message']['chat']['id'])
        content = i['message']['text']
        chat_json.append(make_web_telegram_chat_data_json(chat_id, user_id, content))
    return chat_json

def send_telegram_message(telegram_token, user_id, content):
    '''
    bot sends message given as argument to user
    '''
    bot = telegram.Bot(token = telegram_token)
    bot.send_message(chat_id=str(user_id), text=content)
