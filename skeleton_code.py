from bs4 import BeautifulSoup
import requests
import telegram

request = requests.get("https://job.skku.edu/jobs/all/")
html = request.text

login_data = {'redir2': '/jobs/all/default.aspx?page=1', 'user_id': '________', 'user_pw': '_________'}

companies = []
jobs = []
comjobs = []

with requests.Session() as s:
    url = "https://job.skku.edu/loginproc.aspx"
    for i in range(1,2):
        login_data['redir2']='/jobs/all/default.aspx?page='+str(i)
        r = s.post(url, data = login_data)
        html = (r.content).decode('utf-8')
        soup = BeautifulSoup(html, "html.parser")
        posts = soup.findAll('td', {"class": "tal"})
        for post in posts:
            if(post.get('title')!=None):
                companies.append(post.get('title'))
            js = post.findAll('a')
            for j in js:
                jobs.append(j.get('title'))

for i in range(0,len(companies)):
    comjobs.append(companies[i]+" : "+jobs[i])

bot = telegram.Bot(token='__________')
receivers=[]

try:
    with open('jobiddb.txt', 'r+') as f:
        for line in f:
            receivers.append(line)
except:
    f = open("jobiddb.txt", 'a+')
    for i in bot.getUpdates():
        receivers.append(str(i['message']['chat']['id']))
    receivers = list(set(receivers))
    
    for i in range(0, len(receivers)):
        if i==len(receivers)-1:
            f.writelines(str(receivers[i]))
        else:
            f.writelines(str(receivers[i])+"\n")
    f.close()

for i in range(0, len(receivers)):
    if receivers[i][-1]=="\n":
        receivers[i] = receivers[0][:-1]

receivers = list(set(receivers))
print(receivers)

for i in receivers:
    print(i)
    if i=="_______":
        for j in range(0, len(comjobs)):
            bot.send_message(chat_id=i, text=comjobs[j])
    elif i=="_______":
        for j in range(0, len(comjobs)):
            bot.send_message(chat_id=i, text="♥"+comjobs[j]+"♥")
        bot.send_message(chat_id=i, text="소영아 오늘도 좋은 하루 보내♥")
    else:
        continue
