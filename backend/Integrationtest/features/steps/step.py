import requests
from behave import when 

def before_scenario(context, scenario):
    context.base_url = 'https://reqres.in'
    
@when('user makes  POST rquest "{endpoint}"')
def step_when_post_request(context, endpoint):
    url = f'{context.base_url}{endpoint}'
    headers = {"Content-Type": "application/json"}
    response = requests.get(url,timeout=20)
    context.response = response