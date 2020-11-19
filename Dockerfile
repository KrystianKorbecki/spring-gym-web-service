FROM openjdk:15-alpine
ADD target/gym-0.0.1.jar .
EXPOSE 8080
CMD java -jar gym-0.0.1.jar

