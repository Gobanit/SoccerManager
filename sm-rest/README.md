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