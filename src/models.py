'''
Database structures of models stored in database
'''
def recruitment_model(company, content, deadline):
    '''
    model that stores company, content, and deadline of recruitment
    '''
    __company = company
    __content = content
    __deadline = deadline

def chat_model(chat_id, user_id, content):
    '''
    model that stores chat_id, user_id, content of telegram chat
    '''
    __chat_id = chat_id
    __user_id = user_id
    __content = content

def user_model(user_id):
    '''
    model that stores user's telegram id
    '''
    __user_id = user_id
