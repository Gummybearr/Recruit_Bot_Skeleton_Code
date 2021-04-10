'''
funtions not related to web and database
'''

def make_bot_message_form(rec):
    '''
    make message form that bot sends to users
    '''
    return "["+rec['company']+"] \n"+rec['content']+" \nDue: "+rec['deadline']
