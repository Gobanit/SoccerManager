## Matches

# Get all matches
curl -i -X GET http://localhost:8080/pa165/matches

# Create match
curl -X POST -i -H "Content-Type: application/json" --data '{"homeTeam": 2,"awayTeam": 3,"date":"2018-12-05T17:09:19.014"}' http://localhost:8080/pa165/matches

# Get one match
curl -i -X GET http://localhost:8080/pa165/matches/5

# Delete one match
curl -i -X DELETE http://localhost:8080/pa165/matches/5

# Simulate one match
curl -X POST -i http://localhost:8080/pa165/matches/simulate/5

## Users

# Get all users
curl -i -X GET http://localhost:8080/pa165/users

# Create user
curl -X POST -i -H "Content-Type: application/json" --data '{"username": "user1","rawPassword": "1234","isAdmin": false}' http://localhost:8080/pa165/users

# Get user by id
curl -i -X GET http://localhost:8080/pa165/users/8

# Get user by name
curl -i -X GET http://localhost:8080/pa165/users/name/admin

# Delete user
curl -i -X DELETE http://localhost:8080/pa165/users/user1

# Pick team (id) for user
curl -X PUT http://localhost:8080/pa165/users/user1/team/4

# Get user team
curl -i -X GET http://localhost:8080/pa165/users/user1/team

# Change admin rights
curl -X PUT http://localhost:8080/pa165/users/user1/True

# Authenticate User
curl -X GET -i -H "Content-Type: application/json" --data '{"username": "user1","rawPassword": "1234"}' http://localhost:8080/pa165/users/auth
