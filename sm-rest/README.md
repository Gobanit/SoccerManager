## Matches

# Get all matches
curl -i -X GET http://localhost:8080/sm-rest/matches

# Create match
curl -X POST -i -H "Content-Type: application/json" --data '{"homeTeam": 2,"awayTeam": 3,"date":[2018,12,15,15,2,13,89000000]}' http://localhost:8080/sm-rest/matches

# Get one match
curl -i -X GET http://localhost:8080/sm-rest/matches/5

# Delete one match
curl -i -X DELETE http://localhost:8080/sm-rest/matches/5

# Simulate one match
curl -X POST -i http://localhost:8080/sm-rest/matches/simulate/5