# Matches

## Get all matches
curl -i -H "Authorization: Bearer CURL_TEST" -X GET http://localhost:8080/pa165/matches

## Create match
curl -i -H "Authorization: Bearer CURL_TEST" -H "Content-Type: application/json" --data '{"homeTeam": 5,"awayTeam": 5,"date":"2018-12-05T17:09:19.014"}' -X POST http://localhost:8080/pa165/matches

## Get one match
curl -i -H "Authorization: Bearer CURL_TEST" -X GET http://localhost:8080/pa165/matches/8

## Delete one match
curl -i -H "Authorization: Bearer CURL_TEST" -X DELETE http://localhost:8080/pa165/matches/8

## Simulate one match
curl -i -H "Authorization: Bearer CURL_TEST" -X POST  http://localhost:8080/pa165/matches/simulate/8

# Users

## Get all users
curl -i -H "Authorization: Bearer CURL_TEST" -X GET http://localhost:8080/pa165/users

## Create user
curl -i -H "Authorization: Bearer CURL_TEST" -H "Content-Type: application/json" --data '{"username": "user1","rawPassword": "1234","isAdmin": false}' -X POST http://localhost:8080/pa165/users

## Get user by id
curl -i -H "Authorization: Bearer CURL_TEST" -X GET http://localhost:8080/pa165/users/11

## Get user by name
curl -i -H "Authorization: Bearer CURL_TEST" -X GET http://localhost:8080/pa165/users/name/admin

## Delete user
curl -i -H "Authorization: Bearer CURL_TEST" -X DELETE http://localhost:8080/pa165/users/user1

## Pick team (id) for user
curl -i -H "Authorization: Bearer CURL_TEST" -X PUT http://localhost:8080/pa165/users/user1/team/7

## Get user team
curl -i -H "Authorization: Bearer CURL_TEST" -X GET http://localhost:8080/pa165/users/user1/team

## Change admin rights
curl -i -H "Authorization: Bearer CURL_TEST" -X PUT http://localhost:8080/pa165/users/user1/True

## Authenticate User
curl -i -H "Content-Type: application/json" -X GET --data '{"username": "user1","rawPassword": "1234"}' http://localhost:8080/pa165/users/auth
