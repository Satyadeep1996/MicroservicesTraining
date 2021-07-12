# MicroservicesTraining

How to run the Project

# Start the Eureka, Config and API Gateway server first
# In Config server application.yml provide the password for the git repo
# Create databases 'olx-users', 'olx-masterdata' & olx-advertises' in MySQL
# Start running the other olx services
# Check in eureka dashboard that all the clients are registered or not

API'S

1) To get JWT token 

API :- http://localhost:9191/user/authenticate (POST)

Request Body :-

{
    "userName":"satyadeep",
    "password":"satyadeep123"
}

2) To check if token is correct or not

API :- localhost:9191/user/token/validate (GET)
pass the token as header

3) Create user 

API :- http://localhost:9191/user (POST)

Request Body :-

{
    "firstName": "Satyadeep",
    "lastName": "Gaur",
    "userName": "satyadeep",
    "password": "satyadeep123",
    "email": "satyadeep.gaur@gmail.com",
    "phone": 9999999999
}
