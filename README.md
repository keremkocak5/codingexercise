http://localhost:8080/codingexercise/swagger-ui/index.html

// kerem: if this was a real db, then the @transactional annotation would have been critical

mvn clean package

docker build -t codingexercice .

docker run -p 8080:8080 codingexercice
