# A38-Kanban-development

![image](https://user-images.githubusercontent.com/55543332/168425693-72b469b5-8c11-4abf-9740-681382e2522e.png)

## Contributors:

### Developer team:

György Noémi, Hegedüs Enikő, Tóth Máté

### Project Management:

Csapó Krisztián, Csürke Gábor

## Description

This application is a kanban board. The board displays gitlab issue cards, showing the title, assignee, project, story,
milestone, priority of the issue, and some other information.  
The columns of the board are the statuses of the issues. Horizontally there are swimlanes, which optionally can be the
assignees or the stories of the issues.

The statuses, stories and priorities are actually stored in the labels of a story as described below in the
configuration part.

Issues can be filtered by project, milestone and story. If no story or/and milestone is selected then every issue will
be shown, even possibly those which do not have a story or/and milestone.

The gitlab page of the issues can be reached by clicking on the gitlab logo on the issues. The statuses and the
assignees of issues can be directly changed from the board by the drag and drop function of the issues.

Our application uses the OAuth2 protocol to access GitLab resources on the user’s behalf. We use the Authorization code
flow as described here: https://docs.gitlab.com/ee/api/oauth2.html.

We use graphQL API to get datas from gitlab and modify them (https://docs.gitlab.com/ee/api/graphql/).

This is the backend of the application, which is a Spring application. The frontend is written React.  
This is the frontend remote repository: https://github.com/tothmate911/A38-Kanban-development-Frontend.

## Usage

For instant test usage of the application please go to the following page:
https://a38-kanban.herokuapp.com

After the auto-redirecting to Gitlab authentication platform give these credentials:

* Username: tothmate316
* Password: kanban1!

If you are asked to Authorize the configured GitLab Application to use the account, then please authorize it.

(Please note that you will not be able to assign an issue to a user who is not a member of the issue's project.)

## Configuration

Clone this repository and the frontend repository to your local computer. Make sure that the two downloaded directories
are on the same level.

### Configurations for your gitlab server

First you need to add a new application to GitLab. You may add the application to your own GitLab server, or you can
create an application here: https://gitlab.com/-/profile/applications

- The Redirect URI should be this: <your_frontend_url>/getToken.  
  (For example: https://a38-kanban.herokuapp.com/getToken)
- The scopes of the application should be api.
- After creating the application you should be able to see your Application ID and Secret, which you will need later.


##### A38-Kanban-development/docker-compose.yml
Please find this file: A38-Kanban-development/docker-compose.yml.
Set the appropriate parameters, as described the following section:

```yaml
version: '3'
services:
  backend:
    image: kanban-backend:latest
    build:
      context: '.'
      dockerfile: 'Dockerfile'
    ports:
      - '8080:8080'
    environment:
      - frontend.url=http://localhost:3000    #This should be your frontend url
      - gitlabServer.url=https://gitlab.com   #This should be your gitlab server url      
  frontend:
    image: kanban-frontend:latest
    build:
      context: '../A38-Kanban-development-Frontend'
      dockerfile: 'Dockerfile'
    ports:
      - '3000:3000'   #You can set the port here
    environment:
      - REACT_APP_GITLAB_SERVER=https://gitlab.com    #This should be your gitlab server url
      - REACT_APP_GITLAB_APP_ID=458f27c6eb357cf7419231331e3af3e3a9d39782b7edf50ac2cc083e7a7f1a4a    #This should be your gitlab application id
      - REACT_APP_GITLAB_APP_SECRET=f0fbf238c1ef5d0be56bf1118c430b15daff2b85d790d4bbfd76b8ccbb5bac33    #This should be your gitlab application secret
      - REACT_APP_APPLICATION=http://localhost:3000   #This should be your frontend url
    stdin_open: true
```

##### A38-Kanban-development/src/main/resources/configprops.json

In this file set your predefined properties in a Json file.

```jsonc
{
  "storyPrefix": "story:",  //this should be your own story prefix

  "priorities": [
    {
      "title": "KB[priority][70][P3]",  //this is the label title of one of your priority labels on gitlab
      "display": "P3"   //this is what you want to display on the kanban board, 
                        // that corresponds to the above given priority label title
    }
  ],

  "statuses": [
    {
      "title": "KB[stage][00][Backlog]",    //this is the label title of one of your status labels on gitlab
      "display": "Backlog"     //this is what you want to display on the kanban board, 
                               // that corresponds to the above given status label title
    }
  ]

}
``` 

## Docker deployment

Prerequisites:

- Freshly built jar file has to accessible under target/*.jar  
  You can have it by running `mvn clean install`
- Docker installed (https://docs.docker.com/engine/install/)
- Docker-compose installed (https://docs.docker.com/compose/install/)
- Frontend has to accessible under A38-Kanban-development-Frontend/ and be on the same level with the current directory

If he above points are met:

- Hit `docker-compose up --build` this will build the docker images from the specified  
  docker files based on the `docker-compose.yml` and spin up the application stack.
- Visit `localhost:3000` (To change the host see the docker-compose.yml configuration)
- The `--build` options forces a rebuild every time it is issued. If images are already build simply
  hit `docker-compose up`

If you want to run the docker compose process in the background just pass the `-d` option to docker-compose
