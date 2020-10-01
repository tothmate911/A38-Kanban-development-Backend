# A38-Kanban-development

This project was ordered by A38 ship. 
Their development team uses gitLab. They wanted to see and manipulate their issues on a kanban board, but with more functionality than the one that gitLab provides.

Our application uses OAuth to access the datas in gitlab.
We use graphQL API to get the issues from gitlab, and then we show them on a kanban board.
The columns of the board are the possible statuses.
Horizontally there are swimlanes, which may be the assignees of the issues or the story of the issue.

This is the backend of the application, which is a Spring application.
The frontend is written React (https://github.com/tothmate911/A38-Kanban-development-Frontend).
