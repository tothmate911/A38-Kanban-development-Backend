# A38-Kanban-development-Backend

This project was ordered by A38 ship. 
Their development team uses gitLab. They wanted to see and manipulate their issues on a kanban board, but with more functionality than the one that gitLab provides.

Our application uses OAuth to access the datas in gitlab.
We use graphQL API to get the issues from gitlab, and then we show them on a kanban board.
The columns of the board are the possible statuses.
Horizontally there are swimlanes, which may be the assignees of the issues or the story of the issue.

This is the backend of the application, which is a Spring application.
The frontend is written React (https://github.com/tothmate911/A38-Kanban-development-Frontend).


## Docker deployment

prerequisite:  
- freshly built jar file has to accessible under target/*.jar  
You can have it by running `mvn clean install` 
- docker installed (https://docs.docker.com/engine/install/)  
- docker-compose installed (https://docs.docker.com/compose/install/)  
- frontend has to accessible under A38-Kanban-development-Frontend/ and be on the same level with the current directory

If he above points are met:

- hit `docker-compose up --build` this will build the docker images from the specified  
docker files based on the `docker-compose.yml` and spin up the application stack.
- Visit `localhost:3000` (To change the host see the docker-compose.yml configuration)  
Be amazed.  
- The `--build` options forces a rebuild every time it is issued. If images are already build
simply hit `docker-compose up`
  
If you want to run the docker compose process in the background just pass the `-d` option to docker-compose
