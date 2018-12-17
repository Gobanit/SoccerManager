# Matches

## Get all matches
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/matches

## Create match
curl -i -H "CurlTest: true" -H "Content-Type: application/json" --data '{"homeTeam": 5,"awayTeam": 5,"date":"2018-12-05T17:09:19.014"}' -X POST http://localhost:8080/pa165/rest/matches

## Get one match
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/matches/8

## Delete one match
curl -i -H "CurlTest: true" -X DELETE http://localhost:8080/pa165/rest/matches/8

## Simulate one match
curl -i -H "CurlTest: true" -X POST  http://localhost:8080/pa165/rest/matches/simulate/8

# Users

## Get all users
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/users

## Create user
curl -i -H "CurlTest: true" -H "Content-Type: application/json" --data '{"username": "user1","rawPassword": "1234","isAdmin": false}' -X POST http://localhost:8080/pa165/rest/users

## Get user by id
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/users/11

## Get user by name
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/users/name/admin

## Delete user
curl -i -H "CurlTest: true" -X DELETE http://localhost:8080/pa165/rest/users/user1

## Pick team (id) for user
curl -i -H "CurlTest: true" -X PUT http://localhost:8080/pa165/rest/users/user1/team/7

## Get user team
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/users/user1/team

## Change admin rights
curl -i -H "CurlTest: true" -X PUT http://localhost:8080/pa165/rest/users/user1/True

## Authenticate User
curl -i -H "Content-Type: application/json" -X GET --data '{"username": "user1","rawPassword": "1234"}' http://localhost:8080/pa165/rest/users/auth

# Players

## Create a player
curl -X POST -i -H "CurlTest: true" -H "Content-Type: application/json" --data '{"playerName": "John Smith", "nationality": "Slovakia", "birthDate": "1974-12-12", "rating": 55, "position": "OFFENSE", "footed": "RIGHT"}' http://localhost:8080/pa165/rest/players

## Update a player 
curl -X PUT -i -H "CurlTest: true" -H "Content-Type: application/json" --data '{"id": 1, rating": 55, "position": "OFFENSE", "footed": "RIGHT"}' http://localhost:8080/pa165/rest/players

## Delete a player
curl -i -H "CurlTest: true" -X DELETE http://localhost:8080/pa165/rest/players/1

## Get all players
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/players

## Get all free players
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/players/free

## Get a player by ID
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/players/1

# Teams

## Get team of logged in user
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/teams/users

## Get all teams
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/teams/

## Add player to team 
curl -i -H "CurlTest: true" -X PUT http://localhost:8080/pa165/rest/teams/4/players/4

## Delete team 
curl -i -H "CurlTest: true" -X DELETE http://localhost:8080/pa165/rest/teams/1

## Remove player from team 
curl -i -H "CurlTest: true" -X DELETE http://localhost:8080/pa165/rest/teams//4/players/2

## Get team by ID 
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/teams/4

## Get players of team 
curl -i -H "CurlTest: true" -X GET http://localhost:8080/pa165/rest/teams/4/players
