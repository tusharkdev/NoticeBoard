# NoticeBoard

1. Build NoticeBoard jar
    mvn clean package
2. Build Docker image of NoticeBoard
   docker build -t noticeboard .
3. Use docker-compose to run 3 containers - NoticeBoard, Redis and MongoDB
   docker-compose up --build
4. Noticeboard is up on 9090, redis on 6379 and MongoDB on 27017
