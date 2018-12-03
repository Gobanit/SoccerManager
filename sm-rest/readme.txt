//Matches

Get all matches
GET
http://localhost:8080/sm-rest/matches

Create match
POST
http://localhost:8080/sm-rest/matches 
{"homeTeam": 2,"awayTeam": 3,"date":[2018,12,15,15,2,13,89000000]}

Get one match
GET
http://localhost:8080/sm-rest/matches/5

Delete one match
DELETE
http://localhost:8080/sm-rest/matches/5

Simulate one match
POST
http://localhost:8080/sm-rest/matches/simulate/5